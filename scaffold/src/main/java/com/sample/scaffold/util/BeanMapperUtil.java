package com.sample.scaffold.util;

import org.dozer.DozerBeanMapper;

/**
 * Created by sunguangzhu on 15/8/23.
 */
public class BeanMapperUtil {
    private static DozerBeanMapper beanMapper;

    private BeanMapperUtil() {

    }

    public static DozerBeanMapper getInstance() {
        if (beanMapper == null) {
            beanMapper = new DozerBeanMapper();
        }
        return beanMapper;
    }
}
