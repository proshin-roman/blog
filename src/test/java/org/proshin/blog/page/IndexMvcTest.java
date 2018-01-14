package org.proshin.blog.page;

import lombok.extern.slf4j.Slf4j;
import static org.hamcrest.Matchers.containsString;
import org.junit.Test;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
public class IndexMvcTest extends AbstractMvcTest {

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
}
