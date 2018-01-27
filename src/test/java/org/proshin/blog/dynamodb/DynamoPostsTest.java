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
import org.proshin.blog.Url;
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
                new DynamoPost(posts.getTable(), new Url("url"), "Published post", now(), now(), true, "Some content")
                        .persist();
        assertThat(posts.postByUrl(post.url()).orElseThrow(() -> new PostNotFoundException(new Url("url"))), is(post));
    }

    @Test(expected = PostNotFoundException.class)
    public void testSelectOne_findNonExistingPost() {
        posts.postByUrl(new Url("URL of non-existing post"))
                .orElseThrow(() -> new PostNotFoundException(new Url("URL of non-existing post")));
    }

    @Test
    public void testThatPublishedAreFilteredOut() {
        Url publishedUrl =
                new DynamoPost(posts.getTable(), new Url("url-1"), "Published post", now(), now(), true, "Some content")
                        .persist()
                        .url();
        Url nonPublishedUrl =
                new DynamoPost(posts.getTable(), new Url("url-2"), "NOT published post", now(), now(), false,
                        "Some content")
                                .persist()
                                .url();

        List<Url> allPostsPage =
                posts.page(0, 1, false)
                        .stream()
                        .map(Post::url)
                        .collect(Collectors.toList());
        assertThat(allPostsPage, hasItems(publishedUrl, nonPublishedUrl));

        List<Url> publishedPage =
                posts.page(0, 1, true)
                        .stream()
                        .map(Post::url)
                        .collect(Collectors.toList());
        assertThat(publishedPage, is(singletonList(publishedUrl)));
    }

    @Test
    public void testCreate() {
        Url createdPostUrl = posts.newPost(new Url("url"), "New post", "It's a draft").url();
        posts.postByUrl(createdPostUrl)
                .ifPresent(post -> {
                    assertThat(post.title(), is("New post"));
                    assertThat(post.content(), is("It's a draft"));
                    assertThat(post.creationDate(), notNullValue());
                    assertThat(post.publicationDate(), notNullValue());
                });
    }

    @Test(expected = PostNotFoundException.class)
    public void testDelete() {
        PersistentPost post =
                new DynamoPost(posts.getTable(), new Url("url"), "Published post", now(), now(), true, "Some content")
                        .persist();

        post.delete();

        posts.postByUrl(post.url())
                .orElseThrow(() -> new PostNotFoundException(post.url()));
    }
}
