package com.crazyjiang.crazydemo.mvp.model.entity;

/**
 * Created by Jiangwenjin on 2017/9/12.
 */

public class LoginResultEntity {
    private UserEntity userInfo;

    private String randomKey;

    private int seed;

    public UserEntity getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserEntity userInfo) {
        this.userInfo = userInfo;
    }

    public String getRandomKey() {
        return randomKey;
    }

    public void setRandomKey(String randomKey) {
        this.randomKey = randomKey;
    }

    public int getSeed() {
        return seed;
    }

    public void setSeed(int seed) {
        this.seed = seed;
    }
}
