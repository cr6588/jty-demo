package com.jty.order.dao.impl;
// Generated 2017-2-13 15:02:12 by Hibernate Tools 3.4.0.CR1

import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.SessionFactory;

import com.jty.order.bean.Goods;
import com.jty.order.bean.Order;
import com.jty.order.bean.OrderGoods;
import com.jty.order.dao.OrderDao;
import com.jty.web.bean.PagerInfo;
import com.jty.web.util.UidUtil;

/**
 * Home object for domain model class Order.
 * @author Hibernate Tools
 */
//@Repository
public class OrderDaoImpl implements OrderDao {

//    @Autowired
    private SessionFactory sessionFactory;

    private UidUtil orderIdUtil = new UidUtil("localhost:3306", "dev", "dev", "jty_uid_sequence", "order_id_sequence");
    private UidUtil goodsIdUtil = new UidUtil("localhost:3306", "dev", "dev", "jty_uid_sequence", "goods_id_sequence");

    /*
     * (non-Javadoc)
     * @see com.cr.i18n.dao.impl.OrderDao#getOrderList(java.util.Map,
     * com.cr.web.bean.PagerInfo)
     */
    @Override
    public List<Order> getOrderList(Map<String, Object> param, PagerInfo pager) throws Exception {
        String hsql = "from Order order by id desc";
        Query query = sessionFactory.getCurrentSession().createQuery(hsql);
        if (pager != null) {
            query.setMaxResults(pager.getSize());
            query.setFirstResult((pager.getPage() - 1) * pager.getSize());
        }
        List<Order> i18s = query.list();
        return i18s;
    }

    /*
     * (non-Javadoc)
     * @see com.cr.i18n.dao.impl.OrderDao#getOrder(java.util.Map)
     */
    @Override
    public Order getOrder(Map<String, Object> param) throws Exception {
        String hsql = "from Order where 1=1";
        if (param.get("id") != null) {
            hsql += " and id =:id";
        }
        if (param.get("no") != null) {
            hsql += " and no =:no";
        }
        if (param.get("userId") != null) {
            hsql += " and user.id =:user.id";
        }
        Query query = sessionFactory.getCurrentSession().createQuery(hsql);
        if (param.get("id") != null) {
            query.setLong("id", Long.parseLong(param.get("id").toString()));
        }
        if (param.get("no") != null) {
            query.setString("no", (String) param.get("no"));
        }
        if (param.get("userId") != null) {
            query.setString("user.id", (String) param.get("user.id"));
        }
        @SuppressWarnings("unchecked")
        List<Order> Orders = query.list();
        Order Order = null;
        if (Orders != null) {
            Order = Orders.get(0);
        }
        return Order;
    }

    /*
     * (non-Javadoc)
     * @see com.cr.i18n.dao.impl.OrderDao#addOrder(com.cr.web.bean.Order)
     */
    @Override
    public void addOrder(Order order) throws Exception {
        // TODO 后期改成配置
        order.setId(orderIdUtil.getUid());
        sessionFactory.getCurrentSession().save(order);
    }

    /*
     * (non-Javadoc)
     * @see com.cr.i18n.dao.impl.OrderDao#updateOrder(com.cr.web.bean.Order)
     */
    @Override
    public void updateOrder(Order order) throws Exception {
        sessionFactory.getCurrentSession().update(order);
    }

    /*
     * (non-Javadoc)
     * @see com.cr.i18n.dao.impl.OrderDao#deleteOrder(java.lang.Long)
     */
    @Override
    public void deleteOrder(Map<String, Object> param) throws Exception {
        Query query = sessionFactory.getCurrentSession().createQuery("delete Order where id = ?");
        query.setLong(0, (Long)param.get("id"));
        query.executeUpdate();
    }

    /*
     * (non-Javadoc)
     * @see com.cr.i18n.dao.impl.OrderDao#getOrderListCnt(java.util.Map)
     */
    @Override
    public Integer getOrderListCnt(Map<String, Object> param) throws Exception {
        String hsql = "select count(*) from Order";
        Query query = sessionFactory.getCurrentSession().createQuery(hsql);
        Integer count = Integer.parseInt(query.uniqueResult().toString());
        return count;
    }

    @Override
    public void addOrderGoods(OrderGoods orderGoods) throws Exception {
        sessionFactory.getCurrentSession().save(orderGoods);
    }

    @Override
    public void addOrderGoodsList(Order order) throws Exception {
        if(order == null) {
            return;
        }
        List<OrderGoods> orderGoodsList = order.getOrderGoods();
        if(orderGoodsList == null) {
            return;
        }
        for (OrderGoods orderGoods : orderGoodsList) {
            orderGoods.setOrderId(order.getId());
            sessionFactory.getCurrentSession().save(orderGoods);
        }
    }

    @Override
    public void updateOrderGoods(OrderGoods orderGoods) throws Exception {
        sessionFactory.getCurrentSession().update(orderGoods);
    }

    @Override
    public void deleteOrderGoods(Map<String, Object> param) throws Exception {
        Query query = sessionFactory.getCurrentSession().createQuery("delete OrderGoods where orderId = ?");
        query.setLong(0, (Long)param.get("id"));
        query.executeUpdate();
    }

    @Override
    public List<OrderGoods> getOrderGoodsList(Map<String, Object> param, PagerInfo pager) throws Exception {
        String hsql = "from OrderGoods where  orderId =: orderId order by id desc";
        Query query = sessionFactory.getCurrentSession().createQuery(hsql);
        query.setLong("orderId", (Long)param.get("id"));
        List<OrderGoods> orderGoods = query.list();
        return orderGoods;
    }

    @Override
    public Integer getOrderGoodsListCnt(Map<String, Object> param) throws Exception {
        String hsql = "select count(*) from OrderGoods";
        Query query = sessionFactory.getCurrentSession().createQuery(hsql);
        Integer count = Integer.parseInt(query.uniqueResult().toString());
        return count;
    }

    @Override
    public void addGoods(Goods goods) throws Exception {
        goods.setId(goodsIdUtil.getUid());
        sessionFactory.getCurrentSession().save(goods);
    }

    @Override
    public void updateGoods(Goods goods) throws Exception {
        /**
         * 因为share-jdbc分片键为user_id所以where带了user_id的条件
         */
        Query query = sessionFactory.getCurrentSession().createQuery("update Goods set name = ?, SKU = ?, price = ? where id = ? and user_id = ?");
        query.setString(0, goods.getName());
        query.setString(1, goods.getSKU());
        query.setDouble(2, goods.getPrice());
        query.setLong(3, goods.getId());
        query.setLong(4, goods.getUserId());
        query.executeUpdate();
    }

    @Override
    public void deleteGoods(Map<String, Object> param) throws Exception {
        Query query = sessionFactory.getCurrentSession().createQuery("delete Goods where id = ?");
        query.setLong(0, (Long)param.get("id"));
        query.executeUpdate();
    }

    @Override
    public List<Goods> getGoodsList(Map<String, Object> param, PagerInfo pager) throws Exception {
        String hsql = "from Goods order by id desc";
        Query query = sessionFactory.getCurrentSession().createQuery(hsql);
        if (pager != null) {
            query.setMaxResults(pager.getSize());
            query.setFirstResult((pager.getPage() - 1) * pager.getSize());
        }
        List<Goods> i18s = query.list();
        return i18s;
    }

    @Override
    public Goods getGoods(Map<String, Object> param) throws Exception {
        String hsql = "from Goods where 1=1";
        if (param.get("id") != null) {
            hsql += " and id =:id";
        }
        Query query = sessionFactory.getCurrentSession().createQuery(hsql);
        if (param.get("id") != null) {
            query.setLong("id", Long.parseLong(param.get("id").toString()));
        }
        @SuppressWarnings("unchecked")
        List<Goods> goodsList = query.list();
        Goods goods = null;
        if (goodsList != null) {
            goods = goodsList.get(0);
        }
        return goods;
    }

    @Override
    public Integer getGoodsListCnt(Map<String, Object> param) throws Exception {
        String hsql = "select count(*) from Goods";
        Query query = sessionFactory.getCurrentSession().createQuery(hsql);
        Integer count = Integer.parseInt(query.uniqueResult().toString());
        return count;
    }

}
