package com.warriaty.dormbe.user.model.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.RequiredArgsConstructor;


@Data
@RequiredArgsConstructor
public class UserRequest {

    @NotBlank
    private final String email;

    @NotBlank
    private final String password;
}
