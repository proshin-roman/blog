package org.proshin.blog.page;

import com.amazonaws.services.dynamodbv2.document.Table;
import static java.time.LocalDateTime.now;
import static java.util.Collections.singletonList;
import java.util.List;
import java.util.Optional;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import org.junit.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.proshin.blog.Url;
import org.proshin.blog.dynamodb.DynamoPost;
import org.proshin.blog.dynamodb.DynamoPosts;
import org.proshin.blog.model.PersistentPost;
import org.springframework.web.servlet.ModelAndView;

public class GuestPagesControllerTest {

    private final DynamoPosts posts = mock(DynamoPosts.class);
    private final Table postsTable = mock(Table.class);

    @Test
    public void testThatIndexPageShowsFirstArticles() throws Exception {
        List<PersistentPost> postsForIndexPage =
                singletonList(
                        new DynamoPost(postsTable, new Url("10"), "Test post #10", now(), now(), true, "content"));

        when(posts.page(0, 10, true))
                .thenReturn(postsForIndexPage);

        GuestPagesController guestPagesController = new GuestPagesController(posts);
        ModelAndView modelAndView = guestPagesController.getIndex(10);
        assertThat(modelAndView.getViewName(), is("index"));
        assertThat(modelAndView.getModel().get("posts"), is(postsForIndexPage));
    }

    @Test
    public void testThatViewPostShowsTheRequestedPost() throws Exception {
        PersistentPost requestedPost =
                new DynamoPost(postsTable, new Url("10"), "Test post #10", now(), now(), true,
                        "*markdown* content");
        when(posts.postByUrl(new Url("10")))
                .thenReturn(Optional.of(requestedPost));

        GuestPagesController guestPagesController = new GuestPagesController(posts);
        ModelAndView modelAndView = guestPagesController.getPost(new Url("10"));
        assertThat(modelAndView.getViewName(), is("post"));
        assertThat(modelAndView.getModel().get("post"), is(requestedPost));
        assertThat(modelAndView.getModel().get("content"), is("<p><em>markdown</em> content</p>\n"));
    }
}
