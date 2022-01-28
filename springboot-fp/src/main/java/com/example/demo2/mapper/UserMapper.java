package com.example.demo2.mapper;

import com.example.demo2.model.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper extends JpaRepository<User,Integer> {
     
    User findByUsername(String username);
}
