package com.jty.order.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jty.order.bean.Goods;
import com.jty.order.bean.Order;
import com.jty.order.bean.OrderGoods;
import com.jty.order.dao.OrderDao;
import com.jty.order.service.OrderSer;
import com.jty.web.bean.PagerInfo;
import com.jty.web.util.UidUtil;

public class OrderServiceImpl implements OrderSer {

    @Autowired
    private OrderDao orderDao;

    private UidUtil orderIdUtil = new UidUtil("localhost:3306", "dev", "dev", "jty_uid_sequence", "order_id_sequence");
    private UidUtil goodsIdUtil = new UidUtil("localhost:3306", "dev", "dev", "jty_uid_sequence", "goods_id_sequence");

    public List<Order> getOrderList(Map<String, Object> param, PagerInfo pager) throws Exception {
        return orderDao.getOrderList(param, pager);
    }

    public Order getOrder(Map<String, Object> param) throws Exception {
        Order o = orderDao.getOrder(param);
        if(o != null) {
            List<OrderGoods> og = orderDao.getOrderGoodsByOrderId(o.getId());
            o.setOrderGoods(og);
        }
        return o;
    }

    public void addOrder(Order order) throws Exception {
        order.setId(orderIdUtil.getUid());
        orderDao.addOrder(order);
        orderDao.addOrderGoodsList(order);
    }

    public void updateOrder(Order order) throws Exception {
        orderDao.deleteOrderGoods(order.getId());
        orderDao.addOrderGoodsList(order);
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
        goods.setId(goodsIdUtil.getUid());
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

    @Override
    public void addOrderGoods(OrderGoods orderGoods) throws Exception {
        orderDao.addOrderGoods(orderGoods);
    }
    
}
