package com.example.gconf.test;

import com.ciicgat.sdk.gconf.ConfigCollection;
import com.ciicgat.sdk.gconf.ConfigCollectionFactory;
import com.ciicgat.sdk.gconf.remote.RemoteConfigCollectionFactoryBuilder;
import com.example.gconf.beans.ConfigBean;
import com.example.gconf.beans.IConfig;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by issac.hu on 2018/3/19.
 */
public class GconfTest {

    @Test
    public void test(){
        ConfigCollectionFactory configCollectionFactory = RemoteConfigCollectionFactoryBuilder.getInstance();
        ConfigCollection configCollection = configCollectionFactory.getConfigCollection("contactlist-provider", "1.0.0");

        ConfigBean bean = configCollection.getBean("contactlist.properties", ConfigBean.class);
        IConfig iConfig = configCollection.getBean("contactlist.properties", IConfig.class);
        Assert.assertEquals(iConfig.getName(),"contactlist");
    }
}
