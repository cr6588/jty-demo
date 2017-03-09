package com.jty.web.bean;

/**
 * 处理结果返回类
 * @author Mx
 *
 */
public class RequestResult<T> {
    /**
     * 处理结果编号0代表成功，其他代表失败
     */
    int code=0;
    /**
     * 如果出错的信息
     */
    String message;
    /**
     * 其他附加信息
     */
    T body;
    public int getCode() {
        return code;
    }
    public void setCode(int code) {
        this.code = code;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public void setBody(T body){
        this.body = body;
    }
    public T getBody() {
        return body;
    }
}
