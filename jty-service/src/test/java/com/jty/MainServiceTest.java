package com.jty;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.jty.user.service.UserSer;

public class MainServiceTest {

    @Test
    public void test() {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[] {"classpath:dubbo-client.xml"});
        context.start();
 
        UserSer demoService = (UserSer)context.getBean("userSer"); // 获取远程服务代理
        int hello;
        try {
            hello = demoService.getUserListCnt(null);
            System.out.println("*******************************" +  hello ); // 显示调用结果
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } // 执行远程方法
 
    }

}
