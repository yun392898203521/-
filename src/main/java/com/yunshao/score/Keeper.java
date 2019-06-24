package com.yunshao.score;

/**
 * @Author: yunshao
 * @Date: 2018/8/19 15:57
 */

public class Keeper {

    // 次数
    int x1;
    int x2;
    int x3;

    // 权重
    double key1;
    double key2;
    double key3;

    // 行驶距离
    double L;

    // 违法次数
    int illegalTime;

    public int getX1() {
        return x1;
    }

    public void setX1(int x1) {
        this.x1 = x1;
    }

    public int getX2() {
        return x2;
    }

    public void setX2(int x2) {
        this.x2 = x2;
    }

    public int getX3() {
        return x3;
    }

    public void setX3(int x3) {
        this.x3 = x3;
    }

    public double getKey1() {
        return key1;
    }

    public void setKey1(double key1) {
        this.key1 = key1;
    }

    public double getKey2() {
        return key2;
    }

    public void setKey2(double key2) {
        this.key2 = key2;
    }

    public double getKey3() {
        return key3;
    }

    public void setKey3(double key3) {
        this.key3 = key3;
    }

    public double getL() {
        return L;
    }

    public void setL(double l) {
        L = l;
    }

    public int getIllegalTime() {
        return illegalTime;
    }

    public void setIllegalTime(int illegalTime) {
        this.illegalTime = illegalTime;
    }
}
