package com.serp.security.exceptions;

import org.springframework.security.core.AuthenticationException;

/**
 * @author sanjeet choudhary
 *
 */

public class UserNotActivatedException extends AuthenticationException {

    public UserNotActivatedException(String msg, Throwable t) {
        super(msg, t);
    }

    public UserNotActivatedException(String msg) {
        super(msg);
    }
}
