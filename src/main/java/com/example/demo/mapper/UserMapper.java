package com.example.demo.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import com.example.demo.po.User;

@Component
public interface UserMapper {

	@Select("SELECT * FROM users0 WHERE username = #{username}")
	User findByUserName(@Param("username") String username);
}
