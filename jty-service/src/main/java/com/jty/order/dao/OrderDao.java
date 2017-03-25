package com.jty.order.dao;

import java.util.List;
import java.util.Map;

import com.jty.order.bean.Goods;
import com.jty.order.bean.Order;
import com.jty.order.bean.OrderGoods;
import com.jty.web.bean.PagerInfo;

public interface OrderDao {

    //  order
    List<Order> getOrderList(Map<String, Object> param, PagerInfo pager) throws Exception;

    Order getOrder(Map<String, Object> param) throws Exception;

    void addOrder(Order order) throws Exception;

    void updateOrder(Order Order) throws Exception;

    void deleteOrder(Map<String, Object> param) throws Exception;

    Integer getOrderListCnt(Map<String, Object> param) throws Exception;

    //  orderGoods
    void addOrderGoods(OrderGoods orderGoods) throws Exception;

    void addOrderGoodsList(Order order) throws Exception;

    void updateOrderGoods(OrderGoods orderGoods) throws Exception;

    void deleteOrderGoods(Map<String, Object> param) throws Exception;

    List<OrderGoods> getOrderGoodsList(Map<String, Object> param, PagerInfo pager) throws Exception;

    Integer getOrderGoodsListCnt(Map<String, Object> param) throws Exception;

    //goods
    List<Goods> getGoodsList(Map<String, Object> param, PagerInfo pager) throws Exception;

    Goods getGoods(Map<String, Object> param) throws Exception;

    void addGoods(Goods goods) throws Exception;

    void updateGoods(Goods goods) throws Exception;

    void deleteGoods(Map<String, Object> param) throws Exception;

    Integer getGoodsListCnt(Map<String, Object> param) throws Exception;

}
