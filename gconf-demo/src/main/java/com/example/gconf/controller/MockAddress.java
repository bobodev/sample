package com.example.gconf.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by issac.hu on 2018/3/16.
 */
@RestController
@RequestMapping("api")
public class MockAddress {
    @RequestMapping("getConfigApp")
    public Object getConfigApp(){

        String result="{\"configCollectionId\":\"contactlist-provider\",\"configCollectionVersion\":\"1.0.0\",\"createId\":\"maoyong.hu\",\"desc\":\"通讯录提供者\",\"name\":\"企业通讯录\",\"readStatus\":0,\"status\":1,\"timeCreated\":1495160737000,\"timeModified\":1495161238000}";
        JSONObject jsonObject = JSON.parseObject(result);
        return jsonObject;
    }

    @RequestMapping("listConfigs")
    public Object listConfigs(){
        String result="{\"constants.properties\":\"ecenter.url=https://ecenter.guanaitong.test\\nmy.message.list.url = http://my.guanaitong.test/index.php?m=message&a=list\\nmy.message.unread.url = https://mycenter.guanaitong.test/index.php?wxA=Default.getPersonUnreadMessageCt&memberId=\\nmsg.url=http://message.services.test.ofc\\nmsg.list.url=https://ecenter.guanaitong.test/index.php?wxA=Message.index\\nphp.guide.check.tag=FESTIVAL_MALL_GUIDE\\nwebservice.assets.url=http://webservice.assets.guanaitong.test/index.php\\nappId=10000357\\ncharge.url=//charge.guanaitong.test/index.php?wxA=Charge.chargeJifen\\nad.guid=GAT_ECENTER_FESTIVAL_MALL\",\"jdbc.properties\":\"mysqlUrl=jdbc:mysql://10.100.32.139:3306/festival_mall?useUnicode=true&useSSL=false&characterEncoding=UTF-8\\nmysqlDriverClassName=com.mysql.jdbc.Driver\\nmysqlUserName=root\\nmysqlPassword=111111\",\"log4j.properties\":\"log4j.rootLogger=INFO,file,stdout\\n\\nlog4j.appender.stdout.Encoding=utf-8\\nlog4j.appender.stdout=org.apache.log4j.ConsoleAppender\\nlog4j.appender.stdout.layout=org.apache.log4j.PatternLayout\\nlog4j.appender.stdout.layout.ConversionPattern=^V^ [%p] [%d{yyyy-MM-dd HH:mm:ss.SSS}] [%t] [%c:%L] %X{req.xRequestId} %m%n\\n\\nlog4j.appender.file=org.apache.log4j.DailyRollingFileAppender\\nlog4j.appender.file.File=logs/application.log\\nlog4j.appender.file.DatePattern='.'yyyy-MM-dd\\nlog4j.appender.file.layout=org.apache.log4j.PatternLayout\\nlog4j.appender.file.layout.ConversionPattern=^V^ [%p] [%d{yyyy-MM-dd HH:mm:ss.SSS}] [%t] [%c:%L] %X{req.xRequestId} %m%n\\n\\nlog4j.logger.com.springframework=ERROR\\nlog4j.logger.com.alibaba.dubbo.monitor.dubbo.DubboMonitor=OFF\",\"redis.properties\":\"host=10.101.9.216\\nport=6379\\ntimeout=\\npassword=\\ndatabase=11\",\"velocity.properties\":\"input.encoding=UTF-8\\noutput.encoding=UTF-8\\n\\nresource.loader=webapp\\n\\nwebapp.resource.loader.class=org.apache.velocity.tools.view.servlet.WebappLoader\\nwebapp.resource.loader.path=/WEB-INF/tpl/\\nwebapp.resource.loader.cache=false\\nwebapp.resource.loader.modificationCheckInterval=0\"}" ;
         JSONObject jsonObject = JSON.parseObject(result);
        return jsonObject;
    }

}
