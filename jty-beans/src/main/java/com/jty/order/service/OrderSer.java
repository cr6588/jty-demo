package com.jty.order.service;

import java.util.List;
import java.util.Map;

import com.jty.order.bean.Goods;
import com.jty.order.bean.Order;
import com.jty.order.bean.OrderGoods;
import com.jty.web.bean.PagerInfo;

public interface OrderSer {

    List<Order> getOrderList(Map<String, Object> param, PagerInfo pager) throws Exception;

    Order getOrder(Map<String, Object> param, Long userId) throws Exception;

    void addOrder(Order order) throws Exception;

    void updateOrder(Order order) throws Exception;

    void deleteOrder(Long id) throws Exception;

    Integer getOrderListCnt(Map<String, Object> params) throws Exception;

    List<Goods> getGoodsList(Map<String, Object> param, PagerInfo pager) throws Exception;

    Goods getGoods(Map<String, Object> param) throws Exception;

    void addGoods(Goods goods) throws Exception;

    void updateGoods(Goods goods) throws Exception;

    void deleteGoods(Long id) throws Exception;

    Integer getGoodsListCnt(Map<String, Object> params) throws Exception;

    void addOrderGoods(OrderGoods orderGoods) throws Exception;
}
