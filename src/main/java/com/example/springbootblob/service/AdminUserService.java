package com.example.springbootblob.service;

import com.example.springbootblob.entity.AdminUser;
import com.example.springbootblob.utils.PageResult;
import com.example.springbootblob.utils.PageUtil;

public interface AdminUserService {

    PageResult getAdminUserPage(PageUtil pageUtil);

    AdminUser updateTokenAndLogin(String userName, String password);
}
