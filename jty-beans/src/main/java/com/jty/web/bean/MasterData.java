package com.jty.web.bean;

import java.io.Serializable;
import java.util.Date;

public class MasterData implements Serializable {
    private static final long serialVersionUID = 1L;

    protected Long id;
    protected String no;
    protected Boolean active = true;
    protected Boolean isDelete = false;
    protected Date createDate;
    protected Date updateDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public Boolean getActive() {
        if (active == null) {
            return false;
        } else {
            return active;
        }
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public Boolean getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Boolean isDelete) {
        this.isDelete = isDelete;
    }
}
