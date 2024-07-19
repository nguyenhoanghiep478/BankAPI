package com.example.bankapi.Service;

import jakarta.annotation.PostConstruct;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.stereotype.Service;

@Service
public class RedisConnectionChecker {
    private final JedisConnectionFactory connectionFactory;

    public RedisConnectionChecker(JedisConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }
    @PostConstruct
    public void checkConnection() {
        try (RedisConnection connection = connectionFactory.getConnection()) {
            connection.ping();
            System.out.println("Redis connection is successful");
        } catch (Exception e) {
            System.err.println("Redis connection failed: " + e.getMessage());
        }
    }
}
