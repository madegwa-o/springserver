package com.example.springserver.dtos;

import com.example.springserver.user.Role;
import java.util.Set;

public record UserRequestDto(
        String regNo,
        String annonumousName,
        String password,
        String schoolEmail,
        Set<Role> roles
) {}
