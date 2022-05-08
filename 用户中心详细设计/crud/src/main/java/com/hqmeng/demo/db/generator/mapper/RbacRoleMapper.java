package com.hqmeng.demo.db.generator.mapper;

import com.hqmeng.demo.db.generator.bean.RbacRole;

public interface RbacRoleMapper {
    int deleteByPrimaryKey(Long id);

    int insert(RbacRole record);

    int insertSelective(RbacRole record);

    RbacRole selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(RbacRole record);

    int updateByPrimaryKey(RbacRole record);
}