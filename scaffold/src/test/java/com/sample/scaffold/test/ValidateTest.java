package com.sample.scaffold.test;

import com.sample.scaffold.Application;
import com.sample.scaffold.contract.dto.SingleDto;
import com.sample.scaffold.service.biz.ISingleService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class ValidateTest {

    @Autowired
    private ISingleService singleService;

    /**
     * 测试单个参数验证
     * @throws Exception
     */
    @Test
    public void findOneSingle() throws Exception {
        this.singleService.findOneSingle(null);
    }

    /**
     * 测试对象参数验证
     * @throws Exception
     */
    @Test
    public void saveSingle() throws Exception {
        SingleDto singleDto = new SingleDto();
//        singleDto.setPropInteger(1);
//        singleDto.setPropLong(1l);
//        singleDto.setPropDouble(1.0);
//        singleDto.setPropStr("propStr");
//        singleDto.setDescription("propDescription");
//        singleDto.setPropDate(new Date());
        this.singleService.saveSingle(singleDto);
    }

}
