package com.sample.scaffold.service.biz;

import com.sample.scaffold.model.User;


public interface IUserService {
    User findOneUser(Long id) throws Exception;
    User deleteUser(Long id) throws Exception;
    User updateUser(User user) throws Exception;
}
