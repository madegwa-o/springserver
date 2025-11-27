package com.example.springserver;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class PageContoller {

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
}
