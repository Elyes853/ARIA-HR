package com.itpeac.ariarh.middleoffice.service.dto;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.data.domain.Pageable;

public class PageRequestByCriteria<DTO> {
    public DTO criteria;
    public LazyLoadEvent lazyLoadEvent;

    public Pageable toPageable() {
        return lazyLoadEvent != null ? lazyLoadEvent.toPageable() : null;
    }

    @Override
    public String toString() {

        return new ToStringBuilder(this.getClass()).
                append("criteria", criteria).
                append("lazyLoad Event", lazyLoadEvent).
                toString();
    }
}
