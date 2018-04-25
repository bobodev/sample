package com.ciicgat.commonView.controller;

import com.alibaba.fastjson.JSONObject;
import com.ciicgat.api.userdoor.model.Department;
import com.ciicgat.api.userdoor.model.Person;
import com.ciicgat.api.userdoor.model.PersonSearchCondition;
import com.ciicgat.api.userdoor.service.DepartmentService;
import com.ciicgat.api.userdoor.service.PersonService;
import com.ciicgat.commonView.model.DialogDTO;
import com.ciicgat.commonView.model.PersonSearchDTO;
import com.ciicgat.commonView.util.EnvUtil;
import com.ciicgat.sdk.lang.convert.ApiResponse;
import com.ciicgat.sdk.lang.convert.Pagination;
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

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(value = "/commonView/person")
public class PersonController extends CommonAction{
    private static final Logger LOGGER = LoggerFactory.getLogger(PersonController.class);
	@Autowired
    PersonService personService;
	@Autowired
    DepartmentService departmentService;
	
	

    @RequestMapping(value = "/choosePage", method = RequestMethod.GET)
    public String personPage(HttpServletRequest request,DialogDTO dialogDTO,Model model) {
        LOGGER.info("personPage params:{}",JSONObject.toJSONString(dialogDTO));
        Integer enterpriseId=getEnterpriseId(request);
        if(enterpriseId==null){
            return "500";
        }
        model.addAttribute("chooseIds",dialogDTO.getChooseIds());
        if(StringUtils.isNotEmpty(dialogDTO.getCloseFunc()) && !dialogDTO.getCloseFunc().endsWith("()")){
            model.addAttribute("closeFunc",dialogDTO.getCloseFunc()+"()");
        }else {
            model.addAttribute("closeFunc",dialogDTO.getCloseFunc());
        }
        model.addAttribute("domain", EnvUtil.getJSDomain());
        LOGGER.info("personPage|domain:{}",EnvUtil.getJSDomain());
        model.addAttribute("callbackFunc",dialogDTO.getCallbackFunc());
        //查询部门
        Pagination<Department> departmentPage = departmentService.findDepartment(enterpriseId, null, null, null, null, null,
                null, null, null, null, null);
        LOGGER.info("personPage department size:{}",departmentPage.getTotalCount());
        model.addAttribute("departments", departmentPage.getDataList());
        if (dialogDTO.getSelectType()==1){//单选
            return "person/dialog_choose_person_radio";
        }
        return "person/dialog_choose_person_checkbox";
    }

    @RequestMapping(value = "/getDataBySearch")
    @ResponseBody
    public ApiResponse getPersons(HttpServletRequest request, PersonSearchDTO personSearchDTO,
                                @RequestParam(value = "pageIndex",defaultValue = "1") Integer pageIndex,
                                @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize) {
        Integer enterpriseId=getEnterpriseId(request);
        personSearchDTO.setEnterpriseId(enterpriseId);
        LOGGER.info("getPersons params:{}",JSONObject.toJSONString(personSearchDTO));
        if(enterpriseId==null){
            return null;
        }
        PersonSearchCondition personSearchCondition=new PersonSearchCondition();
        personSearchCondition.setEnterpriseId(enterpriseId);
        personSearchCondition.setCodeSearch(personSearchDTO.getCode());
        personSearchCondition.setNameSearch(personSearchDTO.getName());
        personSearchCondition.setPage(pageIndex);
        personSearchCondition.setRowsPerPage(pageSize);
        if(personSearchDTO.getDepartmentId()!=null){
            personSearchCondition.setDepartmentId(personSearchDTO.getDepartmentId());
        }
        Pagination<Person> page = personService.findPersonsBySearchCondition(personSearchCondition);
        LOGGER.info("getPersons return:{}",JSONObject.toJSONString(page));
        return ApiResponse.success(page);
    }
}
