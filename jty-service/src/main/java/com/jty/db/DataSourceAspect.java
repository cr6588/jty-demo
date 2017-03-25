package com.jty.db;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.aspectj.lang.JoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.interceptor.NameMatchTransactionAttributeSource;
import org.springframework.transaction.interceptor.TransactionAttribute;
import org.springframework.transaction.interceptor.TransactionAttributeSource;
import org.springframework.transaction.interceptor.TransactionInterceptor;
import org.springframework.util.PatternMatchUtils;
import org.springframework.util.ReflectionUtils;

import com.jty.sys.bean.CompanyDb;
import com.jty.sys.service.SysSer;
import com.jty.web.bean.Constant;
import com.jty.web.util.ReflectUtil;

public class DataSourceAspect {

    private List<String> slaveMethodPattern = new ArrayList<String>();

    private static final String[] defaultSlaveMethodStart = new String[] { "query", "find", "get" };

    private String[] slaveMethodStart;

    private DynamicDataSource dataSource;

    public DynamicDataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DynamicDataSource dataSource) {
        this.dataSource = dataSource;
    }

    private static Logger logger = LoggerFactory.getLogger(DataSourceAspect.class);

    @Autowired
    SysSer sysSer;
    /**
     * 读取事务管理中的策略
     * @param txAdvice
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public void setTxAdvice(TransactionInterceptor txAdvice) throws Exception {
        if (txAdvice == null) {
            // 没有配置事务管理策略
            return;
        }
        // 从txAdvice获取到策略配置信息
        TransactionAttributeSource transactionAttributeSource = txAdvice.getTransactionAttributeSource();
        if (!(transactionAttributeSource instanceof NameMatchTransactionAttributeSource)) {
            return;
        }
        // 使用反射技术获取到NameMatchTransactionAttributeSource对象中的nameMap属性值
        NameMatchTransactionAttributeSource matchTransactionAttributeSource = (NameMatchTransactionAttributeSource) transactionAttributeSource;
        Field nameMapField = ReflectionUtils.findField(NameMatchTransactionAttributeSource.class, "nameMap");
        nameMapField.setAccessible(true); // 设置该字段可访问
        // 获取nameMap的值
        Map<String, TransactionAttribute> map = (Map<String, TransactionAttribute>) nameMapField.get(matchTransactionAttributeSource);

        // 遍历nameMap
        for (Map.Entry<String, TransactionAttribute> entry : map.entrySet()) {
            if (!entry.getValue().isReadOnly()) {// 判断之后定义了ReadOnly的策略才加入到slaveMethodPattern
                continue;
            }
            slaveMethodPattern.add(entry.getKey());
        }
    }

    /**
     * 在进入Service方法之前执行
     * @param point 切面对象
     * @throws Exception 
     */
    public void before(JoinPoint point) throws Exception {
        // 获取到当前执行的方法名
        String methodName = point.getSignature().getName();
        if (slaveMethodPattern.isEmpty()) {
            // 当前Spring容器中没有配置事务策略，采用方法名匹配方式
            // isSlave = isSlave(methodName);
        } else {
            // 使用策略规则匹配
            for (String mappedName : slaveMethodPattern) {
                if (isMatch(methodName, mappedName)) {
                    Map<Object, Object> targetDataSources = (Map<Object, Object>) ReflectUtil.getFieldValue(dataSource, "targetDataSources");
                    if(targetDataSources.size() == 0) {
                        DataSource ds = null;
                        try {
                            Map<String, Object> conditionMap = new HashMap<>();
                            conditionMap.put("module", Constant.Module.Order.index);
                            List<CompanyDb> comDbs = sysSer.getCompanyDbList(conditionMap, null);
                            if(comDbs != null && !comDbs.isEmpty()) {
                                for (CompanyDb comDb : comDbs) {
                                    DynamicDataSource.loadComDb(comDb, sysSer);
                                }
                                ds = DynamicDataSource.initShardingDataSource(sysSer);
                            }
                            if(ds != null) {
                                targetDataSources.put(DynamicDataSourceHolder.MASTER, ds);
                                dataSource.setDefaultTargetDataSource(ds);
                            }
                            dataSource.afterPropertiesSet();
                        } catch (Exception e) {
                            logger.error("初始化订单模块数据源失败!!!!!");
                            e.printStackTrace();
                            throw e;
                        }
                    }
                    break;
                }
            }
        }
    }

    /**
     * 通配符匹配 Return if the given method name matches the mapped name.
     * <p>
     * The default implementation checks for "xxx*", "*xxx" and "*xxx*" matches,
     * as well as direct equality. Can be overridden in subclasses.
     * @param methodName the method name of the class
     * @param mappedName the name in the descriptor
     * @return if the names match
     * @see org.springframework.util.PatternMatchUtils#simpleMatch(String,
     *      String)
     */
    protected boolean isMatch(String methodName, String mappedName) {
        return PatternMatchUtils.simpleMatch(mappedName, methodName);
    }

    /**
     * 用户指定slave的方法名前缀
     * @param slaveMethodStart
     */
    public void setSlaveMethodStart(String[] slaveMethodStart) {
        this.slaveMethodStart = slaveMethodStart;
    }

    public String[] getSlaveMethodStart() {
        if (this.slaveMethodStart == null) {
            // 没有指定，使用默认
            return defaultSlaveMethodStart;
        }
        return slaveMethodStart;
    }
}
