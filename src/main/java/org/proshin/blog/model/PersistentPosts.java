package org.proshin.blog.model;

import java.util.List;
import java.util.Optional;
import lombok.NonNull;
import org.proshin.blog.Url;

public interface PersistentPosts {
    @NonNull
    Optional<PersistentPost> postByUrl(@NonNull Url url);

    @NonNull
    List<PersistentPost> page(int offset, int count, boolean publishedOnly);

    @NonNull
    PersistentPost newPost(Url url, @NonNull String title, @NonNull String content);
}
