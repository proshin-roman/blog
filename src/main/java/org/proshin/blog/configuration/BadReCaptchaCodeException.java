package org.proshin.blog.configuration;

import org.springframework.security.core.AuthenticationException;

public class BadReCaptchaCodeException extends AuthenticationException {
    public BadReCaptchaCodeException(String msg, Throwable t) {
        super(msg, t);
    }

    public BadReCaptchaCodeException(String msg) {
        super(msg);
    }
}
