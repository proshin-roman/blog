package org.proshin.blog.oauth.instagram;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;
import org.junit.Test;
import org.proshin.blog.socialnetwork.instagram.oauth.response.AccessToken;

public class AccessTokenTest {
    @Test
    public void testThatAccessTokenIsParsedFromJson() {
        AccessToken accessToken =
                new AccessToken("" +
                        "{" +
                        "  \"access_token\": \"very secure access token\"," +
                        "  \"user\": {" +
                        "    \"id\": \"11111111\", " +
                        "    \"username\": \"custom_username\", " +
                        "    \"profile_picture\": \"https://scontent.cdninstagram.com/t51.2885-19/s150x150/12822369_827960567316089_1427900261_a.jpg\", "
                        +
                        "    \"full_name\": \"Roman Proshin\", " +
                        "    \"bio\": \"Software engineer. Speak Russian, English and Java.\", " +
                        "    \"website\": null" +
                        "  } " +
                        "}");

        assertThat(accessToken.getAccessToken(), is("very secure access token"));
        assertThat(accessToken.getUser().getId(), is(11111111L));
        assertThat(accessToken.getUser().getUsername(), is("custom_username"));
        assertThat(accessToken.getUser().getProfilePicture(),
                is("https://scontent.cdninstagram.com/t51.2885-19/s150x150/12822369_827960567316089_1427900261_a.jpg"));
        assertThat(accessToken.getUser().getFullName(), is("Roman Proshin"));
        assertThat(accessToken.getUser().getBio(), is("Software engineer. Speak Russian, English and Java."));
        assertThat(accessToken.getUser().getWebsite(), nullValue());
    }
}
