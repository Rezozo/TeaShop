package com.tea.paradise.dto.pagination;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
public class PagingResponse<T> {
    private List<T> data;
    private PageResult pagingCommand;
}
