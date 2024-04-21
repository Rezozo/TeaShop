package com.tea.paradise.dto.order;

import com.tea.paradise.dto.address.AddressDto;
import com.tea.paradise.dto.packages.PackageOrderDto;
import com.tea.paradise.dto.recipient.RecipientDto;
import com.tea.paradise.enums.OrderTrackingStatus;
import lombok.*;

import java.time.ZonedDateTime;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDto {
    private Long id;
    private RecipientDto recipient;
    private OrderTrackingStatus status;
    private ZonedDateTime createdDate;
    private List<PackageOrderDto> packageOrders;
    private String trackNumber;
    private AddressDto address;
    private Integer bonusesSpent;
    private Integer bonusesAccrued;
    private Double totalCost;
}
