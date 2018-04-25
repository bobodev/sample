package com.sample.interceptor.interceptor;

import com.sample.interceptor.core.Constants;
import com.sample.interceptor.inspector.InspectorManager;
import com.sample.interceptor.model.RequestInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Jonathan on 2018/3/12.
 */
public class URLInterceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(URLInterceptor.class);
    private InspectorManager inspectorManager;

    public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3) throws Exception {
        // TODO Auto-generated method stub

    }

    public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3) throws Exception {
        // TODO Auto-generated method stub

    }

    /**
     * 在请求处理之前进行调用（Controller方法调用之前）调用,
     * 返回true 则放行， false 则将直接跳出方法
     */
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        response.setHeader("Content-type", "text/html;charset=UTF-8");
        RequestInfo requestInfo = new RequestInfo();
        String ip = getIpAddr(request);
        logger.info("ip:{}", ip);
        if (request.getRequestURI().contains("/isLive")) {
            inspectorManager = InspectorManager.NONE;
        } else if (request.getRequestURI().contains(".js")) {
            inspectorManager = InspectorManager.NONE;
        } else if (request.getRequestURI().contains("/person")) {
            inspectorManager = InspectorManager.PERSON;
        } else {
            inspectorManager = InspectorManager.NONE;
        }
        try {
            //获取对象为空
            inspectorManager.check(request, requestInfo);
            requestInfo.setAppId(12345);
            request.setAttribute(Constants.REQUEST_INFO_TAG, requestInfo);
            if (requestInfo.getResponseHeaderValue() == null) {
                return true;
            }
            //不为空 跳转页面
            response.setStatus(requestInfo.getResponseStatus());
            response.setHeader(requestInfo.getResponseHeaderName(), requestInfo.getResponseHeaderValue());
        } catch (SecurityException e) {
            logger.error("LoginInterceptor:{}", e);
            throw new RuntimeException("LoginInterceptor error");
        }
        return true;

    }

    /**
     * 获取访问的ip地址
     *
     * @param request
     * @return
     */
    public static String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

}