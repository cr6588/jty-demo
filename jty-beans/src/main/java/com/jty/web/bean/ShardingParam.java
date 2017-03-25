package com.jty.web.bean;

public class ShardingParam implements Comparable<ShardingParam> {
 
    Long param;
    public ShardingParam () {
        param = null;
    }

    public ShardingParam (String str) {
        param = Long.parseLong(str);
    }

    public ShardingParam (Integer in) {
        param = Long.parseLong(in + "");
    }

    public ShardingParam (Long in) {
        param = in;
    }

    public Long getParam() {
        return param;
    }

    @Override
    public int compareTo(ShardingParam o) {
        return param.compareTo(o.getParam());
    }

}
