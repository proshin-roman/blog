package org.proshin.blog.model;

import java.util.Date;
import lombok.NonNull;
import lombok.Value;

@Value
public class Post {
    @NonNull
    Long id;
    @NonNull
    String title;
    @NonNull
    Date creationDate;
    @NonNull
    Date publicationDate;
    @NonNull
    boolean published;
    @NonNull
    String content;
}
