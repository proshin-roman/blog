package org.proshin.blog.model;

import java.time.LocalDateTime;
import org.proshin.blog.Url;

public interface Post {
    Url url();

    String title();

    String shortcut();

    LocalDateTime creationDate();

    LocalDateTime publicationDate();

    boolean published();

    String content();
}
