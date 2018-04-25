package com.example.gconf.beans;

import com.ciicgat.sdk.gconf.annotation.BeanFieldKey;

/**
 * Created by issac.hu on 2018/3/19.
 */
public class ConfigBean {


    private String projectName;
    @BeanFieldKey("projectName")
    private String name;

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "ConfigBean{" +
                "projectName='" + projectName + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
