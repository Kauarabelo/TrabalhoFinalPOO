package br.com.estudoskaua.trabalhofinalpoo.api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/test")
    public String test() {
        return "A aplicação está rodando!";
    }
}

