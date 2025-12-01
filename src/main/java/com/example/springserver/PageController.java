package com.example.springserver;

import com.example.springserver.dtos.UserRequestDto;
import com.example.springserver.user.Role;
import com.example.springserver.user.User;
import com.example.springserver.user.UserRepository;
import com.example.springserver.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password4j.BcryptPassword4jPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Controller
@RequiredArgsConstructor
public class PageController {

    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final UserRepository userRepository;

    @GetMapping("/")
    public String index(Model model) {
        List<String> apis = List.of(
                "/api/v1/test/hello",
                "/api/v1/test/status",
                "/api/v1/test/secret-check"
        );

        model.addAttribute("apis", apis);
        return "index";
    }

    @GetMapping("/adduser")
    public String addUser(Model model) {
        List<User> users = userRepository.findAll();
        model.addAttribute("users", users);
        return "adduser";
    }
    @GetMapping("/users")
    public String users(Model model) {
        List<User> users = userRepository.findAll();
        model.addAttribute("users", users);
        return "users";
    }

    @PostMapping("/register")
    public String addUser(
            String regNo,
            String annonumousName,
            String password,
            String schoolEmail,
            String[] roles
    ) {

        // Convert checkbox string values → Set<Role>
        Set<Role> roleSet = roles == null
                ? Set.of()
                : Stream.of(roles)
                .map(Role::valueOf)
                .collect(Collectors.toSet());

        User user = User.builder()
                .regNo(regNo)
                .annonumousName(annonumousName)
                .password(passwordEncoder.encode(password))
                .schoolEmail(schoolEmail)
                .roles(roleSet)
                .build();

        userRepository.save(user);

        return "redirect:/users";
    }

}
