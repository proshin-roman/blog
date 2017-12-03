package org.proshin.blog.dao;

import java.util.List;
import javax.validation.constraints.NotNull;
import lombok.NonNull;
import org.proshin.blog.exception.PostNotFoundException;
import org.proshin.blog.model.Post;

public interface Posts {
    @NonNull
    Post selectOne(@NonNull Long id) throws PostNotFoundException;

    @NonNull
    List<Post> selectPage(int offset, int count, boolean publishedOnly);

    @NotNull
    Post create();

    void publish(@NotNull Long id) throws PostNotFoundException;

    void unpublish(@NotNull Long id) throws PostNotFoundException;

    void delete(@NotNull Long id) throws PostNotFoundException;
}
