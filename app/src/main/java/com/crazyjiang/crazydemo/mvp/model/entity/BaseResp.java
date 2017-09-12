package com.crazyjiang.crazydemo.mvp.model.entity;

import java.io.Serializable;

/**
 * Created by Jiangwenjin on 16/4/19.
 */
public class BaseResp implements Serializable {
    String returnCode;
    String returnMsg;

    public String getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(String returnCode) {
        this.returnCode = returnCode;
    }

    public String getReturnMsg() {
        return returnMsg;
    }

    public void setReturnMsg(String returnMsg) {
        this.returnMsg = returnMsg;
    }

    public boolean isSuccess() {
        return "000000".equals(returnCode);
    }

    @Override
    public String toString() {
        return "BaseResp{" +
                "returnCode='" + returnCode + '\'' +
                ", returnMsg='" + returnMsg + '\'' +
                '}';
    }
}
