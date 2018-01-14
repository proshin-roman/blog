package org.proshin.blog.model;

import java.util.List;
import lombok.NonNull;
import org.proshin.blog.exception.PostNotFoundException;

public interface Posts {
    @NonNull
    PersistentPost selectOne(@NonNull String id) throws PostNotFoundException;

    @NonNull
    List<PersistentPost> selectPage(int offset, int count, boolean publishedOnly);

    @NonNull
    PersistentPost create();

    void delete(@NonNull String id);
}
