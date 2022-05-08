package com.hqmeng.demo.db.generator.mapper;

import com.hqmeng.demo.db.generator.bean.RbacUserRole;

public interface RbacUserRoleMapper {
    int deleteByPrimaryKey(Long id);

    int insert(RbacUserRole record);

    int insertSelective(RbacUserRole record);

    RbacUserRole selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(RbacUserRole record);

    int updateByPrimaryKey(RbacUserRole record);
}