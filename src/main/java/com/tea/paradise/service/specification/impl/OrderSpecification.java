package com.tea.paradise.service.specification.impl;

import com.tea.paradise.dto.pagination.filters.OrderFilter;
import com.tea.paradise.model.*;
import com.tea.paradise.service.UserService;
import com.tea.paradise.service.specification.Specification;
import jakarta.persistence.criteria.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.tea.paradise.service.specification.impl.ProductSpecification.ID_PATH;

@Component
public class OrderSpecification implements Specification<Orders, OrderFilter> {
    public static final String CREATED_DATE_PATH = "createdDate";
    public static final String ORDER_STATUS_PATH = "orderStatus";
    public static final String STATUS_PATH = "status";
    public static final String PACKAGE_ORDERS_PATH = "packageOrders";
    public static final String FIXED_PRICE_PATH = "fixedPrice";
    public static final String QUANTITY_PATH = "quantity";
    public static final String ADDRESS_PATH = "address";
    public static final String USER_PATH = "user";

    @Autowired
    UserService userService;

    @Override
    public List<Predicate> predicates(OrderFilter filter, CriteriaBuilder criteriaBuilder, Root<Orders> root) {
        List<Predicate> predicates = new ArrayList<>();
        Path<ZonedDateTime> zonedDateTimePath = root.get(CREATED_DATE_PATH);
        Path<OrderStatus> orderStatusPath = root.get(ORDER_STATUS_PATH);
        Join<Orders, PackageOrder> packageOrdersJoin = root.join(PACKAGE_ORDERS_PATH, JoinType.LEFT);
        Join<Orders, Address> addressJoin = root.join(ADDRESS_PATH, JoinType.LEFT);
        Join<Address, Users> userJoin = addressJoin.join(USER_PATH, JoinType.LEFT);

        if (filter.getByCurrentUser()) {
            predicates.add(criteriaBuilder.equal(userJoin.get(ID_PATH), userService.getAuthInfo().getId()));
        }

        if (Objects.nonNull(filter.getDateFrom())) {
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(zonedDateTimePath, filter.getDateFrom()));
        }

        if (Objects.nonNull(filter.getDateTo())) {
            predicates.add(criteriaBuilder.lessThanOrEqualTo(zonedDateTimePath, filter.getDateTo()));
        }

        if (Objects.nonNull(filter.getStatus())) {
            predicates.add(criteriaBuilder.equal(orderStatusPath.get(STATUS_PATH), filter.getStatus()));
        }

        if (Objects.nonNull(filter.getMinPrice())) {
            Expression<Double> totalPriceExpression = criteriaBuilder.prod(
                    packageOrdersJoin.get(FIXED_PRICE_PATH),
                    packageOrdersJoin.get(QUANTITY_PATH)
            );
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(totalPriceExpression, filter.getMinPrice()));
        }

        if (Objects.nonNull(filter.getMaxPrice())) {
            Expression<Double> totalPriceExpression = criteriaBuilder.prod(
                    packageOrdersJoin.get(FIXED_PRICE_PATH),
                    packageOrdersJoin.get(QUANTITY_PATH)
            );
            predicates.add(criteriaBuilder.lessThanOrEqualTo(totalPriceExpression, filter.getMaxPrice()));
        }

        return predicates;
    }
}
