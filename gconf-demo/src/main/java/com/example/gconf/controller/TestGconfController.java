package com.example.gconf.controller;

import com.ciicgat.sdk.gconf.ConfigCollection;
import com.ciicgat.sdk.gconf.ConfigCollectionFactory;
import com.ciicgat.sdk.gconf.remote.RemoteConfigCollectionFactoryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by issac.hu on 2018/3/5.
 */
@RestController
@RequestMapping("gconf/test")
public class TestGconfController {

    @Autowired
    TestBean testBean;

    @RequestMapping("test1")
    public String test(){
        ConfigCollectionFactory configCollectionFactory = RemoteConfigCollectionFactoryBuilder.getInstance();
        ConfigCollection configCollection = configCollectionFactory.getConfigCollection("contactlist-provider", "1.0.0");
        String config = configCollection.getConfig("contactlist.properties");
        System.out.println(config);
        return "success";

    }

    @RequestMapping("test2")
    public Object test2(){

        System.out.println(testBean);
        return testBean;

    }
}
