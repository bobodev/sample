package com.sample.interceptor.inspector;


import com.sample.interceptor.model.RequestInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;


public class NoneInspector implements Inspector {
    private static final Logger LOGGER = LoggerFactory.getLogger(NoneInspector.class);

    @Override
    public void check(HttpServletRequest httpServletRequest, RequestInfo requestInfo) throws SecurityException {

    }
}
