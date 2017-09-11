package com.crazyjiang.crazydemo.mvp.model.entity;

import java.util.List;

/**
 * Created by Jiangwenjin on 2017/9/11.
 */
public class RankLabelEntity<T> {
    private int labelId;
    private int dateType;
    private String name;
    private List<T> rank;
    private List<Double> scores;

    public int getLabelId() {
        return labelId;
    }

    public void setLabelId(int labelId) {
        this.labelId = labelId;
    }

    public int getDateType() {
        return dateType;
    }

    public void setDateType(int dateType) {
        this.dateType = dateType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<T> getRank() {
        return rank;
    }

    public void setRank(List<T> rank) {
        this.rank = rank;
    }

    public List<Double> getScores() {
        return scores;
    }

    public void setScores(List<Double> scores) {
        this.scores = scores;
    }
}
