package com.example.gconf.beans;

import com.ciicgat.sdk.gconf.annotation.BeanFieldKey;

/**
 * Created by issac.hu on 2018/3/19.
 */
public interface IConfig {
    @BeanFieldKey("projectName")
   String getName();
}
