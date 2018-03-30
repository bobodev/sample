package com.sample.test.constant;

import com.google.common.annotations.VisibleForTesting;

/**
 * 常量类
 * @author wanchongyang
 * @date 2018/3/29
 */
public class GlobalConstants {
    static boolean isInTest = false;

    public static boolean isInTest() {
        return isInTest;
    }

    @VisibleForTesting
    public static void setIsInTest(boolean isInTest) {
        GlobalConstants.isInTest = isInTest;
    }
}
