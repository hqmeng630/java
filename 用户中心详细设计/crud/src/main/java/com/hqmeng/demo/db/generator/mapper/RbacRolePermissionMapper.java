package com.hqmeng.demo.db.generator.mapper;

import com.hqmeng.demo.db.generator.bean.RbacRolePermission;

public interface RbacRolePermissionMapper {
    int deleteByPrimaryKey(Long id);

    int insert(RbacRolePermission record);

    int insertSelective(RbacRolePermission record);

    RbacRolePermission selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(RbacRolePermission record);

    int updateByPrimaryKey(RbacRolePermission record);
}