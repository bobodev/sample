package com.ciicgat.commonView.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.ciicgat.api.passport.auth.user.SimpleUser;
import com.ciicgat.api.passport.auth.user.UserAuth;
import com.ciicgat.api.passport.auth.user.UserType;
import com.ciicgat.api.userdoor.model.Enterprise;
import com.ciicgat.api.userdoor.model.Person;
import com.ciicgat.api.userdoor.service.EnterpriseService;
import com.ciicgat.api.userdoor.service.PersonService;

public class CommonAction {
	@Autowired
	private PersonService personService;
	@Autowired
	private EnterpriseService enterpriseService;
	private static final Logger log = LoggerFactory.getLogger(CommonAction.class);

	
	public Person getPerson(HttpServletRequest request) {
		Integer memberId = getMemberId(request);
		if (memberId == null) {
			return null;
		}
		Person person = personService.getPersonByMemberId(memberId);
		return person;
	}
	
	public Enterprise getEnterprise(HttpServletRequest request) {
		Integer memberId = getMemberId(request);
		if (memberId == null) {
			return null;
		}
		Enterprise enterprise =enterpriseService.getEnterpriseByMemberId(memberId);
		return enterprise;
	}
	
	public SimpleUser getSimpleUser(HttpServletRequest request) {
        boolean isLogin = UserAuth.authenticate(request);
        if (!isLogin) {
            log.error("not login");
            return null;
        }
		return UserAuth.getCurrentUser();
	}

	public Integer getMemberId(HttpServletRequest request) {
		boolean res = UserAuth.authenticate(request);
		if (res) {
			Integer memberId = UserAuth.getCurrentUser().getMemberId();
			return memberId;
		} else {
			return null;
		}

	}
	public Integer getEnterpriseId(HttpServletRequest request) {
//        boolean isLogin = UserAuth.authenticate(request);
//        if (!isLogin) {
//            log.error("not login");
//            return null;
//        }
//		SimpleUser simpleUser=UserAuth.getCurrentUser();
//		if(simpleUser==null){
//			return null;
//		}
//		if(simpleUser.getUserType().equals(UserType.ENTERPRISE)){
//			Enterprise enterprise =enterpriseService.getEnterpriseByMemberId(simpleUser.getMemberId());
//			return enterprise.getId();
//		}
//		if(simpleUser.getUserType().equals(UserType.PERSON)){
//			Person person = personService.getPersonByMemberId(simpleUser.getMemberId());
//			return person.getEnterpriseId();
//		}
//		return null;
		return 776;

	}

}
