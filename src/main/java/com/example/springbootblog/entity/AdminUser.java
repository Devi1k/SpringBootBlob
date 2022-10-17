package com.example.springbootblog.entity;

public class AdminUser {
    private Integer adminUserId;

    private String loginUserName;

    private String loginPassword;

    private String nickName;

    private Byte locked;

    public Integer getAdminUserId() {
        return adminUserId;
    }

    public void setAdminUserId(Integer adminUserId) {
        this.adminUserId = adminUserId;
    }

    public String getLoginUserName() {
        return loginUserName;
    }

    public void setLoginUserName(String loginUserName) {
        this.loginUserName = loginUserName == null ? null : loginUserName.trim();
    }

    public String getLoginPassword() {
        return loginPassword;
    }

    public void setLoginPassword(String loginPassword) {
        this.loginPassword = loginPassword == null ? null : loginPassword.trim();
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName == null ? null : nickName.trim();
    }

    public Byte getLocked() {
        return locked;
    }

    public void setLocked(Byte locked) {
        this.locked = locked;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(getClass().getSimpleName());
        stringBuilder.append(" [");
        stringBuilder.append("Hash = ").append(hashCode());
        stringBuilder.append(", adminUserId=").append(adminUserId);
        stringBuilder.append(", loginUserName=").append(loginUserName);
        stringBuilder.append(", loginPassword=").append(loginPassword);
        stringBuilder.append(", nickName=").append(nickName);
        stringBuilder.append(", locked=").append(locked);
        stringBuilder.append("]");
        return stringBuilder.toString();
    }
}