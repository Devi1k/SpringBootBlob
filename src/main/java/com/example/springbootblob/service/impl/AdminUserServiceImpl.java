package com.example.springbootblob.service.impl;

import com.example.springbootblob.dao.AdminUserDao;
import com.example.springbootblob.entity.AdminUser;
import com.example.springbootblob.service.AdminUserService;
import com.example.springbootblob.utils.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("adminUserService")
public class AdminUserServiceImpl implements AdminUserService {
    @Resource
    private AdminUserDao adminUserDao;

    @Override
    public PageResult getAdminUserPage(PageUtil pageUtil) {
        List<AdminUser> users = adminUserDao.findAdminUsers(pageUtil);
        int total = adminUserDao.getTotalAdminUser(pageUtil);
        PageResult pageResult = new PageResult(users, total, pageUtil.getLimit(), pageUtil.getPage());
        return pageResult;
    }

    @Override
    public AdminUser updateTokenAndLogin(String userName, String password) {
        AdminUser adminUser = adminUserDao.getAdminUserByUserNameAndPassword(userName, MD5Util.MD5Encode(password, "UTF-8"));
        if (adminUser != null) {
            String token = getNewToken(System.currentTimeMillis() + "", adminUser.getId());
            if (adminUserDao.updateUserToken(adminUser.getId(), token) > 0) {
                adminUser.setUserToken(token);
                return adminUser;
            }
        }
        return null;
    }

    private String getNewToken(String sessionId, Long userId) {
        String src = sessionId + userId + NumberUtil.getRandomNum(4);
        return SystemUtil.genToken(src);
    }
}
