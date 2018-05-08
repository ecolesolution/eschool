/**
 * 
 */
package com.serp.security.domain;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author sanjeet choudhary
 *
 */
@Document
public class UserAuthority {

	@Id
	private ObjectId id;
	private String username;
	private String authority;
	
	
}
