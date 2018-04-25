package com.sample.scaffold.service.biz;

import com.sample.scaffold.model.User;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@CacheConfig(cacheManager = "redisCacheManager", cacheNames = "scaffold")
//@CacheConfig(cacheManager = "simpleCacheManager",cacheNames = "scaffold" )
public class UserService implements IUserService {

    @Override
    @Cacheable(key = "#id.toString()")
    public User findOneUser(Long id) throws Exception {
        User user = new User();
        user.setId(id);
        user.setName("scoffold" + UUID.randomUUID().toString());
        user.setContactAddress("上海市" + id);
        return user;
    }

    @Override
    @CacheEvict(key = "#id.toString()", beforeInvocation = true)
    public User deleteUser(Long id) throws Exception {
        return null;
    }

    @Override
    @CachePut(key = "#user.id.toString()")
    public User updateUser(User user) throws Exception {
        return user;
    }

    /**
     * 失效多个缓存
     */
    @CacheEvict(allEntries = true)
    public void deleteAllCache() throws Exception {

    }
}
