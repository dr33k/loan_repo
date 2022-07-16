package com.agicomputers.LoanAPI.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;


@Controller
@RequestMapping("/")
public class TemplateController  {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/login")
    public String getHomeView() {
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


