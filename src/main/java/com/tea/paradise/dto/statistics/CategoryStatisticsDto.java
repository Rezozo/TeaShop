package com.tea.paradise.dto.statistics;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryStatisticsDto {
    private String categoryTitle;
    private Float percent;
}
