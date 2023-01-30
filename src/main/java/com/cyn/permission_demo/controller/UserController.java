package com.cyn.permission_demo.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Godc
 * @description:
 * @date 2023/1/29 10:33
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @GetMapping("/add")
//    @PreAuthorize("hasPermission('/add','sys:user:add')")
//    @PreAuthorize("hasAuthority('sys:user:add')")
    @PreAuthorize("hasPermission('sys:user:add')")
    public String add() {
        return "add";
    }

    @GetMapping("/delete")
//    @PreAuthorize("hasPermission('/delete','sys:user:delete')")
//    @PreAuthorize("hasAuthority('sys:user:delete')")
    @PreAuthorize("hasAnyPermission('sys:user:delete')")
    public String delete() {
        return "delete";
    }

    @GetMapping("/select")
//    @PreAuthorize("hasPermission('/update','sys:user:select')")
//    @PreAuthorize("hasAuthority('sys:user:select')")
    @PreAuthorize("hasAllPermission('sys:user:select','sys:user:delete')")
    public String select() {
        return "select";
    }

    @GetMapping("/update")
//    @PreAuthorize("hasPermission('/update','sys:user:update')")
//    @PreAuthorize("hasAuthority('sys:user:update')")
    @PreAuthorize("hasPermission('sys:user:update')")
    public String update() {
        return "update";
    }
}
