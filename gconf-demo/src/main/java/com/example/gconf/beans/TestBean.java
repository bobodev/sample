package com.example.gconf.beans;

import com.example.gconf.utils.PropsAnnotation;

/**
 * Created by issac.hu on 2018/3/14.
 */
public class TestBean {

    @PropsAnnotation("key1")
    private int id;
    @PropsAnnotation("key2")
    private String name;
    private boolean flag;
    private double money;
    private Float money2;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public Float getMoney2() {
        return money2;
    }

    public void setMoney2(Float money2) {
        this.money2 = money2;
    }

    @Override
    public String toString() {
        return "TestBean{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", flag=" + flag +
                ", money=" + money +
                ", money2=" + money2 +
                '}';
    }
}
