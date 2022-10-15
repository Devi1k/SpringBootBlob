package com.example.springbootblob.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

public class AdminUser implements Serializable {
    /**
     * primary key
     */
    private long id;
    /**
     * user_name
     */
    private String userName;
    /**
     * password
     */
    private String password;
    /**
     * token
     */
    private String userToken;
    /**
     * deleted or not
     */
    private int isDeleted;
    /**
     * created time
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date createdTime;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }

    public int getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(int isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }


    @Override
    public String toString() {
        return "AdminUser{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", userToken='" + userToken + '\'' +
                ", isDeleted=" + isDeleted +
                ", createdTime=" + createdTime +
                '}';
    }
}
