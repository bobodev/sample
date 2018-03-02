package com.sample.easypoi.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

public class CommonUtil {
    private final static Logger logger = LoggerFactory.getLogger(CommonUtil.class);

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
}
