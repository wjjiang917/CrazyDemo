package com.crazyjiang.crazydemo.mvp.model.entity;

/**
 * Created by Jiangwenjin on 16/4/19.
 */
public class QueryResp<T> extends SimpResp<T> {
    String recordSum;

    public String getRecordSum() {
        return recordSum;
    }

    public void setRecordSum(String recordSum) {
        this.recordSum = recordSum;
    }

    @Override
    public String toString() {
        return "QueryResp{" +
                "record=" + record +
                ", returnCode='" + returnCode + '\'' +
                ", returnMsg='" + returnMsg + '\'' +
                ", recordSum='" + recordSum + '\'' +
                '}';
    }
}
