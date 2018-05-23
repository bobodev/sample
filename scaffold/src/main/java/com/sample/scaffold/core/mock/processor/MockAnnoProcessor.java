package com.sample.scaffold.core.mock.processor;

import com.alibaba.druid.sql.visitor.functions.Char;
import com.alibaba.fastjson.JSON;
import com.sample.scaffold.core.mock.annotation.MockAnno;
import org.apache.commons.io.FileUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.File;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.net.URL;
import java.util.List;

@Aspect
@Component
public class MockAnnoProcessor {

    private final static Logger logger = LoggerFactory.getLogger(MockAnnoProcessor.class);

    @Around("@annotation(com.sample.scaffold.core.mock.annotation.MockAnno)")
    public Object process(ProceedingJoinPoint pjp) throws Throwable {
        Object proceed = null;
        Method currentMethod = ((MethodSignature) pjp.getSignature()).getMethod();
        MockAnno mockAnno = currentMethod.getAnnotation(MockAnno.class);
        String fileName = mockAnno.fileName();
        URL resource = this.getClass().getClassLoader().getResource("mockfile/".concat(fileName));
        File file = new File(resource.toURI());
        String s = FileUtils.readFileToString(file, "UTF-8");
        s = StringUtils.replace(s, "\n", "");
        Class<?> returnType = currentMethod.getReturnType();
        String typeName = returnType.getTypeName();
        //支持String,Integer,int,Short,short,Long,long,Double,double,Float,float,Char,char,BigDecimal,List和自定义对象
        if (String.class.getTypeName().equals(typeName)) {
            proceed = s;
        }else if(Integer.class.getTypeName().equals(typeName)||int.class.getTypeName().equals(typeName)){
            proceed = Integer.valueOf(s);
        }else if(Short.class.getTypeName().equals(typeName)||short.class.getTypeName().equals(typeName)){
            proceed = Short.valueOf(s);
        }else if(Long.class.getTypeName().equals(typeName)||long.class.getTypeName().equals(typeName)){
            proceed = Long.valueOf(s);
        }else if(Double.class.getTypeName().equals(typeName)||double.class.getTypeName().equals(typeName)){
            proceed = Double.valueOf(s);
        }else if(Float.class.getTypeName().equals(typeName)||float.class.getTypeName().equals(typeName)){
            proceed = Float.valueOf(s);
        }else if(Char.class.getTypeName().equals(typeName)||char.class.getTypeName().equals(typeName)){
            proceed = s.charAt(0);
        }else if(BigDecimal.class.getTypeName().equals(typeName)){
            proceed = new BigDecimal(s);
        }else if(List.class.getTypeName().equals(typeName)){
            proceed = JSON.parseArray(s);
        }else{//其他均认为是对象
            proceed = JSON.parseObject(s,returnType);
        }
        return proceed;
    }
}