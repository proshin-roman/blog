package org.proshin.blog.dao;

import java.util.List;
import lombok.NonNull;
import org.proshin.blog.exception.PostNotFoundException;
import org.proshin.blog.model.Post;

public interface Posts {
    @NonNull
    Post selectOne(@NonNull Long id) throws PostNotFoundException;

    @NonNull
    List<Post> selectPage(int offset, int count);
}
