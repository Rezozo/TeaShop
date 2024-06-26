package com.tea.paradise.dto.pagination;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
public class Pagination {
    private Integer currentPage;
    private Integer pageSize;
}
