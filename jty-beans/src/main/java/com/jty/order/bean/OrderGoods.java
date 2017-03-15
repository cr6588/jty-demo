package com.jty.order.bean;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.ForeignKey;

@Entity
@Table(name = "order_goods")
public class OrderGoods implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -2144533441512959006L;
    private Long id;
    private Order order;
    private Goods goods;
    private Integer num;

    public OrderGoods() {

    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

//    @ManyToOne
//    @JoinColumn(name = "order_id")
    @ManyToOne(fetch=FetchType.EAGER,targetEntity=Order.class)
    @JoinColumn(name="order_id")
    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    @OneToOne(fetch = FetchType.EAGER, targetEntity = Goods.class)
    @JoinColumn(name = "goods_id")
    @ForeignKey( name = "none" )
    
    public Goods getGoods() {
        return goods;
    }

    public void setGoods(Goods goods) {
        this.goods = goods;
    }

    @Column(name = "num")
    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

}
