package com.ciicgat.commonView.controller;

import javax.servlet.http.HttpServletRequest;

import com.ciicgat.commonView.model.DialogDTO;
import com.ciicgat.commonView.util.EnvUtil;
import com.ciicgat.sdk.gconf.WorkRegion;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.ciicgat.api.hrscenter.model.Employer;
import com.ciicgat.api.hrscenter.model.EmployerSearch;
import com.ciicgat.api.hrscenter.service.EmployerService;
import com.ciicgat.sdk.lang.convert.ApiResponse;
import com.ciicgat.sdk.lang.convert.Pagination;

@Controller
@RequestMapping(value = "/commonView/employer")
public class EmployerController extends CommonAction{
    private static final Logger LOGGER = LoggerFactory.getLogger(EmployerController.class);
	@Autowired
	EmployerService employerService;
	
	

    @RequestMapping(value = "/choosePersonPage", method = RequestMethod.GET)
    public String employerListPage(HttpServletRequest request,DialogDTO dialogDTO,Model model) {
        LOGGER.info("employerListPage params:{}",JSONObject.toJSONString(dialogDTO));
        Integer enterpriseId=getEnterpriseId(request);
    	if(enterpriseId==null){
    		return "500";
    	}
        model.addAttribute("chooseId",dialogDTO.getChooseIds());
        if(StringUtils.isNotEmpty(dialogDTO.getCloseFunc()) && !dialogDTO.getCloseFunc().endsWith("()")){
            model.addAttribute("closeFunc",dialogDTO.getCloseFunc()+"()");
        }else {
            model.addAttribute("closeFunc",dialogDTO.getCloseFunc());
        }
        model.addAttribute("callbackFunc",dialogDTO.getCallbackFunc());
        model.addAttribute("domain", EnvUtil.getJSDomain());
        return "employer/dialog_choose_employer";
    }

    @ResponseBody
    @RequestMapping(value = "/getPersonBySearch", method = RequestMethod.GET)
    public ApiResponse employerList(HttpServletRequest request,String code, String name,
                                           @RequestParam(value = "pageIndex",defaultValue = "1") Integer pageIndex,
                                           @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize) {
        LOGGER.info("employerList code:{}|name:{}|pageIndex:{}|pageSize:{}",code,name,pageIndex,pageSize);
    	Integer enterpriseId=getEnterpriseId(request);
    	if(enterpriseId==null){
    		return null;
    	}
        EmployerSearch employerSearch = new EmployerSearch();
        //0.有效，1.终止
        employerSearch.setStatus(0);
        employerSearch.setCodeLike(code);
        employerSearch.setNameLike(name);
        employerSearch.setEnterpriseId(enterpriseId);//1043
        employerSearch.setPageNo(pageIndex);
        employerSearch.setPageSize(pageSize);
        Pagination<Employer> page=employerService.getEmployerByCondition(employerSearch);
        LOGGER.info("employerList return:{}",JSONObject.toJSONString(page));
        return ApiResponse.success(page);
    }
}
