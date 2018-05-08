/**
 * 
 */
package com.serp.security.domain;

import java.util.Arrays;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author sanjeet
 *
 */
@Document
public class OAuth2RefreshToken {
	@Id
	private String tokenId;
	private byte[] token;
	private byte[] authentication;

	public OAuth2RefreshToken() {

	}

	@PersistenceConstructor
	public OAuth2RefreshToken(String tokenId, byte[] token, byte[] authentication) {
		super();
		this.tokenId = tokenId;
		this.token = token;
		this.authentication = authentication;
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

	public byte[] getAuthentication() {
		return authentication;
	}

	public void setAuthentication(byte[] authentication) {
		this.authentication = authentication;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(authentication);
		result = prime * result + Arrays.hashCode(token);
		result = prime * result + ((tokenId == null) ? 0 : tokenId.hashCode());
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
		OAuth2RefreshToken other = (OAuth2RefreshToken) obj;
		if (!Arrays.equals(authentication, other.authentication))
			return false;
		if (!Arrays.equals(token, other.token))
			return false;
		if (tokenId == null) {
			if (other.tokenId != null)
				return false;
		} else if (!tokenId.equals(other.tokenId))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("OAuth2RefreshToken [tokenId=");
		builder.append(tokenId);
		builder.append(", token=");
		builder.append(Arrays.toString(token));
		builder.append(", authentication=");
		builder.append(Arrays.toString(authentication));
		builder.append("]");
		return builder.toString();
	}

}
