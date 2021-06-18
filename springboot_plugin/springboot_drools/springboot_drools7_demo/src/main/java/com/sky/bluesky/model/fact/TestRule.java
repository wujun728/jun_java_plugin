package com.sky.bluesky.model.fact;

import com.sky.bluesky.model.BaseModel;

/**
 * 描述：
 * CLASSPATH: com.sky.bluesky.model.fact.TestRule
 * VERSION:   1.0
 * Created by lihao
 * DATE:      2017/7/20
 */
public class TestRule extends BaseModel {
    private String message;
    private Integer amount;
    private Integer score;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }
}
