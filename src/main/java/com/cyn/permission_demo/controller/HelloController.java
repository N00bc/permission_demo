package com.cyn.permission_demo.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

/**
 * @author Godc
 * @description:
 * @date 2023/1/29 10:24
 */
@RestController
@RequestMapping("/hello")
public class HelloController {
    @GetMapping("/")
    public String hello() {
        Authentication user = SecurityContextHolder.getContext().getAuthentication();
        Collection<? extends GrantedAuthority> authorities = user.getAuthorities();
        Object principal = user.getPrincipal();
        String msg = "Hello " + principal + " !";
        System.out.println(msg);
        return msg;
    }
}
