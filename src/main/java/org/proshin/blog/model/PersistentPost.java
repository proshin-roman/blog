package org.proshin.blog.model;

import lombok.NonNull;

public interface PersistentPost extends Post {
    @NonNull
    PersistentPost publish();

    @NonNull
    PersistentPost unpublish();

    @NonNull
    PersistentPost save();
}
