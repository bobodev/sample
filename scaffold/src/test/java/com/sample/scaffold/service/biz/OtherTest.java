package com.sample.scaffold.service.biz;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.PropertyNamingStrategy;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.sample.scaffold.contract.dto.AddrDto;
import com.sample.scaffold.contract.dto.UserDto;
import org.junit.Test;

import java.util.*;

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

    @Test
    public void test02() throws Exception{
        List<Integer> list = new ArrayList<>(Arrays.asList(
                299422,299423,318686,318689,384256,384262,508411,743688,743851,784428,798873,838737,847198,847200,847201,859397,859491,859492,859499,859500,859515,859517,859534,859537,859538,859557,883720,883722,883726,884956,895731,896117,896122,896126,953498,1037979,1132305,1148011,1152026,1152042,1152047,1152052,1152053,1171707,1180559,1180566,1195329,1205776,1219844,1227879,1285712,1285718,1290085,1295560,1295573,1422933,1577397,1611790,1715445,1715449,1732380,1752777,1757538,1785641,1848783,1881541,1905091,1922776,1937914,1974003,2077830,2192131,2297182,2443473,2571022,2625373,2630154,2716246,2792766,2825125,2846725,2848732,2930224,2930380,2947150,2948660,2950987,3001252,3012833,3081391,3141906,3141908,3209037,3209045,3368752,3396754,3397848,3402618,3444503,3477252,3477260,3507655,3554075,3554107,3596637,3600993,3636313,3646911,3699829,3702928,3708015,3717614,3763535,3767711,3932160,3988316,4035199,4204458,4342307,4438431,4488640,4491833,4491869,4543984,4543986,4544100,4561520,4928322,5471578
        ));
        StringBuilder stringBuilder = new StringBuilder();
        for (Integer integer : list) {
            stringBuilder.append("INSERT INTO `ShoppingCart` (`personId`, `skuId`, `num`, `isSelected`, `mallId`)VALUES( 249412,"+integer+", 1, 1, 'jdmall');");
        }
        String s = stringBuilder.toString();

        System.out.println(s);

    }


    @Test
    public void test03() throws Exception{

        HashMap<Object, Object> objectObjectHashMap = new HashMap<>();

        objectObjectHashMap.put("dd","ddd");


        System.out.println("objectObjectHashMap = " + objectObjectHashMap);
    }
}
