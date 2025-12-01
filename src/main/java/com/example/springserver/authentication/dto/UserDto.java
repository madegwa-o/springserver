package com.example.springserver.authentication.dto;

import com.example.springserver.course.Course;
import com.example.springserver.user.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {
    private int userId;

    private String regNo;
    private String annonumousName;
    private String schoolEmail;
    private List<Course> courses;
    private Set<Role> roles;
}
