package com.bizprout.web.api.security;

import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bizprout.web.app.dto.UserDTO;
import com.bizprout.web.app.repository.UserRepositoryImpl;
import com.bizprout.web.app.resource.AESencrp;

@Service("userDetailsServiceImpl")
@Transactional
public class UserDetailsServiceImpl implements UserDetailsService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private UserRepositoryImpl userRepositoryImpl;

	public UserDetails loadUserByUsername(String userId)
			throws UsernameNotFoundException {

		try {
			UserDTO dto = new UserDTO();
			dto.setUsername(userId);

			dto = (UserDTO) userRepositoryImpl.getUserData(userId);

			if (dto == null) {
				logger.debug("user not found with the provided username");
				throw new UsernameNotFoundException("User not found");
			}

			String password = AESencrp.decrypt(dto.getPassword());						

			logger.debug(" user from username " + dto.toString()
					+ dto.getPassword());

			return new org.springframework.security.core.userdetails.User(
					dto.getUsername(), password, getAuthorities(dto));
		} catch (Exception e) {
			throw new UsernameNotFoundException("User not found");
		}
	}

	private Set<GrantedAuthority> getAuthorities(UserDTO dto) {
		Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();

		if ("PPsuperadmin".equals(dto.getUsertype())) {
			GrantedAuthority superAdmin = new SimpleGrantedAuthority(
					"ROLE_PPsuperadmin");
			authorities.add(superAdmin);
		}
		if ("PPAdmin".equals(dto.getUsertype())) {
			GrantedAuthority admin = new SimpleGrantedAuthority("ROLE_PPAdmin");
			authorities.add(admin);
		}

		return authorities;
	}

}
