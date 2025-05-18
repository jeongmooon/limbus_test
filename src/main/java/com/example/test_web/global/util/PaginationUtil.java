package com.example.test_web.global.util;

import com.example.test_web.global.pagination.SliceElement;

import java.util.List;

public class PaginationUtil {

    private PaginationUtil() {
        throw new IllegalStateException("This is Utility class!");
    }

    public static boolean hasNext(List<? extends SliceElement> list, int pageSize) {
        boolean hasNext = false;
        if (list.size() > pageSize) {
            list.remove(pageSize);
            hasNext = true;
        }
        return hasNext;
    }
}
