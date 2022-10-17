package com.example.springbootblog.service;

import com.example.springbootblog.entity.AdminUser;

public interface AdminUserService {

    AdminUser login(String userName, String password);

    AdminUser getUserDetailById(Integer loginUserId);

    Boolean updatePassword(Integer loginUserId, String originalPassword, String newPassword);


    Boolean updateUserName(Integer loginUserId, String loginUserName, String nickName);

}
