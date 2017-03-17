package com.jty.i18n.dao.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jty.i18n.dao.I18nDao;
import com.jty.web.bean.I18n;
import com.jty.web.bean.PagerInfo;

//@Repository
public class I18nDaoImpl implements I18nDao {

//    @Autowired
    private SessionFactory sessionFactory;

    public List<I18n> getI18nList(Map<String, Object> param, PagerInfo pager) throws Exception {
        String hsql = "";
        if (param.get("language") != null) {
            hsql = "from I18n i18n where i18n.language = ?";
        } else {
            hsql = "from I18n";
        }
        
        Query query = sessionFactory.getCurrentSession().createQuery(hsql);
        if (param.get("language") != null) {
            query.setParameter(0, param.get("language"));
        }
        if (pager != null) {
            query.setMaxResults(pager.getSize());
            query.setFirstResult((pager.getPage() - 1) * pager.getSize());
        }
        List<I18n> i18s = query.list();
        return i18s;
    }

    public I18n getI18n(Map<String, Object> param) throws Exception {
        String hsql = "";
        if (param.get("id") != null) {
            hsql = "from I18n i18n where i18n.id = ?";// +
                                                      // param.get("language");
        }
        Query query = sessionFactory.getCurrentSession().createQuery(hsql);
        if (param.get("id") != null) {
            query.setParameter(0, Long.parseLong(param.get("id").toString()));
        }
        @SuppressWarnings("unchecked")
        List<I18n> i18ns = query.list();
        I18n i18n = null;
        if (i18ns != null) {
            i18n = i18ns.get(0);
        }
        return i18n;
    }

    public void addI18n(I18n i18n) throws Exception {
        i18n.setCreateDate(new Date());
        sessionFactory.getCurrentSession().save(i18n);
    }

    public void updateI18n(I18n i18n) throws Exception {
        i18n.setUpdateDate(new Date());
        sessionFactory.getCurrentSession().update(i18n);
    }

    public void deleteI18n(Long id) throws Exception {
        Query query = sessionFactory.getCurrentSession().createQuery("delete I18n where id = ?");
        query.setLong(0, id);
        query.executeUpdate();
        query.executeUpdate();
    }

    public int getI18nListCnt(Map<String, Object> param) throws Exception {
        String hsql = "";
        if (param.get("language") != null) {
            hsql = "select count(*) from I18n i18n where i18n.language = ?";// +
                                                                            // param.get("language");
                                                                            // I18n貌似需要与bean名称一致
        } else {
            hsql = "select count(*) from I18n";
        }
        Query query = sessionFactory.getCurrentSession().createQuery(hsql);
        if (param.get("language") != null) {
            query.setParameter(0, param.get("language"));
        }
        Integer count = Integer.parseInt(query.uniqueResult().toString());
        return count;
    }

}
