package org.proshin.blog.page;

import static org.hamcrest.Matchers.containsString;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class IndexMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testThatIndexPageIsOk() throws Exception {
        mockMvc
            .perform(get("/"))
            .andExpect(status().isOk())
            .andExpect(content().contentType("text/html;charset=UTF-8"))
            .andExpect(content()
                .string(containsString("<a class=\"navbar-brand\" href=\"/\">Блог #тыжпрограммиста</a>")));
    }

    @Test
    public void testThatNonExistingPostReturnsNotFound() throws Exception {
        mockMvc
            .perform(get("/post/404"))
            .andExpect(status().isNotFound())
            .andExpect(content().string(containsString("К сожалению, запрашиваемая страница не найдена")));
    }
}
