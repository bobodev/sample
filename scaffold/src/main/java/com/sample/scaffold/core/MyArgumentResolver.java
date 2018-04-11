package com.sample.scaffold.core;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.PropertyNamingStrategy;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.sample.scaffold.core.converter.annotation.RequestConverterAnno;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class MyArgumentResolver implements HandlerMethodArgumentResolver {

    public static SerializeConfig serializeConfig = new SerializeConfig();

    static {
        serializeConfig.propertyNamingStrategy = PropertyNamingStrategy.SnakeCase;

    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(RequestConverterAnno.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest servletRequest = webRequest.getNativeRequest(HttpServletRequest.class);
        Map<String, String[]> parameterMap = servletRequest.getParameterMap();
        Map<String,Object> map = new HashMap<>();
        Set<String> keySet = parameterMap.keySet();
        for (String key : keySet) {
            map.put(key,servletRequest.getParameter(key));
        }
        Object object = JSONObject.parseObject(JSON.toJSONString(map, serializeConfig), parameter.getParameterType());
        return object;
    }
}
