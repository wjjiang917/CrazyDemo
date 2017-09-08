package com.crazyjiang.crazydemo.mvp.model.entity;

/**
 * Created by Jiangwenjin on 16/4/19.
 */
public class SimpResp<T> extends BaseResp {
    T record;

    public T getRecord() {
        return record;
    }

    public void setRecord(T record) {
        this.record = record;
    }

    @Override
    public String toString() {
        return "SimpResp{" +
                "returnCode='" + returnCode + '\'' +
                ", record=" + record +
                ", returnMsg='" + returnMsg + '\'' +
                '}';
    }
}
