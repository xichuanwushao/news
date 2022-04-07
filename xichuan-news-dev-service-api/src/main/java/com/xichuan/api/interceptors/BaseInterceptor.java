package com.xichuan.api.interceptors;

import com.xichuan.vommon.exception.GraceException;
import com.xichuan.vommon.result.ResponseStatusEnum;
import com.xichuan.vommon.util.RedisOperator;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author : wuxiao
 * @date : 14:55 2022/4/7
 */
public class BaseInterceptor {
    @Autowired
    public RedisOperator redis;

    public static final String REDIS_USER_TOKEN = "redis_user_token";
    public static final String REDIS_USER_INFO = "redis_user_info";
    public boolean verifyUserIdToken(String id,
                                     String token,
                                     String redisKeyPrefix) {

        if (StringUtils.isNotBlank(id) && StringUtils.isNotBlank(token)) {
            String redisToken = redis.get(redisKeyPrefix + ":" + id);
            if (StringUtils.isBlank(id)) {
                GraceException.display(ResponseStatusEnum.UN_LOGIN);
                return false;
            } else {
                if (!redisToken.equalsIgnoreCase(token)) {
                    GraceException.display(ResponseStatusEnum.TICKET_INVALID);
                    return false;
                }
            }
        } else {
            GraceException.display(ResponseStatusEnum.UN_LOGIN);
            return false;
        }

        return true;
    }


}
