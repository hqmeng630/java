package com.hqmeng.demo.controller;

import com.hqmeng.demo.db.extend.mapper.RbacMapper;
import com.hqmeng.demo.db.generator.bean.RbacUser;
import com.hqmeng.demo.db.generator.mapper.RbacUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    RbacMapper rbacMapper;
    @Autowired
    RbacUserMapper rbacUserMapper;

    @GetMapping("/getCurrentUser")
    public Object getCurrentUser(Authentication authentication) {
        return authentication;
    }

    @PostMapping("/changePassword")
    public boolean changePassword(@RequestBody RbacUser rbacUser) {
        RbacUser rbacUser1 = rbacMapper.selectUserByUsername(rbacUser.getUsername());
        rbacUser1.setPassword(passwordEncoder.encode(rbacUser.getPassword()));
        rbacUserMapper.updateByPrimaryKey(rbacUser1);
        return true;
    }
}
