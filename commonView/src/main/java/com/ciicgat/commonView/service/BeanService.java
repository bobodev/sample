package com.ciicgat.commonView.service;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ciicgat.api.core.FeignServiceFactory;
import com.ciicgat.api.hrscenter.service.EmployerService;
import com.ciicgat.api.userdoor.service.DepartmentService;
import com.ciicgat.api.userdoor.service.EnterpriseService;
import com.ciicgat.api.userdoor.service.PersonService;

@Configuration
public class BeanService {
    @Bean
    public PersonService personService(){
        return FeignServiceFactory.newInstance(PersonService.class);
    }

    @Bean
    public EnterpriseService enterpriseService() {
        return FeignServiceFactory.newInstance(EnterpriseService.class);
    }

    @Bean
    public DepartmentService departmentService() {
        return FeignServiceFactory.newInstance(DepartmentService.class);
    }
    
    @Bean
    public EmployerService employerService() {
        return FeignServiceFactory.newInstance(EmployerService.class);
    }

}
