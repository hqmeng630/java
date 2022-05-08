package com.hqmeng.demo.db.extend.mapper;

import com.hqmeng.demo.db.generator.bean.RbacPermission;
import com.hqmeng.demo.db.generator.bean.RbacUser;

import java.util.List;

public interface RbacMapper {
   List<Integer> selectPermissionIdByUserName(String username);

   List<RbacPermission> selectPermissionsByPermissionIds(List<Integer> ids);

   RbacUser selectUserByUsername(String username);
}
