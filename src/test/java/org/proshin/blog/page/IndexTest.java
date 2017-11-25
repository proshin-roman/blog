package org.proshin.blog.page;

import static java.util.Collections.singletonList;
import java.util.Date;
import java.util.List;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import org.junit.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.proshin.blog.dao.Posts;
import org.proshin.blog.model.Post;
import org.springframework.web.servlet.ModelAndView;

public class IndexTest {

    @Test
    public void testThatIndexPageShowsFirstArticles() throws Exception {
        List<Post> postsForIndexPage = singletonList(
            new Post(10L, "Test post #10", new Date(), new Date(), true, "content"));

        Posts posts = mock(Posts.class);
        when(posts.selectPage(0, 10))
            .thenReturn(postsForIndexPage);

        Index index = new Index(posts);
        ModelAndView modelAndView = index.getIndex(10);
        assertThat(modelAndView.getViewName(), is("index"));
        assertThat(modelAndView.getModel().get("posts"), is(postsForIndexPage));
    }

    @Test
    public void testThatViewPostShowsTheRequestedPost() throws Exception {
        Posts posts = mock(Posts.class);
        Post requestedPost = new Post(10L, "Test post #10", new Date(), new Date(), true,
            "*markdown* content");
        when(posts.selectOne(10L))
            .thenReturn(requestedPost);

        Index index = new Index(posts);
        ModelAndView modelAndView = index.getPost(10L);
        assertThat(modelAndView.getViewName(), is("post"));
        assertThat(modelAndView.getModel().get("post"), is(requestedPost));
        assertThat(modelAndView.getModel().get("content"), is("<p><em>markdown</em> content</p>\n"));
    }
}
