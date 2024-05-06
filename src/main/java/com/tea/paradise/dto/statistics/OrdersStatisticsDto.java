package com.tea.paradise.dto.statistics;

import lombok.*;

import java.time.LocalDate;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrdersStatisticsDto {
    private LocalDate date;
    private Integer countOfOrders;
}
