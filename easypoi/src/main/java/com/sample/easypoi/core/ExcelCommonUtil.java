package com.sample.easypoi.core;

import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;

public class ExcelCommonUtil {

    /**
     * 动态拆分数组
     *
     * @param originList
     * @param <T>
     * @return
     */
    public static <T> List<List<T>> sublist(List<T> originList, int splitSize) {
        if (splitSize == 0) {
            splitSize = 5000;
        }
        List<List<T>> resultList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(originList)) {
            if (originList.size() > splitSize) {
                int fromIndex = 0;
                int size = originList.size();
                int end = 0;
                while (fromIndex <= size) {
                    end = fromIndex + splitSize;
                    if (end <= size) {
                        resultList.add(originList.subList(fromIndex, end));
                    } else {
                        resultList.add(originList.subList(fromIndex, size));
                    }
                    fromIndex = end;
                }
            } else {
                resultList.add(originList);
            }
        }
        return resultList;
    }

    /**
     * 处理导入的异步返回结果
     *
     * @param futures
     * @param <T>
     * @return
     * @throws Exception
     */
    public static  <T> ExcelImportResult<T> dealFutureResult(List<Future<ExcelImportResult<T>>> futures) throws Exception {
        ExcelImportResult<T> excelImportResult = new ExcelImportResult();
        List<T> successList = excelImportResult.getSuccessList();
        List<T> failList = excelImportResult.getFailList();
        List<ExcelImportResult> excelImportResults = new ArrayList<>();
        while (true) {
            List<Future<ExcelImportResult<T>>> futuresTemp = new ArrayList<>();
            for (Future<ExcelImportResult<T>> future : futures) {
                if (future.isDone()) {
                    futuresTemp.add(future);
                    excelImportResults.add(future.get());
                } else {
                    break;
                }
            }
            if (futuresTemp.size() > 0) {
                futures.removeAll(futuresTemp);
            }

            if (futures.size() == 0) {
                break;
            }
            Thread.sleep(100);
        }

        for (ExcelImportResult importResult : excelImportResults) {
            successList.addAll(importResult.getSuccessList());
            failList.addAll(importResult.getFailList());
        }
        return excelImportResult;
    }

}
