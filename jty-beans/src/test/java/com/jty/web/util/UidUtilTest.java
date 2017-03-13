package com.jty.web.util;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class UidUtilTest {

    @Test
    public void test() {
        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            Thread t = new Thread(new UidUtil());
            threads.add(t);
        }
        for (Thread thread : threads) {
            thread.start();
        }
//        Long uid;
//        UidUtil uidUtil = new UidUtil();
//        try {
//            uid = uidUtil.getUid("localhost:3306", "dev", "dev");
//            System.out.println("uid : "  + uid);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

}
