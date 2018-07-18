package org.proshin.blog.page;

import static java.lang.String.format;
import static org.hamcrest.Matchers.containsString;
import org.junit.Test;
import org.proshin.blog.AbstractIntegrationTest;
import org.proshin.blog.configuration.ConfigParameters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class LoginPageControllerTest extends AbstractIntegrationTest {

    @Autowired
    private ConfigParameters configParameters;

    @Test
    public void testLogin() throws Exception {
        getMvc()
            .perform(
                MockMvcRequestBuilders.get("/login"))
            .andExpect(status().isOk())
            .andExpect(content().string(containsString("class=\"g-recaptcha input-group\"")))
            .andExpect(
                content().string(containsString(
                    format("data-sitekey=\"%s\">", configParameters.getReCaptcha().getKey()))));
    }
}