package com.agicomputers.LoanAPI.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class TemplateController  {

    @GetMapping()
    public String getHomePage() {
        return "home";
    }

    @GetMapping("login")
    public String getLoginPage() {
        return "login";
    }

    @GetMapping("invalid_session")
    public String getInvalidSessionPage(){
        return "invalid_session";
    }
}


