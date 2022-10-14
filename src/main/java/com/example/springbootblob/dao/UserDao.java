package com.example.springbootblob.dao;

import com.example.springbootblob.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserDao {

    /**
     * @return
     */
    @Select("select id, name, password from tb_user order by id desc")
    List<User> findAllUsers();

    /**
     * @param user
     * @return
     */
    @Insert("insert into tb_user (name,password) values (#{name},#{password})")
    int insertUser(User user);

    /**
     * @param user
     * @return
     */
    @Update("update tb_user set name=#{name},password=#{password} where id=#{id}")
    int updateUser(User user);

    /**
     * @param id
     * @return
     */
    @Delete("delete from tb_user where id=#{id}")
    int deleteUser(Integer id);
}
