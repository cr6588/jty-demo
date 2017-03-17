package com.jty.order;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.jty.order.bean.Goods;
import com.jty.order.bean.Order;
import com.jty.order.bean.OrderGoods;
import com.jty.order.dao.OrderDao;
import com.jty.order.service.OrderSer;
import com.jty.user.bean.User;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring-db.xml", "classpath:spring-aop.xml" })
@Transactional
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false) //defaultRollback = false,addOrderTest 会报错
public class OrderSerTest {

    @Autowired
    private OrderSer orderSer;
    
    @Autowired
    private OrderDao orderDao;

    @Test
    public void test() {
        Order in1 = new Order();
        in1.setNo("test1");
        in1.setTotalMoney(11.2d);
        User u1 = new User();
        u1.setId(1l);
        u1.setUsername("user1");
        in1.setUser(u1);
        OrderGoods orderGoods = new OrderGoods();
        Goods good = new Goods();
        good.setId(5l);
        orderGoods.setOrderId(1l);
        orderGoods.setNum(111);
        orderGoods.setGoods(good);
        List<OrderGoods> orderGoodses = new ArrayList<>();
        orderGoodses.add(orderGoods);
        in1.setOrderGoods(orderGoodses);
        try {
            orderSer.addOrder(in1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
