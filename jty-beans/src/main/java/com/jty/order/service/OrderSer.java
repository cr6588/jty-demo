package com.jty.order.service;

import java.util.List;
import java.util.Map;

import com.jty.order.bean.Order;
import com.jty.web.bean.PagerInfo;

public interface OrderSer {

    List<Order> getOrderList(Map<String, Object> param, PagerInfo pager) throws Exception;

    Order getOrder(Map<String, Object> param) throws Exception;

    void addOrder(Order order) throws Exception;

    void updateOrder(Order order) throws Exception;

    void deleteOrder(Long id) throws Exception;

    Integer getOrderListCnt(Map<String, Object> params) throws Exception;

}
