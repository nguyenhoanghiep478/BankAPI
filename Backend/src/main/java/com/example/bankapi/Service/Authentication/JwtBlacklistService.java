package com.example.bankapi.Service.Authentication;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@Slf4j
public class JwtBlacklistService {
    private final RedisTemplate<String,String> redisTemplate;

    public void addToBlackList(String token,long expiration){
        redisTemplate.opsForValue().set(token,"blacklisted",expiration, TimeUnit.MILLISECONDS);
        String value = (String) redisTemplate.opsForValue().get(token);
        if(value == null){
            log.error("faild to add token {} to redis",token);
        }else{
            log.info("Successfully added token {} to redis",token);
        }
    }
    public boolean isBlackListed(String token){
        return Boolean.TRUE.equals(redisTemplate.hasKey(token));
    }

}
