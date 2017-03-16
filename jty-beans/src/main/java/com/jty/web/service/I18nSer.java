package com.jty.web.service;

import java.util.List;
import java.util.Map;

import com.jty.web.bean.I18n;
import com.jty.web.bean.PagerInfo;

public interface I18nSer {

    List<I18n> getI18nList(Map<String, Object> param, PagerInfo pager) throws Exception;

    I18n getI18n(Map<String, Object> param) throws Exception;

    void addI18n(I18n i18n) throws Exception;

    void updateI18n(I18n i18n) throws Exception;

    void deleteI18n(Long no) throws Exception;

    public int getI18nListCnt(Map<String, Object> params) throws Exception;
}
