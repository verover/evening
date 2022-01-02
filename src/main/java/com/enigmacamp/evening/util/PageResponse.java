package com.enigmacamp.evening.util;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class PageResponse<T> {
    @JsonProperty("content")
    List<T> data;
    @JsonProperty("count")
    private Long count;
    @JsonProperty("totalPage")
    private  Integer totalPage;
    @JsonProperty("page")
    private Integer page;
    @JsonProperty("size")
    private Integer size;

    public PageResponse(List<T> data, Long count, Integer totalPage, Integer page, Integer size) {
        this.data = data;
        this.count = count;
        this.totalPage = totalPage;
        this.page = page;
        this.size = size;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public Integer getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }
}
