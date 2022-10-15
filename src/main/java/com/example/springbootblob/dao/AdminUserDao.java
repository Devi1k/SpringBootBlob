package com.example.springbootblob.dao;

import com.example.springbootblob.entity.AdminUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface AdminUserDao {

    List<AdminUser> findAdminUsers(Map param);

    int getTotalAdminUser(Map param);

    AdminUser getAdminUserByUserNameAndPassword(@Param("username") String userName, @Param("password") String password);

    AdminUser getAdminUserByToken(String userToken);

    AdminUser getAdminUserById(Integer id);

    AdminUser getAdminUserByUserName(String userName);

    int addUser(AdminUser adminUser);

    int insertUsersBatch(@Param("adminUsers") List<AdminUser> adminUsers);

    int updateUserPassword(@Param("userId") Long userId, @Param("newPassword") String newPassword);

    int updateUserToken(@Param("userId") Long userId, @Param("newToken") String newToken);

    int deleteBatch(Object[] ids);
}
