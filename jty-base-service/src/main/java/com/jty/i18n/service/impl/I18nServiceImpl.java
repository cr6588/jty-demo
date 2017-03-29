package com.jty.i18n.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jty.i18n.dao.I18nDao;
import com.jty.web.bean.I18n;
import com.jty.web.bean.PagerInfo;
import com.jty.web.service.I18nSer;


@Service
public class I18nServiceImpl implements I18nSer {

    @Autowired
    private I18nDao i18nDao;

    /* (non-Javadoc)
     * @see com.jty.i18n.service.I18nSer#getI18nList(java.util.Map, com.jty.web.bean.PagerInfo)
     */
    @Override
    public List<I18n> getI18nList(Map<String, Object> param, PagerInfo pager) throws Exception{
        return i18nDao.getI18nList(param, pager);
    }

    /* (non-Javadoc)
     * @see com.jty.i18n.service.I18nSer#getI18n(java.util.Map)
     */
    @Override
    public I18n getI18n(Map<String, Object> param) throws Exception {
        return i18nDao.getI18n(param);
    }

    /* (non-Javadoc)
     * @see com.jty.i18n.service.I18nSer#addI18n(com.jty.web.bean.I18n)
     */
    @Override
    public void addI18n(I18n i18n) throws Exception {
        i18nDao.addI18n(i18n);
    }

    /* (non-Javadoc)
     * @see com.jty.i18n.service.I18nSer#updateI18n(com.jty.web.bean.I18n)
     */
    @Override
    public void updateI18n(I18n i18n) throws Exception {
        i18nDao.updateI18n(i18n);
    }

    /* (non-Javadoc)
     * @see com.jty.i18n.service.I18nSer#deleteI18n(java.lang.Long)
     */
    @Override
    public void deleteI18n(Long no) throws Exception {
        i18nDao.deleteI18n(no);
    }

    public int getI18nListCnt(Map<String, Object> params) throws Exception {
        return i18nDao.getI18nListCnt(params);
    }
}
