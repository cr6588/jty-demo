package com.jty.user.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.jty.user.bean.User;
import com.jty.user.dao.UserDao;
import com.jty.user.service.UserSer;
import com.jty.web.bean.PagerInfo;
import com.jty.web.util.UidUtil;

public class UserServiceImpl implements UserSer {

    private UidUtil uidUtil = new UidUtil("localhost:3306", "dev", "dev", "jty_uid_sequence", "user_id_sequence");;
    @Autowired
    private UserDao userDao;

    public List<User> getUserList(Map<String, Object> param, PagerInfo pager) throws Exception {
        return userDao.getUserList(param, pager);
    }

    public User getUser(Map<String, Object> param) throws Exception {
        return userDao.getUser(param);
    }

    public void addUser(User user) throws Exception {
        user.setId(uidUtil.getUid());
        userDao.addUser(user);
    }

    public void updateUser(User user) throws Exception {
        userDao.updateUser(user);
    }

    public void deleteUser(Long id) throws Exception {
        userDao.deleteUser(id);
    }

    public Integer getUserListCnt(Map<String, Object> params) throws Exception {
        return userDao.getUserListCnt(params);
    }
}
