package com.itpeac.ariarh.middleoffice.service.dto;

import org.apache.commons.lang.builder.ToStringBuilder;

public class SortField {
    private String field;
    private Integer order;

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this.getClass()).
                append("field", field).
                append("order", order).
                toString();
    }
}
