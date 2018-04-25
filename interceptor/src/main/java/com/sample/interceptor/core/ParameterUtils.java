package com.sample.interceptor.core;


import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.commons.io.IOUtils;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.*;

public class ParameterUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(ParameterUtils.class);

    private static Set<String> CONTENTTYPE_SET = null;

    static {
        CONTENTTYPE_SET = new HashSet<>();
        CONTENTTYPE_SET.add("application/json");
        CONTENTTYPE_SET.add("application/json;charset=utf-8");
    }

    /**
     * 解析请求参数，目前支持的参数格式有：（1）query string，（2）body json string
     *
     * @param httpServletRequest
     * @return
     */
    public static Map<String, String> getParameters(HttpServletRequest httpServletRequest) {
        Map<String, String> parameters = new HashMap<>();

        /**
         * case: body-json string
         */
        if ("POST".equals(httpServletRequest.getMethod()) &&
                (StringUtils.isNotEmpty(httpServletRequest.getContentType()) &&CONTENTTYPE_SET.contains(httpServletRequest.getContentType().toLowerCase()))
                ) {
            try {
                String bodyString = IOUtils.toString(httpServletRequest.getInputStream(), "utf-8");
                LOGGER.info("bodyString: " + bodyString);
                parameters.put(Constants.JSON_BODY, bodyString);
            } catch (IOException e) {
                LOGGER.error(e.getMessage(), e);
            }
        }
        /**
         * case: query string
         */
        Map<String, String[]> parameterMap = httpServletRequest.getParameterMap();
        for (Map.Entry<String, String[]> entry : parameterMap.entrySet()) {
            parameters.put(entry.getKey(), entry.getValue()[0]);// august:这里只处理第一个参数，如果同名的参数有多个，那么只有第一个有用
        }
        return Collections.unmodifiableMap(parameters);
    }


}