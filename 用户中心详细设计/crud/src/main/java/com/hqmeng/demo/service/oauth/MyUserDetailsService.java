package com.hqmeng.demo.service.oauth;

import com.alibaba.fastjson.JSONObject;
import com.hqmeng.demo.db.extend.mapper.RbacMapper;
import com.hqmeng.demo.db.generator.bean.RbacPermission;
import com.hqmeng.demo.db.generator.bean.RbacUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
@Slf4j
@Service
public class MyUserDetailsService implements UserDetailsService {
    @Autowired
    RbacMapper rbacMapper;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        RbacUser rbacUser = rbacMapper.selectUserByUsername(s);
        String username = rbacUser.getUsername();
        String password = rbacUser.getPassword();
        log.info("loadUserByUsername rbacUser {}", JSONObject.toJSONString(rbacUser));
        List<Integer> permissionIds = rbacMapper.selectPermissionIdByUserName(s);
        List<RbacPermission> rbacPermissions = rbacMapper.selectPermissionsByPermissionIds(permissionIds);
        String[] authorities = rbacPermissions.stream().map(p -> p.getMethod() + ";" + p.getUrl()).toArray(String[]::new);
        log.info("loadUserByUsername authorities {}", JSONObject.toJSONString(authorities));
        return new MyUserDetails(username, password, AuthorityUtils.createAuthorityList(authorities));
    }
}
