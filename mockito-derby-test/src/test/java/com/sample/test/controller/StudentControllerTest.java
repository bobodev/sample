package com.sample.test.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sample.test.domain.StudentDO;
import com.sample.test.service.StudentService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

/**
 * @author wanchongyang
 * @date 2018/3/29
 */
public class StudentControllerTest extends BaseControllerTest {
    @InjectMocks
    private StudentController studentController;

    @Mock
    private StudentService studentService;

    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        mockMvc = MockMvcBuilders.standaloneSetup(studentController).build();
    }

    @Test
    public void test_getById() throws Exception {
        StudentDO studentDO = new StudentDO();
        studentDO.setStudentNo("A001");
        studentDO.setStudentName("王二");

        Mockito.when(studentService.getById(Mockito.any(Integer.class))).thenReturn(studentDO);

        // 参数对象
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("id", "1");
        MockHttpServletRequestBuilder get = MockMvcRequestBuilders.get("/student/getById").params(params)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE);

        String content = mockMvc.perform(get).andReturn().getResponse().getContentAsString();
        JSONObject jsonObject = JSON.parseObject(content);
        Assert.assertEquals(studentDO.getStudentNo(), jsonObject.getString("studentNo"));
        Assert.assertEquals(studentDO.getStudentName(), jsonObject.getString("studentName"));
    }
}
