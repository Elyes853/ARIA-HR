package com.itpeac.ariarh.middleoffice.service.dto;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.data.domain.Pageable;

public class PageRequest {

    public LazyLoadEvent lazyLoadEvent;

    public Pageable toPageable() {
        return lazyLoadEvent != null ? lazyLoadEvent.toPageable() : null;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
