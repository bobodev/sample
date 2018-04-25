package com.sample.interceptor.inspector;


import com.alibaba.fastjson.JSONObject;
import com.sample.interceptor.core.ServiceFactory;
import com.sample.interceptor.model.RequestInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * 雇员检测
 */

public class PersonInspector implements Inspector {
    private static final Logger logger = LoggerFactory.getLogger(PersonInspector.class);

    @Override
    public void check(HttpServletRequest httpServletRequest, RequestInfo requestInfo) throws SecurityException, ExecutionException {
        Map<String, String> requestParameters = requestInfo.getRequestParameters();
        logger.info("requestParameters:{}", requestParameters);
        //此处编写业务代码 把缓存中查到的数据带入 requestInfo
        InspectorPersonCache inspectorPersonCache = ServiceFactory.getBean(InspectorPersonCache.class);
        String person = inspectorPersonCache.getPersonByCache(1);
        requestInfo.setPerson(person);
        logger.info("PersonInspector | check | person :{}", JSONObject.toJSONString(person));
        if (person == null) {
            return;
        } else {
            requestInfo.setResponseStatus(HttpServletResponse.SC_OK);
            requestInfo.setResponseHeaderName("Location");
            //此处写vm地址
            requestInfo.setResponseHeaderValue("");
        }
    }
}
