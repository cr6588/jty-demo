package com.jty.user.dao;

import java.util.List;
import java.util.Map;

import com.jty.user.bean.User;
import com.jty.web.bean.PagerInfo;

public interface UserDao {

    List<User> getUserList(Map<String, Object> param, PagerInfo pager) throws Exception;

    User getUser(Map<String, Object> param) throws Exception;

    void addUser(User user) throws Exception;

    void updateUser(User User) throws Exception;

    void deleteUser(Long id) throws Exception;

    int getUserListCnt(Map<String, Object> param) throws Exception;

}
