package ru.linali.tacos.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/")
    // про "home" - Это значение интерпретируется как
    // логическое имя представления. Т.е. это работает н с помощью Thymeleaf.
    public String home(){
        return "home";
    }

}
