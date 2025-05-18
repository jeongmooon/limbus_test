package com.example.test_web.global.pagination;

import lombok.Getter;
import org.springframework.data.domain.Slice;

import java.util.List;

@Getter
public class SliceResponse<T extends SliceElement> {
    private final List<T> list;
    private final int currentPage;
    private final int pageSize;
    private final boolean first;
    private final boolean last;

    public SliceResponse(Slice<T> slice) {
        this.list = slice.getContent();
        this.currentPage = slice.getNumber();
        this.pageSize = slice.getSize();
        this.first = slice.isFirst();
        this.last = slice.isLast();
    }
}
