package com.spring.springsecuritytest.dao;

import com.spring.springsecuritytest.entity.Role;
import com.spring.springsecuritytest.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (User)表数据库访问层
 *
 * @author makejava
 * @since 2020-05-15 17:35:06
 */
@Mapper
public interface UserDao {

    User loadUserByUserName(@Param("username") String username);

    List<Role> getUserRolesByUid(@Param("id") Integer id);
}