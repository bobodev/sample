package com.sample.interceptor.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestInfo implements Serializable {

    private Map<String, String> requestParameters;
    private int appId;
    private long startTime = System.currentTimeMillis();
    private long endTime;
    private int responseStatus;
    private String responseHeaderName;
    private String responseHeaderValue;
    private String person;
}
