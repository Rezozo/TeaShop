package com.tea.paradise.dto.pagination;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
public class PagingCommand<T,K> {
    private Pagination pagination;
    private T filter;
    private Sorter<K> sorter;
}
