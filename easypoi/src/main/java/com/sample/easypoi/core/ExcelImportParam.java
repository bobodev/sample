package com.sample.easypoi.core;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExcelImportParam {
    private int startRowNum;//开始行 从0开始
    private int endRowNum;//结束行
    private Map<String,Object> replaceMap = new HashMap<>();
}
