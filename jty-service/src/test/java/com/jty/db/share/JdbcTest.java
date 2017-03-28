package com.jty.db.share;

import java.io.PrintWriter;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import javax.sql.DataSource;

import org.junit.Test;

import com.dangdang.ddframe.rdb.sharding.api.ShardingDataSourceFactory;
import com.dangdang.ddframe.rdb.sharding.api.rule.DataSourceRule;
import com.dangdang.ddframe.rdb.sharding.api.rule.ShardingRule;
import com.dangdang.ddframe.rdb.sharding.api.rule.TableRule;
import com.dangdang.ddframe.rdb.sharding.api.strategy.database.DatabaseShardingStrategy;
import com.dangdang.ddframe.rdb.sharding.api.strategy.table.TableShardingStrategy;
import com.jty.db.DataSourceAspect;
import com.jty.db.strategy.table.ModuloDatabaseShardingAlgorithm;
import com.jty.db.strategy.table.ModuloTableShardingAlgorithm;
import com.jty.web.util.FileUtil;
import com.jty.web.util.JDBC;

public class JdbcTest {

    static class BasicDataSource implements DataSource {
//        result.setDriverClassName(com.mysql.jdbc.Driver.class.getName());
//        result.setUrl(String.format("jdbc:mysql://localhost:3306/%s", dataSourceName));
//        result.setUsername("root");
//        result.setPassword("");
        String driverClassName;
        String url;
        String username;
        String password;
        
        public String getDriverClassName() {
            return driverClassName;
        }

        public void setDriverClassName(String driverClassName) {
            this.driverClassName = driverClassName;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        @Override
        public PrintWriter getLogWriter() throws SQLException {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public void setLogWriter(PrintWriter out) throws SQLException {
            // TODO Auto-generated method stub
            
        }

        @Override
        public void setLoginTimeout(int seconds) throws SQLException {
            // TODO Auto-generated method stub
            
        }

        @Override
        public int getLoginTimeout() throws SQLException {
            // TODO Auto-generated method stub
            return 0;
        }

        @Override
        public Logger getParentLogger() throws SQLFeatureNotSupportedException {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public <T> T unwrap(Class<T> iface) throws SQLException {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public boolean isWrapperFor(Class<?> iface) throws SQLException {
            // TODO Auto-generated method stub
            return false;
        }

        @Override
        public Connection getConnection() throws SQLException {
            try {
                Class.forName(getDriverClassName());
                Connection con = DriverManager.getConnection(url, username, password);
                return con;
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        public Connection getConnection(String username, String password) throws SQLException {
            return null;
        }
    }
    private static DataSource createDataSource(final String dataSourceName) {
        BasicDataSource result = new BasicDataSource();
        result.setDriverClassName(com.mysql.jdbc.Driver.class.getName());
        result.setUrl(String.format("jdbc:mysql://localhost:3306/%s", dataSourceName));
        result.setUsername("dev");
        result.setPassword("dev");
        return result;
    }

    @Test
    public void test() {
        Map<String, DataSource> dataSourceMap = new HashMap<>(2);
        dataSourceMap.put("jty_order_0", createDataSource("jty_order_0"));
        dataSourceMap.put("jty_order_1", createDataSource("jty_order_1"));
        DataSourceRule dataSourceRule = new DataSourceRule(dataSourceMap);
        TableRule orderTableRule = TableRule.builder("t_order")
            .actualTables(Arrays.asList("t_order_0", "t_order_1"))
            .dataSourceRule(dataSourceRule)
            .build();
        ShardingRule shardingRule = ShardingRule.builder()
                  .dataSourceRule(dataSourceRule)
                  .tableRules(Arrays.asList(orderTableRule))
                  .databaseShardingStrategy(new DatabaseShardingStrategy("user_id", new ModuloDatabaseShardingAlgorithm()))
                  .tableShardingStrategy(new TableShardingStrategy("id", new ModuloTableShardingAlgorithm()))
                  .build();
        DataSource dataSource = ShardingDataSourceFactory.createDataSource(shardingRule);
        String sql = "insert into  t_order(id, no , total_money, user_id) values(3,1,1,1)";
        try  {
            Connection conn = dataSource.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.executeUpdate();
            sql = "update t_order set no=1, total_money=11, user_id=1 where id=1";
            conn = dataSource.getConnection();
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void createDbTest() {
        String url = "localhost:3308", dbUsername = "dev", password = "dev";
        JDBC jdbc = new JDBC("jdbc:mysql://" + url + "?allowMultiQueries=true&amp;useUnicode=true&amp;characterEncoding=UTF-8", dbUsername, password);
        String databaseName = "jty_order_0";
        if(true) {   //判读数据库是否存在
            String sqlPathPrefix = DataSourceAspect.class.getResource("/").getPath() + "sql/";
            String sql = FileUtil.readTxtFile2StrByStringBuilder(sqlPathPrefix + "jty_goods.sql");
            sql += FileUtil.readTxtFile2StrByStringBuilder(sqlPathPrefix + "jty_order.sql");
            sql += FileUtil.readTxtFile2StrByStringBuilder(sqlPathPrefix + "jty_uid_sequence.sql");
            try {
                jdbc.execute(sql);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } //创建数据库，验证数据库是否创建成功
        }
    }

//    @Test
//    public void initShardingDataSourceTest() {
//        DataSourceAspect dataSourceAspect = new DataSourceAspect();
//        String url = "localhost:3308", dbUsername = "dev", password = "dev";
//        DataSource ds = dataSourceAspect.initShardingDataSource(url, dbUsername, password);
//        String sql = "insert into  t_order(id, no , total_money, user_id) values(10,1,1,1)";
//        try  {
//            Connection conn = ds.getConnection();
//            PreparedStatement preparedStatement = conn.prepareStatement(sql);
//            preparedStatement.executeUpdate();
//            sql = "update t_order set no=1, total_money=11, user_id=1 where id=1";
//            conn = ds.getConnection();
//            preparedStatement = conn.prepareStatement(sql);
//            preparedStatement.executeUpdate();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    @Test
    public void existDbTest() {
        String url = "localhost:3308", dbUsername = "dev", password = "dev";
        String databaseName = "jty_goods_0";
        JDBC jdbc = new JDBC("jdbc:mysql://" + url + "?allowMultiQueries=true&amp;useUnicode=true&amp;characterEncoding=UTF-8", dbUsername, password);
        if(!jdbc.existDb(databaseName)) {   //判读数据库是否存在
            String sqlPathPrefix = this.getClass().getResource("/").getPath() + "sql/";
            String sql = FileUtil.readTxtFile2StrByStringBuilder(sqlPathPrefix + "jty_goods.sql");
            sql += FileUtil.readTxtFile2StrByStringBuilder(sqlPathPrefix + "jty_order.sql");
            sql += FileUtil.readTxtFile2StrByStringBuilder(sqlPathPrefix + "jty_uid_sequence.sql");
            System.out.println(sql);
            try {
                jdbc.execute(sql);
            } catch (Exception e) {
                e.printStackTrace();
            } //创建数据库，验证数据库是否创建成功
        }
    }

    @Test
    public void excuteTest() {
        JDBC jdbc = new JDBC("jdbc:mysql://localhost:3306/jty_basic?allowMultiQueries=true&amp;useUnicode=true&amp;characterEncoding=UTF-8", "dev", "dev");
        String sql ="{call jty_basic.create_order_table (?, ?)}"; //存储过程调用，模块库所在的mysql必须含有存储过程
        try {
            CallableStatement callableStatement = jdbc.getCstmt(sql);
            callableStatement.setString(1, "jty_order_x");
            callableStatement.setString(2, "1");  
            callableStatement.execute();  
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
