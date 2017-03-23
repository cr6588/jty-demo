package com.jty.web.bean;

public interface Constant {
    interface Module {
        interface Order {
            static final String name = "order";
            static final Integer index = 1;
            public static final String[] logicTable = {"t_order", "order_goods", "goods"};
        }
    }
}
