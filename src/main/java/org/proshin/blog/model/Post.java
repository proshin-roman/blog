package org.proshin.blog.model;

import java.time.LocalDateTime;

public interface Post {
    String getId();

    String getTitle();

    LocalDateTime getCreationDate();

    LocalDateTime getPublicationDate();

    boolean isPublished();

    String getContent();
}
