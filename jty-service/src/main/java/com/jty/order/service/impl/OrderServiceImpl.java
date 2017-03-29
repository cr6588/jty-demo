package com.jty.order.service.impl;

import java.util.HashMap;
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

@Service("orderSer")
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
            param.put("orderId", param.get("id"));
            param.remove("id");
            List<OrderGoods> og = orderDao.getOrderGoodsList(param, null);
            o.setOrderGoods(og);
        }
        return o;
    }

    public Long addOrder(Order order) throws Exception {
        order.setId(orderIdUtil.getUid());
        orderDao.addOrder(order);
        //share-jdbc不支持特殊INSERT 每条INSERT语句只能插入一条数据，不支持VALUES后有多行数据的语句
        if(order.getOrderGoods() != null) {
//            当try语句块运行结束时，hintManager 会被自动关闭。这是因为hintManager 实现了java中的java.lang.AutoCloseable接口。所有实现了这个接口的类都可以在try-with-resources结构中使用。
//            try(HintManager hintManager = HintManager.getInstance()) {
//                hintManager.addDatabaseShardingValue("order_goods", "user_id", order.getUser().getId());
                for (OrderGoods orderGoods : order.getOrderGoods()) {
                    orderGoods.setOrderId(order.getId());
                    orderGoods.setUserId(order.getUser().getId());
                    orderDao.addOrderGoods(orderGoods);
                }
//            }
        }
        return order.getId();
    }

    public void updateOrder(Order order) throws Exception {
        Map<String, Object> param = new HashMap<>();
        param.put("orderId", order.getId());
        param.put("userId", order.getUser().getId());
        orderDao.deleteOrderGoods(param);
        if(order.getOrderGoods() != null) {
            for (OrderGoods orderGoods : order.getOrderGoods()) {
                orderGoods.setOrderId(order.getId());
                orderGoods.setUserId(order.getUser().getId());
                orderDao.addOrderGoods(orderGoods);
            }
        }
        orderDao.updateOrder(order);
    }

    public void deleteOrder(Map<String, Object> param) throws Exception {
        orderDao.deleteOrder(param);
        param.put("orderId", param.get("id"));
        param.remove("id");
        orderDao.deleteOrderGoods(param);
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

    public void deleteGoods(Map<String, Object> param) throws Exception {
        orderDao.deleteGoods(param);
    }

    public Integer getGoodsListCnt(Map<String, Object> params) throws Exception {
        return orderDao.getGoodsListCnt(params);
    }

    @Override
    public void addOrderGoods(OrderGoods orderGoods) throws Exception {
        orderDao.addOrderGoods(orderGoods);
    }
    
}
