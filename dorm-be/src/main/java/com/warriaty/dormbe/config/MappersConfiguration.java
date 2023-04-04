package com.warriaty.dormbe.config;

import com.warriaty.dormbe.user.mapper.UserMapper;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MappersConfiguration {

    @Bean
    UserMapper createUserMapper() {
        return Mappers.getMapper(UserMapper.class);
    }
}
