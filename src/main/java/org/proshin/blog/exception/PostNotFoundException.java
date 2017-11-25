package org.proshin.blog.exception;

import lombok.NonNull;

public class PostNotFoundException extends RuntimeException {
    public PostNotFoundException(@NonNull String message) {
        super(message);
    }

    public PostNotFoundException(@NonNull String message, @NonNull Exception original) {
        super(message, original);
    }
}
