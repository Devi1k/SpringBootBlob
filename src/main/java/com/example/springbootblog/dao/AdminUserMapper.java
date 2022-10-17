package com.example.springbootblog.dao;

import com.example.springbootblog.entity.AdminUser;
import org.apache.ibatis.annotations.Param;

public interface AdminUserMapper {
    int insert(AdminUser record);

    int insertSelective(AdminUser record);

    AdminUser login(@Param("userName") String userName, @Param("password") String password);

    AdminUser selectByPrimaryKey(Integer adminUserId);

    int updateByPrimaryKey(Integer adminUserId);

    int updateByPrimaryKeySelective(Integer adminUserId);
}
