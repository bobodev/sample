package com.sample.interceptor.inspector;


import com.alibaba.fastjson.JSON;

import com.sample.interceptor.Exception.BusinessRuntimeException;
import com.sample.interceptor.core.ErrorUtil;
import com.sample.interceptor.model.RequestInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;


public class NotAllowInspector implements Inspector {
    private static final Logger LOGGER = LoggerFactory.getLogger(NotAllowInspector.class);

    @Override
    public void check(HttpServletRequest httpServletRequest, RequestInfo requestInfo) throws SecurityException {
        LOGGER.info("NotAllowInspector|requestInfo:{}", JSON.toJSONString(requestInfo));
        throw new BusinessRuntimeException(ErrorUtil.NOT_ALLOW);
    }
}
