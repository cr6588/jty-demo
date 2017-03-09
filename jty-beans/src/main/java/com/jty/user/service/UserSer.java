package com.jty.user.service;

import java.util.List;
import java.util.Map;

import com.jty.user.bean.User;
import com.jty.web.bean.PagerInfo;

public interface UserSer {

    List<User> getUserList(Map<String, Object> param, PagerInfo pager) throws Exception;

    User getUser(Map<String, Object> param) throws Exception;

    void addUser(User user) throws Exception;

    void updateUser(User user) throws Exception;

    void deleteUser(Long id) throws Exception;

    Integer getUserListCnt(Map<String, Object> params) throws Exception;

}
