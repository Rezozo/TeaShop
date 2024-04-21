package com.tea.paradise.dto.order;

import com.tea.paradise.dto.packages.PackageOrderDto;
import com.tea.paradise.enums.OrderTrackingStatus;
import lombok.*;

import java.time.ZonedDateTime;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderShortDto {
    private Long id;
    private OrderTrackingStatus status;
    private ZonedDateTime createdDate;
    private List<PackageOrderDto> packageOrders;
    private String trackNumber;
    private Double totalCost;
}
