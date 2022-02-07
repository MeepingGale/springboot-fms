package com.example.demo2.service.impl;

import com.example.demo2.component.UserDetail;
import com.example.demo2.mapper.UserMapper;
import com.example.demo2.model.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailService implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userMapper.findByUsername(username);

        if(user == null) throw new UsernameNotFoundException("Use doesn't exist in the database.");

        return new UserDetail(user);
    }
    
}
