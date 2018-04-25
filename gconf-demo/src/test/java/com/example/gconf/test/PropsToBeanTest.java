package com.example.gconf.test;

import com.example.gconf.beans.TestBean;
import com.example.gconf.utils.PropsToBeanUtils;
import org.junit.Test;

/**
 * Created by issac.hu on 2018/3/14.
 */
public class PropsToBeanTest {

    @Test
    public void test(){
        TestBean testBean = PropsToBeanUtils.toBean("test.properties", TestBean.class,true);

        System.out.println(testBean);
    }
}
