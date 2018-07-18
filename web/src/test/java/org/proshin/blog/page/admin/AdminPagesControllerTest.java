package org.proshin.blog.page.admin;

import static org.hamcrest.Matchers.containsString;
import org.junit.Ignore;
import org.junit.Test;
import org.proshin.blog.AbstractIntegrationTest;
import org.springframework.security.test.context.support.WithMockUser;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Ignore
@WithMockUser(roles = "ADMIN")
public class AdminPagesControllerTest extends AbstractIntegrationTest {

    @Test
    public void testGet() throws Exception {
        getMvc()
            .perform(
                get("/admin"))
            .andExpect(status().isOk())
            .andExpect(content().string(containsString("<a href=\"/admin/posts/\"")));
    }
}