package org.proshin.blog.page;

import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import static java.time.LocalDateTime.now;
import lombok.extern.slf4j.Slf4j;
import static org.hamcrest.Matchers.containsString;
import org.junit.Test;
import org.proshin.blog.AbstractIntegrationTest;
import org.proshin.blog.dynamodb.DynamoPost;
import org.proshin.blog.dynamodb.DynamoPosts;
import org.proshin.blog.model.PersistentPost;
import org.springframework.beans.factory.annotation.Autowired;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
public class GuestPagesControllerIntegrationTest extends AbstractIntegrationTest {

    @Autowired
    private DynamoDB dynamoDB;

    @Test
    public void testThatIndexPageIsOk() throws Exception {
        getMvc()
                .perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(content()
                        .string(containsString("<a class=\"navbar-brand\" href=\"/\">Блог #тыжпрограммиста</a>")));
    }

    @Test
    public void testThatNonExistingPostReturnsNotFound() throws Exception {
        getMvc()
                .perform(get("/post/404"))
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString("К сожалению, запрашиваемая страница не найдена")));
    }

    @Test
    public void testThatNonPublishedPostIsNotAvailableForGuests() throws Exception {
        PersistentPost post =
                new DynamoPost(dynamoDB.getTable(DynamoPosts.TABLE_NAME),
                        "Some test post", now(), now(), false, "Just a piece of content")
                                .save();
        // when it's not published
        post.unpublish().save();

        getMvc()
                .perform(get("/post/" + post.getId()))
                .andExpect(status().isNotFound());

        // when it's published
        post.publish().save();

        getMvc()
                .perform(get("/post/" + post.getId()))
                .andExpect(status().isOk());
    }
}
