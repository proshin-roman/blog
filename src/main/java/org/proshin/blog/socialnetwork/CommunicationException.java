package org.proshin.blog.socialnetwork;

import lombok.NonNull;

public class CommunicationException extends RuntimeException {
    public CommunicationException(@NonNull String message) {
        super(message);
    }
}
