package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.mapper.UserMapper;
import com.example.demo.po.User;

@Service
public class MyUserDetailsService implements UserDetailsService {

	@Autowired
	private UserMapper userMapper;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userMapper.findByUserName(username);
		if (user == null) {
			throw new UsernameNotFoundException("用戶不存在！");
		}
		List<GrantedAuthority> authorities = generateAuthorities(user.getRoles());
		user.setAuthorities(authorities);
		return user;
	}

	private List<GrantedAuthority> generateAuthorities(String roles) {
		return AuthorityUtils.commaSeparatedStringToAuthorityList(roles);
//		return convertSeparatedStringToAuthorityList(roles, ";");
	}

	private List<GrantedAuthority> convertSeparatedStringToAuthorityList(String roles, String separator) {
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		String[] roleArray = roles.split(separator);
		if (roles != null && "".equals(roles)) {
			for (String role : roleArray) {
				authorities.add(new SimpleGrantedAuthority(role));
			}
		}
		return authorities;
	}

}
