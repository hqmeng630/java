package com.hqmeng.demo.db.generator.mapper;

import com.hqmeng.demo.db.generator.bean.RbacUser;

public interface RbacUserMapper {
    int deleteByPrimaryKey(Long id);

    int insert(RbacUser record);

    int insertSelective(RbacUser record);

    RbacUser selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(RbacUser record);

    int updateByPrimaryKey(RbacUser record);
}