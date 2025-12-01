package com.example.springserver.user;

import com.example.springserver.course.Course;
import com.example.springserver.course.CourseRepository;
import com.example.springserver.dtos.UserRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final CourseRepository courseRepository;

    public User createUser(UserRequestDto dto) {


        User user = User.builder()
                .regNo(dto.regNo())
                .annonumousName(dto.annonumousName())
                .password(dto.password())
                .schoolEmail(dto.schoolEmail())
                .roles(dto.roles())
                .build();

        return userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUser(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User Not Found"));
    }

    public User updateUser(Long id, UserRequestDto dto) {

        User user = getUser(id);


        user.setRegNo(dto.regNo());
        user.setAnnonumousName(dto.annonumousName());
        user.setPassword(dto.password());
        user.setSchoolEmail(dto.schoolEmail());
        user.setRoles(dto.roles());

        return userRepository.save(user);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
