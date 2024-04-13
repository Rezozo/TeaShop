package com.tea.paradise.dto.pagination.filters;

import com.tea.paradise.enums.OrderTrackingStatus;
import lombok.*;

import java.time.ZonedDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderFilter {
    private Boolean byCurrentUser;
    private Double minPrice;
    private Double maxPrice;
    private ZonedDateTime dateFrom;
    private ZonedDateTime dateTo;
    private OrderTrackingStatus status;
}
