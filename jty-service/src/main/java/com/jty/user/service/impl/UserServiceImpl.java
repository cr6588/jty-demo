package com.jty.user.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.jty.user.bean.User;
import com.jty.user.dao.UserDao;
import com.jty.user.service.UserSer;
import com.jty.web.bean.PagerInfo;

public class UserServiceImpl implements UserSer {

    @Autowired
    private UserDao userDao;

    public List<User> getUserList(Map<String, Object> param, PagerInfo pager) throws Exception {
        return userDao.getUserList(param, pager);
    }

    public User getUser(Map<String, Object> param) throws Exception {
        return userDao.getUser(param);
    }

    public void addUser(User user) throws Exception {
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
