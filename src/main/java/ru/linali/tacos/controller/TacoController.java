package ru.linali.tacos.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TacoController {
    @GetMapping("tacos")
    public String tacos() {
        return "home";
    }

}
