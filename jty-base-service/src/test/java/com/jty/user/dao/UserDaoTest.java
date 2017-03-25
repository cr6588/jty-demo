package com.jty.user.dao;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.jty.web.bean.PagerInfo;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring-mybatis-db.xml", "classpath:spring-aop.xml", "classpath:log4j.xml" })
@Transactional
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
public class UserDaoTest {

    @Autowired
    UserDao userDao;

    public long getTime(int page) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        PagerInfo pager = new PagerInfo();
        pager.setPage(page);
        pager.setSize(10);
        long start = System.currentTimeMillis();
        userDao.getUserList(map, pager);
        long end = System.currentTimeMillis();
        return end - start;
    }
//   BigDecimal枚举常量用法摘要 
//   CEILING 
//   向正无限大方向舍入的舍入模式
//   DOWN
//   向零方向舍入的舍入模式
//   DOWN        
//   向零方向舍入的舍入模式
//   FLOOR
//   向负无限大方向舍入的舍入模式
//   HALF_DOWN
//   向最接近数字方向舍入的舍入模式，如果与两个相邻数字的距离相等，则向下舍入
//   HALF_EVEN 
//   向最接近数字方向舍入的舍入模式，如果与两个相邻数字的距离相等，则向相邻的偶数舍入
//   HALF_UP
//   向最接近数字方向舍入的舍入模式，如果与两个相邻数字的距离相等，则向上舍入
//   UNNECESSARY 
//   用于断言请求的操作具有精确结果的舍入模式，因此不需要舍入
//   UP 
//   远离零方向舍入的舍入模式
    @Test
    public void selectTest() {
        try {
            System.out.println(userDao.getUserListCnt(null));
            getTime(1); //第一次时间跨度大不取
            long totalTime = getTime(1) + getTime(1) + getTime(1);
            BigDecimal firstPageTime = new BigDecimal(totalTime).divide(new BigDecimal(3), 2 , RoundingMode.HALF_UP);
            System.out.println("三次查询总耗时：" + totalTime + ",平均值：" + firstPageTime.toString());
            int mediumPage = 50000;
            totalTime = getTime(mediumPage) + getTime(mediumPage) + getTime(mediumPage);
            BigDecimal mediumPageTime = new BigDecimal(totalTime).divide(new BigDecimal(3), 2 , RoundingMode.HALF_UP);
            System.out.println("三次查询总耗时：" + totalTime + ",平均值：" + mediumPageTime.toString());
            int lastPage = mediumPage * 2;
            totalTime = getTime(lastPage) + getTime(lastPage) + getTime(lastPage);
            BigDecimal lastPageTime = new BigDecimal(totalTime).divide(new BigDecimal(3), 2 , RoundingMode.HALF_UP);
            System.out.println("三次查询总耗时：" + totalTime + ",平均值：" + lastPageTime.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void userTest() {
        Map<String, Object> param = new HashMap<>();
        param.put("id", 1l);
        try {
            userDao.getUser(param);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
