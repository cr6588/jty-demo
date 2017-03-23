package com.jty.web.bean;

public interface Constant {
    interface Db {
        interface Type {
            public static final int mysql = 1;
        }
        interface Status {
            public static final int normal = 1;
            public static final int lock = 2;
        }
    }
    interface Module {
        interface Order {
            public static final String name = "order";
            public static final int index = 1;
            public static final String[] logicTable = {"t_order", "order_goods", "goods"};
        }
    }
}
