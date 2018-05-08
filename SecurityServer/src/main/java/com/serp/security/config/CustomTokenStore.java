/**
 * 
 */
package com.serp.security.config;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import static java.util.Objects.nonNull;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.common.util.SerializationUtils;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.AuthenticationKeyGenerator;
import org.springframework.security.oauth2.provider.token.DefaultAuthenticationKeyGenerator;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.stereotype.Component;
import com.serp.security.repository.OAuth2AccessTokenRepository;
import com.serp.security.repository.OAuth2RefreshTokenRepository;

/**
 * @author sanjeet choudhary
 *
 */
@Component
public class CustomTokenStore implements TokenStore {
	private static final Log LOG = LogFactory.getLog(CustomTokenStore.class);

	@Autowired
	OAuth2AccessTokenRepository oauth2AccessTokenRepository;
	@Autowired
	OAuth2RefreshTokenRepository oauth2RefreshTokenRepository;
	
	private AuthenticationKeyGenerator authenticationKeyGenerator = new DefaultAuthenticationKeyGenerator();

	public void setAuthenticationKeyGenerator(AuthenticationKeyGenerator authenticationKeyGenerator) {
		this.authenticationKeyGenerator = authenticationKeyGenerator;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.security.oauth2.provider.token.TokenStore#
	 * readAuthentication(org.springframework.security.oauth2.common.
	 * OAuth2AccessToken)
	 */
	@Override
	public OAuth2Authentication readAuthentication(OAuth2AccessToken token) {
		// TODO Auto-generated method stub
		return readAuthentication(token.getValue());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.security.oauth2.provider.token.TokenStore#
	 * readAuthentication(java.lang.String)
	 */
	@Override
	public OAuth2Authentication readAuthentication(String token) {
		// TODO Auto-generated method stub
		final String tokenId = extractTokenKey(token);
		OAuth2Authentication authentication = null;
		com.serp.security.domain.OAuth2AccessToken oAuth2AccessToken = oauth2AccessTokenRepository
				.findByTokenId(tokenId);
		if (nonNull(oAuth2AccessToken)) {
			try {
				return deserializeAuthentication(oAuth2AccessToken.getAuthentication());

			} catch (IllegalArgumentException e) {
				removeAccessToken(token);
			}
		}
		return authentication;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.security.oauth2.provider.token.TokenStore#
	 * storeAccessToken(org.springframework.security.oauth2.common.
	 * OAuth2AccessToken,
	 * org.springframework.security.oauth2.provider.OAuth2Authentication)
	 */
	@Override
	public void storeAccessToken(OAuth2AccessToken token, OAuth2Authentication authentication) {
		String refreshToken = null;
		if (token.getRefreshToken() != null) {
			refreshToken = token.getRefreshToken().getValue();
		}

		if (readAccessToken(token.getValue()) != null) {
			removeAccessToken(token.getValue());
		}

		com.serp.security.domain.OAuth2AccessToken oAuth2AccessToken = new com.serp.security.domain.OAuth2AccessToken(
				extractTokenKey(token.getValue()), serializeAccessToken(token),
				authenticationKeyGenerator.extractKey(authentication),
				authentication.isClientOnly() ? null : authentication.getName(),
				authentication.getOAuth2Request().getClientId(), serializeAuthentication(authentication),
				extractTokenKey(refreshToken));

		oauth2AccessTokenRepository.save(oAuth2AccessToken);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.security.oauth2.provider.token.TokenStore#readAccessToken
	 * (java.lang.String)
	 */
	@Override
	public OAuth2AccessToken readAccessToken(String tokenValue) {
		OAuth2AccessToken accessToken = null;
		com.serp.security.domain.OAuth2AccessToken oAuth2AccessToken = oauth2AccessTokenRepository
				.findByTokenId(extractTokenKey(tokenValue));
		if (nonNull(oAuth2AccessToken)) {
			try {
				accessToken = deserializeAccessToken(oAuth2AccessToken.getToken());
			} catch (IllegalArgumentException e) {
				LOG.error("Could not extract access token for authentication " + tokenValue, e);
				removeAccessToken(tokenValue);
			}
		}
		return accessToken;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.security.oauth2.provider.token.TokenStore#
	 * removeAccessToken(org.springframework.security.oauth2.common.
	 * OAuth2AccessToken)
	 */
	@Override
	public void removeAccessToken(OAuth2AccessToken token) {
		removeAccessToken(token.getValue());
	}

	public void removeAccessToken(String tokenValue) {
		final String tokenId = extractTokenKey(tokenValue);
		oauth2AccessTokenRepository.deleteByTokenId(tokenId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.security.oauth2.provider.token.TokenStore#
	 * storeRefreshToken(org.springframework.security.oauth2.common.
	 * OAuth2RefreshToken,
	 * org.springframework.security.oauth2.provider.OAuth2Authentication)
	 */
	@Override
	public void storeRefreshToken(OAuth2RefreshToken refreshToken, OAuth2Authentication authentication) {
		com.serp.security.domain.OAuth2RefreshToken oAuth2RefreshToken = new com.serp.security.domain.OAuth2RefreshToken(
				extractTokenKey(refreshToken.getValue()), serializeRefreshToken(refreshToken),
				serializeAuthentication(authentication));
		oauth2RefreshTokenRepository.save(oAuth2RefreshToken);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.security.oauth2.provider.token.TokenStore#
	 * readRefreshToken(java.lang.String)
	 */
	@Override
	public OAuth2RefreshToken readRefreshToken(String tokenValue) {
		OAuth2RefreshToken refreshToken = null;
		try {
			com.serp.security.domain.OAuth2RefreshToken oAuth2RefreshToken = oauth2RefreshTokenRepository
					.findByTokenId(extractTokenKey(tokenValue));
			if (nonNull(oAuth2RefreshToken)) {
				refreshToken = deserializeRefreshToken(oAuth2RefreshToken.getToken());
			}
		} catch (IllegalArgumentException e) {
			LOG.warn("Failed to deserialize refresh token for token " + tokenValue, e);
			removeRefreshToken(tokenValue);
		}
		return refreshToken;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.security.oauth2.provider.token.TokenStore#
	 * readAuthenticationForRefreshToken(org.springframework.security.oauth2.common.
	 * OAuth2RefreshToken)
	 */
	@Override
	public OAuth2Authentication readAuthenticationForRefreshToken(OAuth2RefreshToken token) {
		return readAuthenticationForRefreshToken(token.getValue());
	}

	public OAuth2Authentication readAuthenticationForRefreshToken(String value) {
		OAuth2Authentication authentication = null;
		try {
			com.serp.security.domain.OAuth2RefreshToken oAuth2RefreshToken = oauth2RefreshTokenRepository
					.findByTokenId(extractTokenKey(value));
			if (nonNull(oAuth2RefreshToken)) {
				authentication = deserializeAuthentication(oAuth2RefreshToken.getAuthentication());
			}
		} catch (IllegalArgumentException e) {
			LOG.warn("Failed to deserialize access token for " + value, e);
			removeRefreshToken(value);
		}
		return authentication;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.security.oauth2.provider.token.TokenStore#
	 * removeRefreshToken(org.springframework.security.oauth2.common.
	 * OAuth2RefreshToken)
	 */
	@Override
	public void removeRefreshToken(OAuth2RefreshToken token) {
		removeRefreshToken(token.getValue());
	}

	public void removeRefreshToken(String token) {
		oauth2RefreshTokenRepository.deleteByTokenId(extractTokenKey(token));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.security.oauth2.provider.token.TokenStore#
	 * removeAccessTokenUsingRefreshToken(org.springframework.security.oauth2.common
	 * .OAuth2RefreshToken)
	 */
	@Override
	public void removeAccessTokenUsingRefreshToken(OAuth2RefreshToken refreshToken) {
		removeAccessTokenUsingRefreshToken(refreshToken.getValue());
	}

	public void removeAccessTokenUsingRefreshToken(String refreshToken) {
		oauth2AccessTokenRepository.deleteByRefreshToken(extractTokenKey(refreshToken));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.security.oauth2.provider.token.TokenStore#getAccessToken(
	 * org.springframework.security.oauth2.provider.OAuth2Authentication)
	 */
	@Override
	public OAuth2AccessToken getAccessToken(OAuth2Authentication authentication) {
		OAuth2AccessToken accessToken = null;
		String key = authenticationKeyGenerator.extractKey(authentication);
		com.serp.security.domain.OAuth2AccessToken oAuth2AccessToken = oauth2AccessTokenRepository
				.findByAuthenticationId(key);
		if (nonNull(oAuth2AccessToken)) {
			accessToken = deserializeAccessToken(oAuth2AccessToken.getToken());
		}
		if (nonNull(accessToken)
				&& !key.equals(authenticationKeyGenerator.extractKey(readAuthentication(accessToken.getValue())))) {
			removeAccessToken(accessToken.getValue());
			// Keep the store consistent (maybe the same user is represented by this
			// authentication but the details have
			// changed)
			storeAccessToken(accessToken, authentication);
		}
		return accessToken;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.security.oauth2.provider.token.TokenStore#
	 * findTokensByClientIdAndUserName(java.lang.String, java.lang.String)
	 */
	@Override
	public Collection<OAuth2AccessToken> findTokensByClientIdAndUserName(String clientId, String userName) {
		List<com.serp.security.domain.OAuth2AccessToken> oAuth2AccessTokens = oauth2AccessTokenRepository
				.findByUsernameAndClientId(userName, clientId);
		return transformToOAuth2AccessTokens(oAuth2AccessTokens);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.security.oauth2.provider.token.TokenStore#
	 * findTokensByClientId(java.lang.String)
	 */
	@Override
	public Collection<OAuth2AccessToken> findTokensByClientId(String clientId) {
		List<com.serp.security.domain.OAuth2AccessToken> oAuth2AccessTokens = oauth2AccessTokenRepository
				.findByClientId(clientId);
		return transformToOAuth2AccessTokens(oAuth2AccessTokens);
	}

	protected String extractTokenKey(String value) {
		if (value == null) {
			return null;
		}
		MessageDigest digest;
		try {
			digest = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			throw new IllegalStateException("MD5 algorithm not available.  Fatal (should be in the JDK).");
		}

		try {
			byte[] bytes = digest.digest(value.getBytes("UTF-8"));
			return String.format("%032x", new BigInteger(1, bytes));
		} catch (UnsupportedEncodingException e) {
			throw new IllegalStateException("UTF-8 encoding not available.  Fatal (should be in the JDK).");
		}
	}

	protected byte[] serializeAccessToken(OAuth2AccessToken token) {
		return SerializationUtils.serialize(token);
	}

	protected byte[] serializeRefreshToken(OAuth2RefreshToken token) {
		return SerializationUtils.serialize(token);
	}

	protected byte[] serializeAuthentication(OAuth2Authentication authentication) {
		return SerializationUtils.serialize(authentication);
	}

	protected OAuth2AccessToken deserializeAccessToken(byte[] token) {
		return SerializationUtils.deserialize(token);
	}

	protected OAuth2RefreshToken deserializeRefreshToken(byte[] token) {
		return SerializationUtils.deserialize(token);
	}

	protected OAuth2Authentication deserializeAuthentication(byte[] authentication) {
		return SerializationUtils.deserialize(authentication);
	}

	private Collection<OAuth2AccessToken> transformToOAuth2AccessTokens(
			final List<com.serp.security.domain.OAuth2AccessToken> oAuth2AccessTokens) {
		return oAuth2AccessTokens.stream().filter(Objects::nonNull)
				.map(token -> SerializationUtils.<OAuth2AccessToken>deserialize(token.getToken()))
				.collect(Collectors.toList());
	}
}
