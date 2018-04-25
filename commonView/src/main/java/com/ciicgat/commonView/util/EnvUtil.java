package com.ciicgat.commonView.util;

import com.alibaba.fastjson.JSONObject;
import com.ciicgat.sdk.gconf.WorkRegion;

/**
 * Created by chencheng on 2018/1/26.
 */
public class EnvUtil {
    public static String getJSDomain(){
        return "guanaitong."+WorkRegion.getCurrentWorkRegion().getPublicDomainSuffix();
    }

    public static String getAdminJSDomain(){
        return "ciicgat."+WorkRegion.getCurrentWorkRegion().getPublicDomainSuffix();
    }

}
