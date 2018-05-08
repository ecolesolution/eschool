package com.serp.security.domain;

import org.bson.types.ObjectId;
import org.hibernate.validator.constraints.Email;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.serp.security.annotaions.CascadeSave;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

/**
 * @author sanjeet choudhary
 *
 */
@Document
public class User {

    @Id
    @NotNull
    @Size(min = 0, max = 50)
    private String username;

    @Size(min = 0, max = 500)
    private String password;

    @Email
    @Size(min = 0, max = 50)
    private String email;

    private boolean activated;

    @Size(min = 0, max = 100)
    @Field("activationkey")
    private String activationKey;

    @Size(min = 0, max = 100)
    @Field("resetpasswordkey")
    private String resetPasswordKey;

    @DBRef
    @Field("authorities")
    //@CascadeSave
    private Set<Authority> authorities;
    
	public User() {
		// TODO Auto-generated constructor stub
	}

    @PersistenceConstructor
	public User(String username, String password, String email, boolean activated, String activationKey,
			String resetPasswordKey, Set<Authority> authorities) {
		super();
		this.username = username;
		this.password = password;
		this.email = email;
		this.activated = activated;
		this.activationKey = activationKey;
		this.resetPasswordKey = resetPasswordKey;
		this.authorities = authorities;
	}

	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public boolean isActivated() {
		return activated;
	}


	public void setActivated(boolean activated) {
		this.activated = activated;
	}


	public String getActivationKey() {
		return activationKey;
	}


	public void setActivationKey(String activationKey) {
		this.activationKey = activationKey;
	}


	public String getResetPasswordKey() {
		return resetPasswordKey;
	}


	public void setResetPasswordKey(String resetPasswordKey) {
		this.resetPasswordKey = resetPasswordKey;
	}


	public Set<Authority> getAuthorities() {
		return authorities;
	}


	public void setAuthorities(Set<Authority> authorities) {
		this.authorities = authorities;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (activated ? 1231 : 1237);
		result = prime * result + ((activationKey == null) ? 0 : activationKey.hashCode());
		result = prime * result + ((authorities == null) ? 0 : authorities.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((resetPasswordKey == null) ? 0 : resetPasswordKey.hashCode());
		result = prime * result + ((username == null) ? 0 : username.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (activated != other.activated)
			return false;
		if (activationKey == null) {
			if (other.activationKey != null)
				return false;
		} else if (!activationKey.equals(other.activationKey))
			return false;
		if (authorities == null) {
			if (other.authorities != null)
				return false;
		} else if (!authorities.equals(other.authorities))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (resetPasswordKey == null) {
			if (other.resetPasswordKey != null)
				return false;
		} else if (!resetPasswordKey.equals(other.resetPasswordKey))
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("User [username=");
		builder.append(username);
		builder.append(", password=");
		builder.append(password);
		builder.append(", email=");
		builder.append(email);
		builder.append(", activated=");
		builder.append(activated);
		builder.append(", activationKey=");
		builder.append(activationKey);
		builder.append(", resetPasswordKey=");
		builder.append(resetPasswordKey);
		builder.append(", authorities=");
		builder.append(authorities);
		builder.append("]");
		return builder.toString();
	}

    

   
  
}
