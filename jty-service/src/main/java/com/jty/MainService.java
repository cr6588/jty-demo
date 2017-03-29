package com.jty;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MainService {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[] {"classpath:spring-mybatis-db.xml", "classpath:spring-aop.xml", "classpath:log4j.xml" });
        context.start();
        String[] str = context.getBeanDefinitionNames();
        for (int i = 0; i < str.length; i++) {
            System.out.println(str[i]);
        }
//        try {
//            System.in.read(); //linux下使用nohup java -jar jty-order-service.jar &命令运行时会报错
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
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
