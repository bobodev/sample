package com.sample.interceptor.inspector;


import com.sample.interceptor.core.ParameterUtils;
import com.sample.interceptor.model.RequestInfo;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

public class InspectorManager {
    private final Inspector[] inspectors;
    private final String name;

    private static final List<InspectorManager> INSPECTOR_MANAGERS = new ArrayList<>();


    private InspectorManager(String name, Inspector[] inspectors) {
        this.inspectors = inspectors;
        this.name = Objects.requireNonNull(name);
        INSPECTOR_MANAGERS.add(this);
    }

    public void check(HttpServletRequest httpServletRequest, RequestInfo requestInfo) throws SecurityException, ExecutionException {
        Map<String, String> parameters = ParameterUtils.getParameters(httpServletRequest);
        requestInfo.setRequestParameters(parameters);
        for (Inspector inspector : inspectors) {
            inspector.check(httpServletRequest, requestInfo);
        }
    }

    public static final InspectorManager NONE = new InspectorManager("NONE",
            new Inspector[]{
                    new NoneInspector()
            }
    );

    /**
     * 标准外部请求
     */
    public static final InspectorManager PERSON = new InspectorManager("PERSON",
            new Inspector[]{
                   new PersonInspector()
            }
    );

    /**
     * 标准外部请求
     */
    public static final InspectorManager STANDARD = new InspectorManager("STANDARD",
            new Inspector[]{
                    new NotAllowInspector()
            }
    );


    public static final InspectorManager valueOf(String name) {
        for (InspectorManager inspectorManager : INSPECTOR_MANAGERS) {
            if (inspectorManager.name.equalsIgnoreCase(name)) {
                return inspectorManager;
            }
        }
        throw new RuntimeException();
    }

}