package org.proshin.blog.exception;

import lombok.NonNull;
import org.proshin.blog.Url;

public class PostNotFoundException extends RuntimeException {
    private final Url url;

    public PostNotFoundException(@NonNull Url url) {
        super(String.format("Post with URL='%s' not found", url.decoded()));
        this.url = url;
    }

    public Url url() {
        return url;
    }
}
