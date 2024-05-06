package com.tea.paradise.dto.statistics;

import lombok.*;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StatisticsDto {
    private List<CategoryStatisticsDto> categoryStatistics;
    private List<OrdersStatisticsDto> ordersStatistics;
    private Integer countOfOrders;
    private Integer countOfSales;
    private Double totalPrice;
}
