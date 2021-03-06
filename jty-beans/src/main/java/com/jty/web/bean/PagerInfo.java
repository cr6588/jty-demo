package com.jty.web.bean;

import java.io.Serializable;

/**
 * 分页访问参数类
 * @author Mx
 */
public class PagerInfo implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    /**
     * 每页数量
     */
    Integer size = 10;
    /**
     * 页码
     */
    Integer page;
    Long total;

    public PagerInfo() {
        super();
    }

    public PagerInfo(Integer size, Integer page) {
        super();
        this.size = size;
        this.page = page;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public Boolean supportPage() {
        if (this.size == null || this.page == null) {
            return false;
        }
        return true;
    }
}
