package com.moma.momaadmin.config;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.io.*;
import java.time.Duration;

@Configuration
public class RedisConfig {

    @Value("${spring.redis.host}")
    private String host;

    @Value("${spring.redis.port}")
    private Integer port;

    @Value("${spring.redis.password}")
    private String password;

    @Value("${spring.redis.lettuce.pool.max-idle}")
    private Integer maxIdle;

    @Value("${spring.redis.lettuce.pool.min-idle}")
    private Integer minIdle;

    @Value("${spring.redis.lettuce.pool.max-active}")
    private Integer maxTotal;

    @Value("${spring.redis.lettuce.pool.max-wait}")
    private Duration maxWaitMillis;


    @Bean
    LettuceConnectionFactory lettuceConnectionFactory() {
        GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
        poolConfig.setMaxIdle(maxIdle == null ? 8 : maxIdle);
        poolConfig.setMinIdle(minIdle == null ? 1 : minIdle);
        poolConfig.setMaxTotal(maxTotal == null ? 8 : maxTotal);
        poolConfig.setMaxWaitMillis(maxWaitMillis == null ? 5000L : maxWaitMillis.toMillis());
        LettucePoolingClientConfiguration lettucePoolingClientConfiguration = LettucePoolingClientConfiguration.builder()
                .poolConfig(poolConfig).build();
        RedisStandaloneConfiguration redisConfig = new RedisStandaloneConfiguration();
        redisConfig.setHostName(host);
        redisConfig.setPort(port);
        if (password != null && !"".equals(password)) {
            redisConfig.setPassword(password);
        }
        return new LettuceConnectionFactory(redisConfig, lettucePoolingClientConfiguration);
    }

    @Bean(name = "redisTemplate")
    public RedisTemplate<String, Object> redisTemplate(LettuceConnectionFactory lettuceConnectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(lettuceConnectionFactory);
        MyRedisSerializer myRedisSerializer=new MyRedisSerializer();
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(myRedisSerializer);
        template.setHashValueSerializer(myRedisSerializer);
        return template;
    }

    static class MyRedisSerializer implements RedisSerializer<Object> {

        @Override
        public byte[] serialize(Object o) throws SerializationException {
            return serializeObj(o);
        }

        @Override
        public Object deserialize(byte[] bytes) throws SerializationException {
            return deserializeObj(bytes);
        }

        /**
         * ?????????
         * @param object
         * @return
         */
        private static byte[] serializeObj(Object object) {
            ObjectOutputStream outputStream = null;
            ByteArrayOutputStream arrayOutputStream = null;
            try {
                arrayOutputStream = new ByteArrayOutputStream();
                outputStream = new ObjectOutputStream(arrayOutputStream);
                outputStream.writeObject(object);
                byte[] bytes = arrayOutputStream.toByteArray();
                return bytes;
            } catch (Exception e) {
                throw new RuntimeException("???????????????",e);
            }
        }

        private static Object deserializeObj(byte[] bytes){
            if (bytes==null){
                return null;
            }
            ByteArrayInputStream inputStream=null;
            inputStream=new ByteArrayInputStream(bytes);
            ObjectInputStream objectInputStream= null;
            try {
                objectInputStream = new ObjectInputStream(inputStream);
                return objectInputStream.readObject();
            } catch (Exception e) {
                throw new RuntimeException("??????????????????",e);
            }

        }
    }
}
