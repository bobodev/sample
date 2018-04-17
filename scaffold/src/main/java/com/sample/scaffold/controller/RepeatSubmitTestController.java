package com.sample.scaffold.controller;

import com.sample.scaffold.core.repeatsubmit.TokenManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/scaffold/repeatSubmit")
public class RepeatSubmitTestController {

    @Autowired
    private TokenManager tokenManager;

    /**
     * 产生token
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping("/productToken")
    public String productToken(ModelMap model) throws Exception {
        model.addAttribute("token",tokenManager.productToken());
        return "xxx";
    }

    /**
     * 表单防重复提交
     * @param token
     * @return
     * @throws Exception
     */
    @RequestMapping("/submit")
    public String submit(String token) throws Exception {
        try{
            tokenManager.validateToken(token);
            //业务方法开始

            //业务方法结束
        }catch (Exception e){
            //异常信息
        }finally {
            tokenManager.removeToken(token);//最好移除，不移除的化默认一天失效
        }
        return "xxx";
    }

    /**
     * 产生token fox ajax
     * @return
     * @throws Exception
     */
    @RequestMapping("/productTokenFoxAjax")
    @ResponseBody
    public String productTokenFoxAjax() throws Exception {
        return tokenManager.productToken();
    }

}
