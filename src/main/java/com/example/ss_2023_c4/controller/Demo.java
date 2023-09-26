package com.example.ss_2023_c4.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Demo {

    @GetMapping("/demo")
    public String demo(){
        return "Demo";
    }

}
