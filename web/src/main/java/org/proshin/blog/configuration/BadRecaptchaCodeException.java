package org.proshin.blog.configuration;

import org.springframework.security.core.AuthenticationException;

public class BadRecaptchaCodeException extends AuthenticationException {
    public BadRecaptchaCodeException(String msg, Throwable t) {
        super(msg, t);
    }

    public BadRecaptchaCodeException(String msg) {
        super(msg);
    }
}
