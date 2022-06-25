package com.agicomputers.LoanAPI.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class TemplateController  {

    @GetMapping()
    public String getHomeView() {
        return "login";
    }

    @GetMapping("login")
    public String getLogin() {
        return "login";
    }
    @GetMapping("dashboard")
    public String getDashboard(){
        return "dashboard";
    }

    @GetMapping("invalid_session")
    public String getInvalidSession(){
        return "invalid_session";
    }

    @GetMapping("failed")
    public String getFailed(){return "fail";}
}


