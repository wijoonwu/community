package com.callbus.community.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ArticleController {

    @GetMapping("/")
    public String Welcome(){
        return "";
    }

}
