package com.jty.order.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.jty.order.bean.Goods;
import com.jty.order.bean.Order;
import com.jty.order.dao.OrderDao;
import com.jty.order.service.OrderSer;
import com.jty.web.bean.PagerInfo;

public class OrderServiceImpl implements OrderSer {

    @Autowired
    private OrderDao orderDao;

    public List<Order> getOrderList(Map<String, Object> param, PagerInfo pager) throws Exception {
        return orderDao.getOrderList(param, pager);
    }

    public Order getOrder(Map<String, Object> param) throws Exception {
        return orderDao.getOrder(param);
    }

    public void addOrder(Order order) throws Exception {
        orderDao.addOrder(order);
    }

    public void updateOrder(Order order) throws Exception {
        orderDao.updateOrder(order);
    }

    public void deleteOrder(Long id) throws Exception {
        orderDao.deleteOrder(id);
    }

    public Integer getOrderListCnt(Map<String, Object> params) throws Exception {
        return orderDao.getOrderListCnt(params);
    }

    public List<Goods> getGoodsList(Map<String, Object> param, PagerInfo pager) throws Exception {
        return orderDao.getGoodsList(param, pager);
    }

    public Goods getGoods(Map<String, Object> param) throws Exception {
        return orderDao.getGoods(param);
    }

    public void addGoods(Goods goods) throws Exception {
        orderDao.addGoods(goods);
    }

    public void updateGoods(Goods goods) throws Exception {
        orderDao.updateGoods(goods);
    }

    public void deleteGoods(Long id) throws Exception {
        orderDao.deleteGoods(id);
    }

    public Integer getGoodsListCnt(Map<String, Object> params) throws Exception {
        return orderDao.getGoodsListCnt(params);
    }
}
