package com.classican.bankaccountstatement.model.dto.request;

import java.util.Collection;
import java.util.Objects;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

/**
 * Application User DTO
 */
public class ApplicationUserDTO extends User {

	private static final long serialVersionUID = 1L;
	private long userId;

	public ApplicationUserDTO(String username, String password,
			Collection<? extends GrantedAuthority> authorities) {
		super(username, password, true, true, true, true, authorities);
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		if (!super.equals(o)) return false;
		ApplicationUserDTO that = (ApplicationUserDTO) o;
		return userId == that.userId;
	}

	@Override
	public int hashCode() {
		return Objects.hash(super.hashCode(), userId);
	}
}
