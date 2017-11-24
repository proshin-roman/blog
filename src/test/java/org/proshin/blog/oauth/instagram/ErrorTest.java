package org.proshin.blog.oauth.instagram;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import org.junit.Test;
import org.proshin.blog.socialnetwork.instagram.oauth.response.Error;

public class ErrorTest {
    @Test
    public void testThatErrorIsParsedFromJson() {
        Error error =
                new Error("" +
                        "{" +
                        "  \"error_type\": \"OAuthException\", " +
                        "  \"code\": 400, " +
                        "  \"error_message\": \"You must provide a client_id\" " +
                        "}");

        assertThat(error.getCode(), is(400));
        assertThat(error.getErrorType(), is("OAuthException"));
        assertThat(error.getErrorMessage(), is("You must provide a client_id"));
    }
}
