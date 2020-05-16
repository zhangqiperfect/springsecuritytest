package com.spring.springsecuritytest;

import com.spring.springsecuritytest.dao.UserDao;
import com.spring.springsecuritytest.entity.Role;
import com.spring.springsecuritytest.entity.User;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.SpringVersion;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
class SpringsecuritytestApplicationTests {
    @Autowired
    UserDao userDao;

    @Test
    void contextLoads() {
    }

    @Test
    public void testForeach() {

        List<Role> roles = new ArrayList<>();
        roles.add(new Role(1, "u1", "user1"));
        roles.add(new Role(2, "u2", "user2"));
        roles.forEach(r -> {
            System.out.println(r);
        });
    }

    @Test
    public void testLoadUserByUserName() {
        User user = userDao.loadUserByUserName("user");
        System.out.println(user);
    }

    @Test
    public void testPasswordEncoder() {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encode = passwordEncoder.encode("123456");
        System.out.println(encode);
    }

    @Test
    public void testGetUserRolesByUid() {
        List<Role> roleList = userDao.getUserRolesByUid(1);
        roleList.forEach(r -> {
            System.out.println(r);
        });
    }
    @Test
    public void test() {
        System.out.println(SpringVersion.getVersion());

    }
}
