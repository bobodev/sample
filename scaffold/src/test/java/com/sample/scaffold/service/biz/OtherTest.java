package com.sample.scaffold.service.biz;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.PropertyNamingStrategy;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.sample.scaffold.contract.dto.AddrDto;
import com.sample.scaffold.contract.dto.UserDto;
import org.junit.Test;

import java.util.Locale;

public class OtherTest {

    @Test
    public void test01() throws Exception{
        UserDto user = new UserDto();
        user.setId(1);
        user.setPersonName("fsdfsdfsdf");
        user.setPhoneNumber("fsdfsdfsdf");
        AddrDto addr = new AddrDto();
        addr.setCompanyAddr("fdsf");
        addr.setHomeAddr("fsdf");
        user.setAddrDto(addr);

        SerializeConfig serializeConfig = new SerializeConfig();
        serializeConfig.propertyNamingStrategy= PropertyNamingStrategy.SnakeCase;
        String s = JSON.toJSONString(user, serializeConfig);
        System.out.println("s = " + s);
    }

    protected static String addUnderscores(String name) {
        StringBuilder buf = new StringBuilder(name.replace('.', '_'));

        for(int i = 1; i < buf.length() - 1; ++i) {
            if (Character.isLowerCase(buf.charAt(i - 1)) && Character.isUpperCase(buf.charAt(i)) && Character.isLowerCase(buf.charAt(i + 1))) {
                buf.insert(i++, '_');
            }
        }

        return buf.toString().toLowerCase(Locale.ROOT);
    }
}
