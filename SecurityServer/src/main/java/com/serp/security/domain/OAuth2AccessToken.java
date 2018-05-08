/**
 * 
 */
package com.serp.security.domain;

import java.util.Arrays;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * @author sanjeet choudhary
 *
 */
@Document
public class OAuth2AccessToken {

	@Id
	@Field("_id")
	private String tokenId;
	private byte[] token;
	private String authenticationId;
	private String username;
	private String clientId;
	private byte[] authentication;
	private String refreshToken;

	@PersistenceConstructor
	public OAuth2AccessToken(String tokenId, byte[] token, String authenticationId, String username, String clientId,
			byte[] authentication, String refreshToken) {
		super();
		this.tokenId = tokenId;
		this.token = token;
		this.authenticationId = authenticationId;
		this.username = username;
		this.clientId = clientId;
		this.authentication = authentication;
		this.refreshToken = refreshToken;
	}

	public String getTokenId() {
		return tokenId;
	}

	public void setTokenId(String tokenId) {
		this.tokenId = tokenId;
	}

	public byte[] getToken() {
		return token;
	}

	public void setToken(byte[] token) {
		this.token = token;
	}

	public String getAuthenticationId() {
		return authenticationId;
	}

	public void setAuthenticationId(String authenticationId) {
		this.authenticationId = authenticationId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public byte[] getAuthentication() {
		return authentication;
	}

	public void setAuthentication(byte[] authentication) {
		this.authentication = authentication;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(authentication);
		result = prime * result + ((authenticationId == null) ? 0 : authenticationId.hashCode());
		result = prime * result + ((clientId == null) ? 0 : clientId.hashCode());
		result = prime * result + ((refreshToken == null) ? 0 : refreshToken.hashCode());
		result = prime * result + Arrays.hashCode(token);
		result = prime * result + ((tokenId == null) ? 0 : tokenId.hashCode());
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
		OAuth2AccessToken other = (OAuth2AccessToken) obj;
		if (!Arrays.equals(authentication, other.authentication))
			return false;
		if (authenticationId == null) {
			if (other.authenticationId != null)
				return false;
		} else if (!authenticationId.equals(other.authenticationId))
			return false;
		if (clientId == null) {
			if (other.clientId != null)
				return false;
		} else if (!clientId.equals(other.clientId))
			return false;
		if (refreshToken == null) {
			if (other.refreshToken != null)
				return false;
		} else if (!refreshToken.equals(other.refreshToken))
			return false;
		if (!Arrays.equals(token, other.token))
			return false;
		if (tokenId == null) {
			if (other.tokenId != null)
				return false;
		} else if (!tokenId.equals(other.tokenId))
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
		builder.append("OAuth2AccessToken [tokenId=");
		builder.append(tokenId);
		builder.append(", token=");
		builder.append(Arrays.toString(token));
		builder.append(", authenticationId=");
		builder.append(authenticationId);
		builder.append(", username=");
		builder.append(username);
		builder.append(", clientId=");
		builder.append(clientId);
		builder.append(", authentication=");
		builder.append(Arrays.toString(authentication));
		builder.append(", refreshToken=");
		builder.append(refreshToken);
		builder.append("]");
		return builder.toString();
	}

}
