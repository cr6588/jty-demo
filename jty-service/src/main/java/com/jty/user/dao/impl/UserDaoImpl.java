package com.jty.user.dao.impl;
// Generated 2017-2-13 15:02:12 by Hibernate Tools 3.4.0.CR1

import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jty.user.bean.User;
import com.jty.user.dao.UserDao;
import com.jty.web.bean.PagerInfo;
import com.jty.web.util.UidUtil;

/**
 * Home object for domain model class User.
 * @author Hibernate Tools
 */
@Repository
public class UserDaoImpl implements UserDao {

    @Autowired
    private SessionFactory sessionFactory;
    private UidUtil uidUtil = new UidUtil("localhost:3306", "dev", "dev", "jty_uid_sequence", "user_id_sequence");;
    /*
     * (non-Javadoc)
     * @see com.cr.i18n.dao.impl.UserDao#getUserList(java.util.Map,
     * com.cr.web.bean.PagerInfo)
     */
    @Override
    public List<User> getUserList(Map<String, Object> param, PagerInfo pager) throws Exception {
        String hsql = "from User order by id desc";
        Query query = sessionFactory.getCurrentSession().createQuery(hsql);
        if (pager != null) {
            query.setMaxResults(pager.getSize());
            query.setFirstResult((pager.getPage() - 1) * pager.getSize());
        }
        List<User> i18s = query.list();
        return i18s;
    }

    /*
     * (non-Javadoc)
     * @see com.cr.i18n.dao.impl.UserDao#getUser(java.util.Map)
     */
    @Override
    public User getUser(Map<String, Object> param) throws Exception {
        String hsql = "from User where 1=1 ";
        if (param.get("id") != null) {
            hsql += " and id =:id";
        }
        if (param.get("username") != null) {
            hsql += " and username =:username";
        }
        if (param.get("password") != null) {
            hsql += " and password =:password";
        }
        Query query = sessionFactory.getCurrentSession().createQuery(hsql);
        if (param.get("id") != null) {
            query.setLong("id", (long) param.get("id"));
        }
        if (param.get("username") != null) {
            query.setString("username", (String) param.get("username"));
        }
        if (param.get("password") != null) {
            query.setString("password", (String) param.get("password"));
        }
        @SuppressWarnings("unchecked")
        List<User> Users = query.list();
        User User = null;
        if (Users != null) {
            User = Users.get(0);
        }
        return User;
    }

    /*
     * (non-Javadoc)
     * @see com.cr.i18n.dao.impl.UserDao#addUser(com.cr.web.bean.User)
     */
    @Override
    public void addUser(User user) throws Exception {
        user.setId(uidUtil.getUid());
        sessionFactory.getCurrentSession().save(user);
    }

    /*
     * (non-Javadoc)
     * @see com.cr.i18n.dao.impl.UserDao#updateUser(com.cr.web.bean.User)
     */
    @Override
    public void updateUser(User User) throws Exception {
        sessionFactory.getCurrentSession().update(User);
    }

    /*
     * (non-Javadoc)
     * @see com.cr.i18n.dao.impl.UserDao#deleteUser(java.lang.Long)
     */
    @Override
    public void deleteUser(Long id) throws Exception {
        Query query = sessionFactory.getCurrentSession().createQuery("delete User where id = ?");
        query.setLong(0, id);
        query.executeUpdate();
        query.executeUpdate();
    }

    /*
     * (non-Javadoc)
     * @see com.cr.i18n.dao.impl.UserDao#getUserListCnt(java.util.Map)
     */
    @Override
    public Integer getUserListCnt(Map<String, Object> param) throws Exception {
        String hsql = "select count(*) from User";
        Query query = sessionFactory.getCurrentSession().createQuery(hsql);
        Integer count = Integer.parseInt(query.uniqueResult().toString());
        return count;
    }
}
