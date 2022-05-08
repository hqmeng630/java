package com.hqmeng.demo.db.generator.mapper;

import com.hqmeng.demo.db.generator.bean.RbacPermission;

public interface RbacPermissionMapper {
    int deleteByPrimaryKey(Long id);

    int insert(RbacPermission record);

    int insertSelective(RbacPermission record);

    RbacPermission selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(RbacPermission record);

    int updateByPrimaryKey(RbacPermission record);
}