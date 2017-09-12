package com.crazyjiang.crazydemo.mvp.model.entity;

/**
 * Created by Jiangwenjin on 2017/9/12.
 */

public class RongLoginResp {
    private int code;

    private ResultEntity result;

    public void setCode(int code) {
        this.code = code;
    }

    public void setResult(ResultEntity result) {
        this.result = result;
    }

    public int getCode() {
        return code;
    }

    public ResultEntity getResult() {
        return result;
    }

    public static class ResultEntity {
        private String id;
        private String token;

        public void setId(String id) {
            this.id = id;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getId() {
            return id;
        }

        public String getToken() {
            return token;
        }
    }
}
