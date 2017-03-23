package com.jty.i18n.dao;

import java.util.List;
import java.util.Map;

import com.jty.web.bean.I18n;
import com.jty.web.bean.PagerInfo;

public interface I18nDao {
    public List<I18n> getI18nList(Map<String, Object> param, PagerInfo pager) throws Exception;

    public I18n getI18n(Map<String, Object> param) throws Exception;

    public void addI18n(I18n i18n) throws Exception;

    public void updateI18n(I18n i18n) throws Exception;

    public void deleteI18n(Long id) throws Exception;

    public int getI18nListCnt(Map<String, Object> params) throws Exception;

}
