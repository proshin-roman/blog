package org.proshin.blog.model;

import java.util.List;
import java.util.Optional;
import org.proshin.blog.Url;

public interface Posts {
    Optional<PersistentPost> postByUrl(Url url);

    List<PersistentPost> page(int offset, int count, boolean publishedOnly);

    PersistentPost create(Url url, String title, String content);
}
