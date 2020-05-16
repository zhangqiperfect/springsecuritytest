package com.spring.springsecuritytest.service;

import com.spring.springsecuritytest.dao.UserDao;
import com.spring.springsecuritytest.entity.Role;
import com.spring.springsecuritytest.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author ZQ
 * @create 2020-05-15
 */
@Service
@Slf4j
public class UserService implements UserDetailsService {
    @Autowired
    UserDao userDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDao.loadUserByUserName(username);
        System.out.println("user===================================="+user);
        if (user == null) {
            log.error("账户不存在");
//            throw new UsernameNotFoundException("账户不存在");
        }
        List<Role> roleList = userDao.getUserRolesByUid(user.getId());
        System.out.println("========================================");
        System.out.println("========================================"+roleList);
        user.setRoles(roleList);
        return user;
    }
}
