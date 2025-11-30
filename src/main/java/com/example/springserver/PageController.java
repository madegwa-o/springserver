package com.example.springserver;


import com.example.springserver.dtos.UserDTO;
import com.example.springserver.user.User;
import com.example.springserver.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class PageController {

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

    @GetMapping("/users")
    public String users(Model model) {

        List<User> users = userRepository.findAll();

        model.addAttribute("users", users);
        return "users";
    }

    @PostMapping("/addUser")
    public String addUser(UserDTO user) {

        User newUser = User.builder()
                .annonumousName(user.getUsername())
                .schoolEmail(user.getEmail())
                .build();
        userRepository.save(newUser);
        return "redirect:/users";
    }
}
