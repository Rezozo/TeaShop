package com.tea.paradise.service;

import com.tea.paradise.dto.statistics.CategoryStatisticsDto;
import com.tea.paradise.dto.statistics.OrdersStatisticsDto;
import com.tea.paradise.dto.statistics.StatisticsDto;
import com.tea.paradise.enums.StatisticsSortType;
import com.tea.paradise.model.Category;
import com.tea.paradise.model.Orders;
import com.tea.paradise.model.PackageOrder;
import com.tea.paradise.model.Product;
import com.tea.paradise.repository.OrderRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class StatisticsService {
    OrderRepository ordersRepository;

    public StatisticsDto calculateStatistics(StatisticsSortType sortType) {
        StatisticsDto statistics = new StatisticsDto();

        ZonedDateTime endDate = ZonedDateTime.now();
        ZonedDateTime startDate = getStartDate(endDate, sortType);

        List<Orders> orders = ordersRepository.findAllByCreatedDateBetween(startDate, endDate);
        int totalOrders = orders.size();
        int totalSales = orders.stream()
                .mapToInt(order -> order.getPackageOrders().stream()
                        .mapToInt(PackageOrder::getQuantity)
                        .sum())
                .sum();
        double totalPrice = orders.stream()
                .flatMap(order -> order.getPackageOrders().stream())
                .mapToDouble(po -> po.getFixedPrice() * po.getQuantity() - po.getOrder().getBonusesSpent())
                .sum();
        statistics.setCountOfOrders(totalOrders);
        statistics.setCountOfSales(totalSales);
        statistics.setTotalPrice(totalPrice);

        Map<Category, Double> categorySales = new HashMap<>();
        for (Orders order : orders) {
            for (PackageOrder packageOrder : order.getPackageOrders()) {
                Product product = packageOrder.getPack().getProduct();
                Category category = product.getCategory();
                double packageSale = packageOrder.getFixedPrice() * packageOrder.getQuantity();
                categorySales.merge(category, packageSale, Double::sum);
            }
        }

        List<CategoryStatisticsDto> categoryStatistics = new ArrayList<>();
        double totalSalesValue = categorySales.values().stream().mapToDouble(Double::doubleValue).sum();
        for (Map.Entry<Category, Double> entry : categorySales.entrySet()) {
            CategoryStatisticsDto dto = new CategoryStatisticsDto();
            dto.setCategoryTitle(entry.getKey().getName());
            dto.setPercent((float) ((entry.getValue() / totalSalesValue) * 100));
            categoryStatistics.add(dto);
        }
        statistics.setCategoryStatistics(categoryStatistics);

        Map<LocalDate, Integer> ordersByDate = orders.stream()
                .collect(Collectors.groupingBy(
                        order -> order.getCreatedDate().toLocalDate(),
                        Collectors.summingInt(order -> 1)
                ));

        List<OrdersStatisticsDto> ordersStatistics = new ArrayList<>();
        for (Map.Entry<LocalDate, Integer> entry : ordersByDate.entrySet()) {
            OrdersStatisticsDto dto = new OrdersStatisticsDto();
            dto.setDate(LocalDate.from(entry.getKey().atStartOfDay(ZoneId.systemDefault())));
            dto.setCountOfOrders(entry.getValue());
            ordersStatistics.add(dto);
        }
        statistics.setOrdersStatistics(ordersStatistics);

        return statistics;
    }

    private ZonedDateTime getStartDate(ZonedDateTime endDate, StatisticsSortType sortType) {
        return switch (sortType) {
            case YEAR -> endDate.minusYears(1);
            case MONTH -> endDate.minusMonths(1);
            case WEEK -> endDate.minusWeeks(1);
            case DAY -> endDate.minusDays(1);
            default -> ZonedDateTime.now().minusYears(10);
        };
    }
}
