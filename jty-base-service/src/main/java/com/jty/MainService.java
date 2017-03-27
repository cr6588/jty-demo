package com.jty;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MainService {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
            new String[] { "classpath:spring-mybatis-db.xml", "classpath:spring-aop.xml", "classpath:dubbo-server.xml", "classpath:log4j.xml" });
        context.start();
        String[] str = context.getBeanDefinitionNames();
        for (int i = 0; i < str.length; i++) {
            System.out.println(str[i]);
        }
        // Main.main(args);
        synchronized (MainService.class) {
            while (true) {
                try {
                    MainService.class.wait();
                } catch (Throwable e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
