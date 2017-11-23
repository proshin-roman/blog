package org.proshin.blog.exception;

public class PostNotFoundException extends RuntimeException {
    public PostNotFoundException(String message, Exception original) {
        super(message, original);
    }
}
