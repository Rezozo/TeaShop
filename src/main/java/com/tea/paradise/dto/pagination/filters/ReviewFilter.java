package com.tea.paradise.dto.pagination.filters;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReviewFilter {
    private boolean byCurrentUser;
}
