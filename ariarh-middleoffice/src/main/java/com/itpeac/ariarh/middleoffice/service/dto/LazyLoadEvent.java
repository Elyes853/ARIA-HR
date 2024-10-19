package com.itpeac.ariarh.middleoffice.service.dto;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class LazyLoadEvent {
    /**
     * First row offset.
     */
    public int first;

    /**
     * Number of rows per page.
     */
    public int rows;
    public List<SortField> multiSortMeta = new ArrayList<>();
    public String sortField;
    public int sortOrder;

    public Pageable toPageable() {
        if (sortField != null) {
            return PageRequest.of(toPageIndex(), rows, toSortDirection(), sortField);
        } else if (CollectionUtils.isNotEmpty(multiSortMeta)) {
            return PageRequest.of(toPageIndex(), rows,
                    Sort.by(multiSortMeta.stream().map(sf -> sf.getOrder() == -1 ? Order.desc(sf.getField()) : Order.asc(sf.getField())).collect(Collectors.toList())));
        } else {
            return PageRequest.of(toPageIndex(), rows);
        }
    }

    /**
     * Zero based page index.
     */
    public int toPageIndex() {
        return (first + rows) / rows - 1;
    }

    public Sort.Direction toSortDirection() {
        return sortOrder == 1 ? Sort.Direction.ASC : Sort.Direction.DESC;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this.getClass()).
                append("rows", rows).
                append("sortField", sortField).
                append("sortOrder", sortOrder).
                append("multiSortMeta", multiSortMeta).
                toString();
    }


}