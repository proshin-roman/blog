package org.proshin.blog.page.admin;

import static org.apache.commons.lang3.StringUtils.removeEnd;
import static org.apache.commons.lang3.StringUtils.removeStart;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.startsWith;
import static org.junit.Assert.assertThat;
import org.junit.Test;
import org.proshin.blog.AbstractIntegrationTest;
import org.proshin.blog.StringToDate;
import org.proshin.blog.dynamodb.DynamoPosts;
import org.proshin.blog.exception.PostNotFoundException;
import org.proshin.blog.model.PersistentPost;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WithMockUser(roles = "ADMIN")
public class AdminPostsPagesControllerTest extends AbstractIntegrationTest {

    @Autowired
    private DynamoPosts posts;

    @Test
    public void testGetPosts() throws Exception {
        PersistentPost post = posts.create("Non published post", "Any test content");
        getMvc()
                .perform(
                        get("/admin/posts"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(post.getId())));
    }

    @Test
    public void testCreate() throws Exception {
        String location =
                getMvc()
                        .perform(
                                get("/admin/posts/create"))
                        .andExpect(status().isFound())
                        .andExpect(header().string("Location", startsWith("/admin/posts/")))
                        .andReturn()
                        .getResponse()
                        .getHeader("Location");

        String postId =
                removeEnd(
                        removeStart(
                                location,
                                "/admin/posts/"),
                        "/edit");

        PersistentPost post = posts.selectOne(postId);
        assertThat(post.isPublished(), is(false));
    }

    @Test
    public void testEdit() throws Exception {
        PersistentPost post = posts.create("Non published post", "Any test content");

        getMvc()
                .perform(
                        get("/admin/posts/{id}/edit", post.getId()))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(post.getId())));
    }

    @Test
    public void testPublish() throws Exception {
        PersistentPost post = posts.create("Non published post", "Any test content");
        assertThat(post.isPublished(), is(false)); // it should be by default

        getMvc()
                .perform(
                        get("/admin/posts/{id}/publish", post.getId()))
                .andExpect(status().isFound())
                .andExpect(header().string("Location", "/admin/posts/"));

        assertThat(posts.selectOne(post.getId()).isPublished(), is(true));
    }

    @Test
    public void testUnpublish() throws Exception {
        PersistentPost post =
                posts.create("Non published post", "Any test content")
                        .publish()
                        .save();
        assertThat(post.isPublished(), is(true));

        getMvc()
                .perform(
                        get("/admin/posts/{id}/unpublish", post.getId()))
                .andExpect(status().isFound())
                .andExpect(header().string("Location", "/admin/posts/"));

        assertThat(posts.selectOne(post.getId()).isPublished(), is(false));
    }

    @Test(expected = PostNotFoundException.class)
    public void testDelete() throws Exception {
        PersistentPost post = posts.create("Non published post", "Any test content");

        getMvc()
                .perform(
                        get("/admin/posts/{id}/delete", post.getId()))
                .andExpect(status().isFound())
                .andExpect(header().string("Location", "/admin/posts/"));

        posts.selectOne(post.getId());
    }

    @Test
    public void testSave() throws Exception {
        PersistentPost post = posts.create("Non published post", "Any test content");

        getMvc()
                .perform(
                        post("/admin/posts/{id}/save", post.getId())
                                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                                .param("id", post.getId())
                                .param("title", "New title")
                                .param("content", "New content")
                                .param("creationDate", "2018-01-09T13:23")
                                .param("publicationDate", "2018-01-07T13:23")
                                .param("published", "true"))
                .andExpect(status().isFound())
                .andExpect(header().string("Location", "/admin/posts"));

        PersistentPost changedPost = posts.selectOne(post.getId());
        assertThat(changedPost.getTitle(), is("New title"));
        assertThat(changedPost.getContent(), is("New content"));
        assertThat(changedPost.getCreationDate(),
                is(new StringToDate("2018-01-09T13:23").toLocalDateTime()));
        assertThat(changedPost.getPublicationDate(),
                is(new StringToDate("2018-01-07T13:23").toLocalDateTime()));
        assertThat(changedPost.isPublished(), is(true));
    }
}