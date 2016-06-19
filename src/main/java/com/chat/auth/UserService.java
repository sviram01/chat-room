package com.chat.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.chat.model.User;
import com.chat.model.UserDao;

/**
 * @author Shreya Viramgama
 *
 */
@Service
public class UserService implements UserDetailsService {
	private final UserDao userDao;

	@Autowired
	public UserService(UserDao userDao) {
		this.userDao = userDao;
	}

	@Override
	public CurrentUser loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userDao.findByUsername(username);

		return new CurrentUser(user);
	}
}