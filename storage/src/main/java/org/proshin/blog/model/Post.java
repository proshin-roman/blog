package org.proshin.blog.model;

import java.time.LocalDateTime;
import java.util.Optional;
import org.proshin.blog.Url;

public interface Post {

    Long id();

    Url url();

    String title();

    Optional<String> shortcut();

    LocalDateTime creationDate();

    Optional<LocalDateTime> publicationDate();

    boolean published();

    Optional<String> content();
}
