package com.ciicgat.commonView.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.core.convert.converter.Converter;

/**
 * Created by qiang.wang on 2017/10/19 6:25.
 */
public class CustomDateConverter implements Converter<String, Date> {
    private static final String PATTERN = "yyyy-MM-dd HH:mm:ss";

    @Override
    public Date convert(String source) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(PATTERN);

        try {
            return simpleDateFormat.parse(source);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

}
