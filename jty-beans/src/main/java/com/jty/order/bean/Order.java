package com.jty.order.bean;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.ForeignKey;

import com.jty.user.bean.User;

// Generated 2017-2-13 15:02:46 by Hibernate Tools 3.4.0.CR1

/**
 * User generated by hbm2java
 */
@Entity
//@Table(name = "order") order是mysql关键字
@Table(name = "t_order")
public class Order implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -6407331588804713520L;
    private Long id;
    private String no;
    private User user;
    private Double totalMoney;
    private List<OrderGoods> orderGoods;

    public Order() {

    }
//    JPA提供的四种标准用法为TABLE,SEQUENCE,IDENTITY,AUTO.
//    TABLE：使用一个特定的数据库表格来保存主键。
//    SEQUENCE：根据底层数据库的序列来生成主键，条件是数据库支持序列。
//    IDENTITY：主键由数据库自动生成（主要是自动增长型）
//    AUTO：主键由程序控制(也是默认的,在指定主键时，如果不指定主键生成策略，默认为AUTO)
    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", unique = true, nullable = false)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "no")
    public String getNo() {
        return this.no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    @ForeignKey( name = "none" )
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Column(name = "total_money")
    public Double getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(Double totalMoney) {
        this.totalMoney = totalMoney;
    }

//    @OneToMany(mappedBy="order",targetEntity=OrderGoods.class,fetch=FetchType.LAZY)
    @OneToMany(mappedBy = "order", targetEntity=OrderGoods.class, cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, fetch = FetchType.EAGER)
//    @JoinColumn(name = "order_id")
    @ForeignKey( name = "none" )
    public List<OrderGoods> getOrderGoods() {
        return orderGoods;
    }

    public void setOrderGoods(List<OrderGoods> orderGoods) {
        this.orderGoods = orderGoods;
    }

}
