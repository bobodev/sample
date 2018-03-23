package com.example.gconf.initial;

import com.ciicgat.sdk.gconf.spring.GConfContextInitializer;

/**
 * Created by issac.hu on 2018/3/20.
 */
public class CumtomInitial extends GConfContextInitializer {
    public CumtomInitial(){
        this("contactlist-provider","1.0.0");
    }
    public CumtomInitial(String appid, String version) {
        super(appid, version);
    }
}
