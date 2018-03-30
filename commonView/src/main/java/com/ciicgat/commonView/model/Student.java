package com.ciicgat.commonView.model;

import java.io.Serializable;

/**
 * Created by chencheng on 2018/3/30.
 */
public class Student implements Serializable {
    private Integer id;
    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
