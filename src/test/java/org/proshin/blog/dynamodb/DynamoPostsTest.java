package org.proshin.blog.dynamodb;

import static java.time.LocalDateTime.now;
import static java.util.Collections.singletonList;
import java.util.List;
import java.util.stream.Collectors;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import org.junit.Test;
import org.proshin.blog.AbstractIntegrationTest;
import org.proshin.blog.exception.PostNotFoundException;
import org.proshin.blog.model.PersistentPost;
import org.proshin.blog.model.Post;
import org.springframework.beans.factory.annotation.Autowired;

public class DynamoPostsTest extends AbstractIntegrationTest {

    @Autowired
    private DynamoPosts posts;

    @Test
    public void testSelectOne_findExistingPost() {
        PersistentPost post =
                new DynamoPost(posts.getTable(), "Published post", now(), now(), true, "Some content")
                        .save();
        assertThat(posts.selectOne(post.getId()), is(post));
    }

    @Test(expected = PostNotFoundException.class)
    public void testSelectOne_findNonExistingPost() {
        posts.selectOne("ID of non-existing post");
    }

    @Test
    public void testThatPublishedAreFilteredOut() {
        String publishedId =
                new DynamoPost(posts.getTable(), "Published post", now(), now(), true, "Some content")
                        .save()
                        .getId();
        String nonPublishedId =
                new DynamoPost(posts.getTable(), "NOT published post", now(), now(), false, "Some content")
                        .save()
                        .getId();

        List<String> allPostsPage =
                posts.selectPage(0, 1, false)
                        .stream()
                        .map(Post::getId)
                        .collect(Collectors.toList());
        assertThat(allPostsPage, hasItems(publishedId, nonPublishedId));

        List<String> publishedPage =
                posts.selectPage(0, 1, true)
                        .stream()
                        .map(Post::getId)
                        .collect(Collectors.toList());
        assertThat(publishedPage, is(singletonList(publishedId)));
    }

    @Test
    public void testCreate() {
        String createdPostId = posts.create("New post", "It's a draft").getId();
        PersistentPost post = posts.selectOne(createdPostId);
        assertThat(post.getTitle(), is("New post"));
        assertThat(post.getContent(), is("It's a draft"));
        assertThat(post.getCreationDate(), notNullValue());
        assertThat(post.getPublicationDate(), notNullValue());
    }

    @Test(expected = PostNotFoundException.class)
    public void testDelete() {
        PersistentPost post =
                new DynamoPost(posts.getTable(), "Published post", now(), now(), true, "Some content")
                        .save();

        posts.delete(post.getId());

        posts.selectOne(post.getId());
    }
}
