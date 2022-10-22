package com.example.springbootblog.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class BlogTagRelation {
    private Long relationId;
    private Long blogId;
    private Integer tagId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    public Long getRelationId() {
        return relationId;
    }

    public void setRelationId(Long relationId) {
        this.relationId = relationId;
    }

    public Long getBlogId() {
        return blogId;
    }

    public void setBlogId(Long blogId) {
        this.blogId = blogId;
    }

    public Integer getTagId() {
        return tagId;
    }

    public void setTagId(Integer tagId) {
        this.tagId = tagId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "BlogTagRelation{" +
                "relationId=" + relationId +
                ", blogId=" + blogId +
                ", tagId=" + tagId +
                ", createTime=" + createTime +
                '}';
    }
}
