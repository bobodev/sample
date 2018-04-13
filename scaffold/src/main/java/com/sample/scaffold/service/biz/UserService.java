package com.sample.scaffold.service.biz;

import com.sample.scaffold.model.User;
import org.springframework.cache.annotation.*;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
//@CacheConfig(cacheManager = "redisCacheManager",cacheNames = "user" )
@CacheConfig(cacheManager = "simpleCacheManager",cacheNames = "scaffold" )
public class UserService implements IUserService{

    @Override
    @Cacheable(key = "#id.toString()")
    public User findOneUser(Long id) throws Exception {
        User user = new User();
        user.setId(id);
        user.setName("scoffold"+ UUID.randomUUID().toString());
        user.setContactAddress("上海市"+id);
        return user;
    }

    @Override
    @CacheEvict(key = "#id.toString()")
    public User deleteUser(Long id) throws Exception {
        return null;
    }

    @Override
    @CachePut(key = "#user.id.toString()")
    public User updateUser(User user) throws Exception {
        user.setContactAddress("江苏省");
        return user;
    }

    /**
     * 失效多个缓存
     */
    @Caching(
            evict = {
                    @CacheEvict("user"),
                    @CacheEvict("scaffold")
            }
    )
    public void deleteAllCache(){

    }
}
