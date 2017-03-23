package com.jty;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.jty.sys.service.SysSer;

public class MainServiceTest {

    SysSer sysSer;
    @Before
    public void init() {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[] {"classpath:dubbo-client.xml"});
        context.start();
 
        sysSer = (SysSer)context.getBean("sysSer"); // 获取远程服务代理
    }


    @Test
    public void sysSerTest() {
        Map<String, Object> param = new HashMap<>();
        param.put("id", 2l);
        try {
            sysSer.getCompanyDb(param);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
