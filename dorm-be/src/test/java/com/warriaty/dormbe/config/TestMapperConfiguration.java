package com.warriaty.dormbe.config;

import com.warriaty.dormbe.user.mapper.UserMapper;
import org.mapstruct.factory.Mappers;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class TestMapperConfiguration {

    @Bean
    UserMapper createUserMapper() {
        return Mappers.getMapper(UserMapper.class);
    }
}
