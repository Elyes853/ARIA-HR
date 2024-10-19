package com.itpeac.ariarh.middleoffice.service.dto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class PageResponse<T> {
    public final long totalPages;
    public final long totalElements;
    public final List<T> content;
    public Map<String, Object> extras = null;

    public PageResponse(long totalPages, long totalElements, List<T> content) {
        this.totalPages = totalPages;
        this.totalElements = totalElements;
        this.content = content;
    }

    public void addExtraValue(String key, Object value) {
        if (extras == null) {
            extras = new HashMap<>();
        }
        extras.put(key, value);
    }
}
