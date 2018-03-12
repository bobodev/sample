package com.sample.interceptor.inspector;


import com.sample.interceptor.model.RequestInfo;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.ExecutionException;

public interface Inspector {

    /**
     * @param httpServletRequest
     * @param requestInfo
     * @throws SecurityException
     */

    void check(HttpServletRequest httpServletRequest, RequestInfo requestInfo) throws SecurityException, ExecutionException;

}
