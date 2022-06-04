package com.sgwannabig.smallgift.springboot.mapper;

import com.sgwannabig.smallgift.springboot.domain.users.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface AuthMapper {

    @Insert("INSERT INTO user(username,hashedPassword) VALUES(#{username},#{hashedPassword})")
    int regist(@Param("username")String username, @Param("hashedPassword")String hashedPassword);



    @Select("SELECT username FROM user WHERE username=#{username}")
    User isExists(@Param("username")String username);

    @Select("SELECT id FROM user WHERE username=#{username}")
    int getId(@Param("username")String username);


}
