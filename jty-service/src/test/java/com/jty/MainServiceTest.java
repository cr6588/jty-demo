package com.jty;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.jty.db.DynamicDataSourceHolder;
import com.jty.order.service.OrderSer;
import com.jty.sys.service.SysSer;

public class MainServiceTest {

    OrderSer orderSer;
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
      
        int hello;
        try {
            Map<String, Object> map = new HashMap<>();
            hello = orderSer.getGoodsListCnt(map);
            System.out.println("*******************************" +  hello ); // 显示调用结果
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } // 执行远程方法
 
    }

    @Test
    public void orderSerTest() {
        Map<String, Object> param = new HashMap<>();
        param.put("id", 23l);
        Long userId = 1l;
        param.put("userId", userId);
        try {
            orderSer.getOrder(param);
            orderSer.getGoods(param);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void sysSerTest() {
        Map<String, Object> param = new HashMap<>();
        param.put("id", 2l);
        try {
            sysSer.getCompanyDb(param);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
