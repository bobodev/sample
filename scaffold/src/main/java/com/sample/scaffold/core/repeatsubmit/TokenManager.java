package com.sample.scaffold.core.repeatsubmit;

import com.sample.scaffold.core.ServiceException;
import com.sample.scaffold.service.technology.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.UUID;

@Component
public class TokenManager {
    private static final int EXPIRED = 60 * 60 * 24;//默认一天过期
    private static final String TRUE_STRING = "true";
    private static final String FALSE_STRING = "false";
    @Autowired
    private RedisService redisService;

    /**
     * 产生token
     *
     * @return
     * @throws Exception
     */
    public String productToken() throws Exception {
        String token = UUID.randomUUID().toString();
        this.redisService.setex(UUID.randomUUID().toString(), EXPIRED, TRUE_STRING);
        return token;
    }

    /**
     * 验证token
     *
     * @param token
     * @return
     * @throws Exception
     */
    public boolean validateToken(String token) throws Exception {
        if (StringUtils.isEmpty(token)) {
            throw new ServiceException("token验证失败");
        }
        if (redisService.exists(token) && TRUE_STRING.equals(redisService.get(token))) {
            this.redisService.setex(UUID.randomUUID().toString(), EXPIRED, FALSE_STRING);
            return true;
        }
        return false;
    }

    /**
     * 移除token
     *
     * @param token
     * @return
     * @throws Exception
     */
    public void removeToken(String token) throws Exception {
        if (StringUtils.isEmpty(token)) {
            throw new ServiceException("token移除失败");
        }
        redisService.del(token);
    }

}
