package com.zzz.my.shop.commons.dto;

import java.io.Serializable;

/**
 *自定义返回结果
 * @param
 * @return
 * @author yumako
 * @date 2019/8/5
 */
public class BaseResult implements Serializable {
    private int status;
    private String message;
    private Object data;
    public static final int STATUS_SUCCESS = 200;
    public static final int STATUS_FAIL = 500;

    public static BaseResult success(){
        return BaseResult.createResult(STATUS_SUCCESS, "成功",null);
    }

    public static BaseResult success(String message){
        return BaseResult.createResult(STATUS_SUCCESS, message,null);
    }

    public static BaseResult success(String message,Object data){
        return BaseResult.createResult(STATUS_SUCCESS, message, data);
    }

    public static BaseResult fail(){
        return BaseResult.createResult(STATUS_FAIL, "失败",null);
    }

    public static BaseResult fail(String message){
        return BaseResult.createResult(STATUS_FAIL, message,null);
    }

    public static BaseResult fail(int status, String message){
        return BaseResult.createResult(status, message,null);
    }

    private static BaseResult createResult(int status,String message,Object data){
        BaseResult baseResult = new BaseResult();
        baseResult.setStatus(status);
        baseResult.setMessage(message);
        baseResult.setData(data);
        return baseResult;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
