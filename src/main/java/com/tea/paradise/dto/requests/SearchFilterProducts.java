package com.tea.paradise.dto.requests;

import com.tea.paradise.enums.SortType;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SearchFilterProducts {
    SortType sortType;
    String searchString;
    // TODO add another filters
}
