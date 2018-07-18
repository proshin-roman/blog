package org.proshin.blog.page.admin;

import static org.apache.commons.lang3.StringUtils.removeEnd;
import static org.apache.commons.lang3.StringUtils.removeStart;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.startsWith;
import static org.junit.Assert.assertThat;
import org.junit.Ignore;
import org.junit.Test;
import org.proshin.blog.AbstractIntegrationTest;
import org.proshin.blog.StringToDate;
import org.proshin.blog.Url;
import org.proshin.blog.exception.PostNotFoundException;
import org.proshin.blog.model.Post;
import org.proshin.blog.model.Posts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @todo #165 Fix these tests so that they use MySQL implementation of Post/Posts interfaces.
 */
@Ignore
@WithMockUser(roles = "ADMIN")
public class AdminPostsPagesControllerTest extends AbstractIntegrationTest {

    @Autowired
    private Posts posts;

    @Test
    public void testGetPosts() throws Exception {
        Post post = posts.create(new Url("url"), "Non published post", "Any test content");
        getMvc()
            .perform(
                get("/admin/posts"))
            .andExpect(status().isOk())
            .andExpect(content().string(containsString(post.url().encoded())));
    }

    @Test
    public void testCreate() throws Exception {
        String location =
            getMvc()
                .perform(
                    post("/admin/posts/create")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("url", "some-correct-url")
                        .param("title", "New blog post"))
                .andExpect(status().isFound())
                .andExpect(header().string("Location", startsWith("/admin/posts/")))
                .andReturn()
                .getResponse()
                .getHeader("Location");

        String postUrl =
            removeEnd(
                removeStart(
                    location,
                    "/admin/posts/"
                ),
                "/edit"
            );

        posts.postByUrl(new Url(postUrl)).ifPresent(post -> {
            assertThat(post.published(), is(false));
        });
    }

    @Test
    public void testEdit() throws Exception {
        Post post = posts.create(new Url("url"), "Non published post", "Any test content");

        getMvc()
            .perform(
                get("/admin/posts/{url}/edit", post.url()))
            .andExpect(status().isOk())
            .andExpect(content().string(containsString(post.url().encoded())));
    }

    @Test
    public void testPublish() throws Exception {
        Post post = posts.create(new Url("url"), "Non published post", "Any test content");
        assertThat(post.published(), is(false)); // it should be by default

        getMvc()
            .perform(
                get("/admin/posts/{url}/publish", post.url()))
            .andExpect(status().isFound())
            .andExpect(header().string("Location", "/admin/posts/"));

        posts.postByUrl(post.url())
            .ifPresent(persistentPost -> assertThat(persistentPost.published(), is(true)));
    }

    @Test
    public void testUnpublish() throws Exception {
        Post post =
            posts.create(new Url("url"), "Non published post", "Any test content")
                .publish()
                .persist();
        assertThat(post.published(), is(true));

        getMvc()
            .perform(
                get("/admin/posts/{url}/unpublish", post.url()))
            .andExpect(status().isFound())
            .andExpect(header().string("Location", "/admin/posts/"));

        posts.postByUrl(post.url())
            .ifPresent(persistentPost -> assertThat(persistentPost.published(), is(false)));
    }

    @Test(expected = PostNotFoundException.class)
    public void testDelete() throws Exception {
        Post post = posts.create(new Url("url"), "Non published post", "Any test content");

        getMvc()
            .perform(
                get("/admin/posts/{url}/delete", post.url()))
            .andExpect(status().isFound())
            .andExpect(header().string("Location", "/admin/posts/"));

        posts.postByUrl(post.url())
            .orElseThrow(() -> new PostNotFoundException(post.url()));
    }

    @Test
    public void testSave() throws Exception {
        Post post = posts.create(new Url("url"), "Non published post", "Any test content");

        getMvc()
            .perform(
                post("/admin/posts/{url}/save", post.url().encoded())
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .param("url", post.url().decoded())
                    .param("title", "New title")
                    .param("shortcut", "New shortcut")
                    .param("content", "New content")
                    .param("creationDate", "2018-01-09T13:23")
                    .param("publicationDate", "2018-01-07T13:23")
                    .param("published", "true"))
            .andExpect(status().isFound())
            .andExpect(header().string("Location", "/admin/posts"));

        posts.postByUrl(post.url()).ifPresent(changedPost -> {
            assertThat(changedPost.title(), is("New title"));
            assertThat(changedPost.shortcut(), is("New shortcut"));
            assertThat(changedPost.content(), is("New content"));
            assertThat(changedPost.creationDate(),
                is(new StringToDate("2018-01-09T13:23").toLocalDateTime())
            );
            assertThat(changedPost.publicationDate(),
                is(new StringToDate("2018-01-07T13:23").toLocalDateTime())
            );
            assertThat(changedPost.published(), is(true));
        });
    }
}