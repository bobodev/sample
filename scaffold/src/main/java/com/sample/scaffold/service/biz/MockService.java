package com.sample.scaffold.service.biz;

import com.alibaba.fastjson.JSONObject;
import com.sample.scaffold.contract.dto.UserDto;
import com.sample.scaffold.core.mock.annotation.MockAnno;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class MockService {
    @MockAnno(fileName = "String.txt")
    public String mockString() throws Exception{
        return "mockString";
    }
    @MockAnno(fileName = "Integer.txt")
    public Integer mockInteger() throws Exception{
        return 1;
    }

    @MockAnno(fileName = "Integer.txt")
    public int mockInt() throws Exception{
        return 1;
    }

    @MockAnno(fileName = "Integer.txt")
    public int moc() throws Exception{
        return 1;
    }

    @MockAnno(fileName = "BigDecimal.txt")
    public BigDecimal mockBigDecimal() throws Exception{
        return new BigDecimal(1);
    }

    @MockAnno(fileName = "ListString.txt")
    public List<String> mockListString() throws Exception{
        return new ArrayList<>(Arrays.asList("1,2,3"));
    }

    @MockAnno(fileName = "ListJson.txt")
    public List<JSONObject> mockListJson() throws Exception{
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id",1);
        return new ArrayList<>(Arrays.asList(jsonObject));
    }

    @MockAnno(fileName = "ListUserDto.txt")
    public List<UserDto> mockListUserDto() throws Exception{
        UserDto userDto = new UserDto();
        userDto.setId(1);
        userDto.setPersonName("小明");
        return new ArrayList<>(Arrays.asList(userDto));
    }

    @MockAnno(fileName = "UserDto.txt")
    public UserDto mockUserDto() throws Exception{
        UserDto userDto = new UserDto();
        userDto.setId(1);
        userDto.setPersonName("小明");
        return userDto;
    }
}
