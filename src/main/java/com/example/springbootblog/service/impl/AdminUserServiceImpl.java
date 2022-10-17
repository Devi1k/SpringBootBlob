package com.example.springbootblog.service.impl;

import com.example.springbootblog.dao.AdminUserMapper;
import com.example.springbootblog.entity.AdminUser;
import com.example.springbootblog.service.AdminUserService;
import com.example.springbootblog.utils.MD5Util;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class AdminUserServiceImpl implements AdminUserService {
    @Resource
    private AdminUserMapper adminUserMapper;

    @Override
    public AdminUser login(String userName, String password) {
        String passwordMD5 = MD5Util.MD5Encode(password, "UTF-8");
        return adminUserMapper.login(userName, passwordMD5);
    }

    @Override
    public AdminUser getUserDetailById(Integer loginUserId) {
        return adminUserMapper.selectByPrimaryKey(loginUserId);
    }

    @Override
    public Boolean updatePassword(Integer loginUserId, String originalPassword, String newPassword) {
        AdminUser tempUser = adminUserMapper.selectByPrimaryKey(loginUserId);
        if (tempUser != null) {
            String originalPasswordMD5 = MD5Util.MD5Encode(originalPassword, "UTF-8");
            String newPasswordMD5 = MD5Util.MD5Encode(newPassword, "UTF-8");
            if (originalPasswordMD5.equals(tempUser.getLoginPassword())) {
                tempUser.setLoginPassword(newPasswordMD5);
                return adminUserMapper.updateByPrimaryKeySelective(loginUserId) > 0;
            }
        }

        return false;
    }

    @Override
    public Boolean updateUserName(Integer loginUserId, String loginUserName, String nickName) {
        AdminUser tempUser = adminUserMapper.selectByPrimaryKey(loginUserId);
        if (tempUser != null) {
            tempUser.setLoginUserName(loginUserName);
            tempUser.setNickName(nickName);
            return adminUserMapper.updateByPrimaryKeySelective(loginUserId) > 0;
        }
        return false;
    }
}
