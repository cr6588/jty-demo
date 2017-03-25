package com.jty.order;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.jty.order.bean.Goods;
import com.jty.order.bean.Order;
import com.jty.order.bean.OrderGoods;
import com.jty.order.dao.OrderDao;
import com.jty.order.service.OrderSer;
import com.jty.sys.service.SysSer;
import com.jty.user.bean.User;
import com.jty.web.bean.PagerInfo;

public class OrderSerTest {

    private OrderSer orderSer;
    SysSer sysSer;
    @Before
    public void init() {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[] {"classpath:dubbo-client.xml"});
        String[] str = context.getBeanDefinitionNames();
        for (int i = 0; i < str.length; i++) {
            System.out.println(str[i]);
        }
        orderSer = (OrderSer)context.getBean("orderSer"); // 获取远程服务代理
        sysSer = (SysSer)context.getBean("sysSer"); // 获取远程服务代理
    }

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

    @Test
    public void orderTest() {
        try {
            User u1 = new User();
            u1.setId(1l);
            u1.setUsername("user1");
            PagerInfo pager = new PagerInfo();
            Map<String, Object> paramMap = new HashMap<String, Object>();
            paramMap.put("userId", u1.getId());

            int cnt = orderSer.getOrderListCnt(paramMap);
            Order in1 = new Order();
            in1.setUser(u1);
            in1.setNo("test1");
            in1.setTotalMoney(11.2d);

            Goods g = new Goods();
            g.setId(11l);
            OrderGoods orderGoods = new OrderGoods();
            orderGoods.setGoods(g);
            orderGoods.setUserId(u1.getId());
            List<OrderGoods> orderGoodsList = new ArrayList<>();
            orderGoodsList.add(orderGoods);
            in1.setOrderGoods(orderGoodsList);
            in1.setId(orderSer.addOrder(in1));

            Assert.assertNotNull(in1.getId());
            paramMap.put("id", in1.getId());

            Order get1 = orderSer.getOrder(paramMap);
            Assert.assertNotNull(get1);
            Assert.assertEquals(in1.getId(), get1.getId());
            Assert.assertEquals(in1.getNo(), get1.getNo());
            Assert.assertEquals(in1.getTotalMoney(), get1.getTotalMoney());
            Assert.assertNotNull(get1.getOrderGoods());
            Assert.assertNotEquals(0, get1.getOrderGoods().size());

            in1.setNo("test1——update");
            in1.setTotalMoney(11.3d);
            orderSer.updateOrder(in1);

            get1 = orderSer.getOrder(paramMap);
            Assert.assertNotNull(get1);
            Assert.assertEquals(in1.getId(), get1.getId());
            Assert.assertEquals(in1.getNo(), get1.getNo());
            Assert.assertEquals(in1.getTotalMoney(), get1.getTotalMoney());
            Assert.assertNotNull(get1.getOrderGoods());
            Assert.assertNotEquals(0, get1.getOrderGoods().size());

            Order in2 = new Order();
            in2.setNo("test2");
            in2.setTotalMoney(11.5d);
            in2.setUser(u1);
            in2.setOrderGoods(orderGoodsList);
            orderSer.addOrder(in2);

            paramMap.remove("id");

            List<Order> list = orderSer.getOrderList(paramMap, null);
            Assert.assertEquals(list.size(), cnt + 2);

            paramMap.clear();
            paramMap.put("id", in1.getId());
            paramMap.put("userId", in1.getUser().getId());
            orderSer.deleteOrder(paramMap);
            paramMap.clear();
            paramMap.put("userId", in1.getUser().getId());
            Assert.assertEquals(cnt, orderSer.getOrderListCnt(paramMap) - 1);

            pager.setPage(1);
            pager.setSize(10);

            orderSer.getOrderList(paramMap, pager);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
