package com.jty.order.dao;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
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
import com.jty.user.bean.User;
import com.jty.web.bean.PagerInfo;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring-db.xml", "classpath:spring-aop.xml" })
@Transactional
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true) //defaultRollback = false,addOrderTest 会报错
public class OrderDaoTest {

    @Autowired
    OrderDao orderDao;

    public long getTime(int page) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        PagerInfo pager = new PagerInfo();
        pager.setPage(page);
        pager.setSize(10);
        long start = System.currentTimeMillis();
        orderDao.getOrderList(map, pager);
        long end = System.currentTimeMillis();
        return end - start;
    }

    // BigDecimal枚举常量用法摘要
    // CEILING
    // 向正无限大方向舍入的舍入模式
    // DOWN
    // 向零方向舍入的舍入模式
    // DOWN
    // 向零方向舍入的舍入模式
    // FLOOR
    // 向负无限大方向舍入的舍入模式
    // HALF_DOWN
    // 向最接近数字方向舍入的舍入模式，如果与两个相邻数字的距离相等，则向下舍入
    // HALF_EVEN
    // 向最接近数字方向舍入的舍入模式，如果与两个相邻数字的距离相等，则向相邻的偶数舍入
    // HALF_UP
    // 向最接近数字方向舍入的舍入模式，如果与两个相邻数字的距离相等，则向上舍入
    // UNNECESSARY
    // 用于断言请求的操作具有精确结果的舍入模式，因此不需要舍入
    // UP
    // 远离零方向舍入的舍入模式
    @Test
    public void selectTest() {
        try {
            System.out.println(orderDao.getOrderListCnt(null));
            getTime(1); // 第一次时间跨度大不取
            long totalTime = getTime(1) + getTime(1) + getTime(1);
            BigDecimal firstPageTime = new BigDecimal(totalTime).divide(new BigDecimal(3), 2, RoundingMode.HALF_UP);
            System.out.println("三次查询总耗时：" + totalTime + ",平均值：" + firstPageTime.toString());
            int mediumPage = 50000;
            totalTime = getTime(mediumPage) + getTime(mediumPage) + getTime(mediumPage);
            BigDecimal mediumPageTime = new BigDecimal(totalTime).divide(new BigDecimal(3), 2, RoundingMode.HALF_UP);
            System.out.println("三次查询总耗时：" + totalTime + ",平均值：" + mediumPageTime.toString());
            int lastPage = mediumPage * 2;
            totalTime = getTime(lastPage) + getTime(lastPage) + getTime(lastPage);
            BigDecimal lastPageTime = new BigDecimal(totalTime).divide(new BigDecimal(3), 2, RoundingMode.HALF_UP);
            System.out.println("三次查询总耗时：" + totalTime + ",平均值：" + lastPageTime.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void updOrderTest() {
        Order in1 = new Order();
        in1.setId(8l);
        in1.setNo("test1_update");
        in1.setTotalMoney(11.2d);
        User u1 = new User();
        u1.setId(1l);
        u1.setUsername("user1");
        in1.setUser(u1);
        try {
            orderDao.updateOrder(in1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void addOrderTest() {
        Order in1 = new Order();
//        in1.setId(1l);
        in1.setNo("test1");
        in1.setTotalMoney(11.2d);
        User u1 = new User();
        u1.setId(4l);
        u1.setUsername("user1");
        in1.setUser(u1);
        List<OrderGoods> ogs =new ArrayList<>();
        OrderGoods og = new OrderGoods();
        Map<String, Object> param = new HashMap<>();
        param.put("id", "1");
        try {
//            Goods g = orderDao.getGoods(param);
            Goods g = new Goods();
            g.setId(6l);
            g.setUserId(1l);
//        og.setId(1l);
//            defaultRollback = false 会报错
            og.setGoods(g);
            ogs.add(og);
            in1.setOrderGoods(ogs);
//            orderDao.addOrder(in1);
            orderDao.addOrder(in1);
//            og.setOrderId(in1.getId());
            //orderDao.addOrderGoods(og);
//            Assert.assertEquals(1 , (long)orderDao.getOrderListCnt(null));
//            Assert.assertEquals(1, (long)orderDao.getOrderGoodsListCnt(null));
//            Date d = new Date();
//            System.out.println(d);
//            for(int i = 0; i < 10000; i++) {
//                in1 = new Order();
//                in1.setNo("test1");
//                in1.setTotalMoney(11.2d);
//                in1.setUser(u1);
//                orderDao.addOrder(in1);
//            }
//            Date end = new Date();
//            System.out.println(end);
//            System.out.println(end.getTime() - d.getTime());
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Test
    public void orderTest() {
        try {
            PagerInfo pager = new PagerInfo();
            Map<String, Object> paramMap = new HashMap<String, Object>();
            int cnt = orderDao.getOrderListCnt(paramMap);

            Order in1 = new Order();
            in1.setNo("test1");
            in1.setTotalMoney(11.2d);
            User u1 = new User();
            u1.setId(1l);
            u1.setUsername("user1");
            in1.setUser(u1);
            orderDao.addOrder(in1);
            Assert.assertNotNull(in1.getId());
            paramMap.put("id", in1.getId());

            Goods g = new Goods();
            g.setName("goods");
            OrderGoods og = new OrderGoods();
//            og.setOrderId(in1.getId());
            og.setGoods(g);
            orderDao.addGoods(g);
            orderDao.addOrderGoods(og);
            Assert.assertNotNull(g.getId());
            Assert.assertNotNull(og.getId());

            Order get1 = orderDao.getOrder(paramMap);
            Assert.assertNotNull(get1);
            Assert.assertEquals(in1.getId(), get1.getId());
            Assert.assertEquals(in1.getNo(), get1.getNo());
            Assert.assertEquals(in1.getTotalMoney(), get1.getTotalMoney());
            Assert.assertEquals(in1.getUser().getId(), get1.getUser().getId());
            Assert.assertNotNull(orderDao.getOrderGoodsByOrderId(get1.getId()));

            in1.setNo("test1——update");
            in1.setTotalMoney(11.3d);
            User u2 = new User();
            u2.setId(2l);
            in1.setUser(u2);
            orderDao.updateOrder(in1);

            get1 = orderDao.getOrder(paramMap);
            Assert.assertNotNull(get1);
            Assert.assertEquals(in1.getId(), get1.getId());
            Assert.assertEquals(in1.getNo(), get1.getNo());
            Assert.assertEquals(in1.getTotalMoney(), get1.getTotalMoney());
            Assert.assertEquals(in1.getUser().getId(), get1.getUser().getId());

            Order in2 = new Order();
            in2.setNo("test2");
            in2.setTotalMoney(11.5d);
            User u3 = new User();
            u3.setId(3l);
            in2.setUser(u3);
            orderDao.addOrder(in2);

            paramMap.remove("id");

            List<Order> list = orderDao.getOrderList(paramMap, null);
            Assert.assertEquals(list.size(), cnt + 2);

            orderDao.deleteOrder(in1.getId());

            Assert.assertEquals(cnt, orderDao.getOrderListCnt(paramMap) - 1);

            pager.setPage(1);
            pager.setSize(10);

            orderDao.getOrderList(paramMap, pager);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void addGoodsTest() {
        Goods goods = new Goods();
//        goods.setId(4l);
        goods.setName("3");
        goods.setSKU("1");
        goods.setPrice(3d);
        goods.setUserId(4l);
        try {
            orderDao.addGoods(goods);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Test
    public void updGoodsTest() {
        Goods goods = new Goods();
        goods.setId(4l);
        goods.setName("3");
        goods.setSKU("1");
        goods.setPrice(3d);
        goods.setUserId(4l);
        try {
            orderDao.updateGoods(goods);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
