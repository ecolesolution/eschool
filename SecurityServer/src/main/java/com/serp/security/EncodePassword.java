/**
 * 
 */
package com.serp.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @author sanjeet choudhary
 *
 */
public class EncodePassword {
public static void main(String[] args) {
	
	String pwd="admin";
	
	BCryptPasswordEncoder pw=new BCryptPasswordEncoder();
	System.out.println(pw.encode(pwd));
}
}
