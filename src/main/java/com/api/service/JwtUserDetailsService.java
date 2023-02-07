package com.api.service;
import com.api.model.Role;
import com.api.model.UserDao;
import com.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


@Service
public class JwtUserDetailsService implements UserDetailsService {
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder bcryptEncoder;


	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserDao user = userRepository.findByEmail(username);
		if (user == null) {
			throw new UsernameNotFoundException("User not found with username: " + username);
		}
		Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
		List<Role>userRoles = new ArrayList<>(user.getRoles());
		userRoles.forEach(role ->{
			authorities.add(new SimpleGrantedAuthority(role.getRoleName()));
		} );
		return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
				authorities);
	}
}