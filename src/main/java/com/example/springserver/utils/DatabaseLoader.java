package com.example.springserver.utils;//package com.example.FullBackend.utils;
//
//import com.example.FullBackend.course.Course;
//import com.example.FullBackend.course.CourseRepository;
//import com.example.FullBackend.user.User;
//import com.example.FullBackend.user.Role;
//import com.example.FullBackend.user.UserRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.context.annotation.Bean;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Component;
//
//import java.util.List;
//import java.util.Set;
//
//@Component
//@RequiredArgsConstructor
//public class DatabaseLoader {
//
//    private final UserRepository userRepository;
//    private final CourseRepository courseRepository;
//
//
//    public PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//
//    @Bean
//    public CommandLineRunner databaseInit() {
//        return args -> {
//
//            String hashedPassword = passwordEncoder.encode("1234");
//
//            // Create courses
//            Course course1 = Course.builder()
//                    .courseName("Mathematics")
//                    .courseCode("MATH101")
//                    .courseDescription("An introduction to basic mathematics.")
//                    .build();
//
//            Course course2 = Course.builder()
//                    .courseName("Physics")
//                    .courseCode("PHYS101")
//                    .courseDescription("Basic principles of physics.")
//                    .build();
//
//            Course course3 = Course.builder()
//                    .courseName("Chemistry")
//                    .courseCode("CHEM101")
//                    .courseDescription("Introduction to chemistry concepts.")
//                    .build();
//
//            Course course4 = Course.builder()
//                    .courseName("Biology")
//                    .courseCode("BIO101")
//                    .courseDescription("Fundamentals of biology.")
//                    .build();
//
//            // Save courses to the repository
//            courseRepository.saveAll(List.of(course1, course2, course3, course4));
//
//            // Create users and associate with courses
//            User user = User.builder()
//                    .annonumousName("john")
//                    .password(hashedPassword)
//                    .schoolEmail("john@d.com")
//                    .courses(List.of(course1, course2))
//                    .roles(Set.of(Role.DEVELOPER,Role.ADMIN))
//                    .build();
//
//            User user1 = User.builder()
//                    .annonumousName("peter")
//                    .password(hashedPassword)
//                    .schoolEmail("peter@l.com")
//                    .courses(List.of(course1, course2))
//                    .roles(Set.of(Role.LECTURER))
//                    .build();
//
//            User user2 = User.builder()
//                    .annonumousName("jane")
//                    .regNo("p106")
//                    .password(hashedPassword)
//                    .schoolEmail("jane@s.com")
//                    .courses(List.of(course2, course3, course4))
//                    .roles(Set.of(Role.STUDENT, Role.ADMIN))
//                    .build();
//
//            User user3 = User.builder()
//                    .annonumousName("adam")
//                    .regNo("p101")
//                    .password(hashedPassword)
//                    .schoolEmail("admin@d.com")
//                    .roles(Set.of(Role.ADMIN, Role.STUDENT, Role.LECTURER, Role.DEVELOPER))
//                    .build();
//
//            // Save users to the repository
//            userRepository.saveAll(List.of(user,user1, user2, user3));
//        };
//    }
//}
