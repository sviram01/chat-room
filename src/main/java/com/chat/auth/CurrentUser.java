package com.chat.auth;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.chat.model.User;

/**
 * @author Shreya Viramgama
 *
 */
public class CurrentUser implements UserDetails{

	private static final long serialVersionUID = -8397850353506022636L;

	private User user;
	
	public CurrentUser(User user){
		this.user = user;
	}
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return null;
	}

	@Override
	public String getPassword() {
		return user!=null?user.getPassword():null;
	}

	@Override
	public String getUsername() {
		return user!=null?user.getUsername():null;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}
