package com.warriaty.dormbe.user.mapper;


import com.warriaty.dormbe.user.entity.User;
import com.warriaty.dormbe.user.model.response.UserResponse;
import org.mapstruct.Mapper;


@Mapper
public interface UserMapper {

    UserResponse mapToUserResponse(User user);
}

