package org.proshin.blog.oauth.instagram

import org.junit.Test
import org.proshin.blog.oauth.instagram.response.AccessToken
import kotlin.test.expect

class AccessTokenTest {

    @Test
    internal fun testThatAccessTokenIsParsedFromJson() {
        val accessToken = AccessToken("""{
            "access_token": "very secure access token",
            "user": {
                "id": "11111111",
                "username": "custom_username",
                "profile_picture": "https://scontent.cdninstagram.com/t51.2885-19/s150x150/12822369_827960567316089_1427900261_a.jpg",
                "full_name": "Roman Proshin",
                "bio": "Software engineer. Speak Russian, English and Java.",
                "website": null
            }
        }""")

        expect("very secure access token", { accessToken.accessToken })
        expect(11111111, { accessToken.user.id })
        expect("custom_username", { accessToken.user.username })
        expect("https://scontent.cdninstagram.com/t51.2885-19/s150x150/12822369_827960567316089_1427900261_a.jpg", { accessToken.user.profilePicture })
        expect("Roman Proshin", { accessToken.user.fullName })
        expect("Software engineer. Speak Russian, English and Java.", { accessToken.user.bio })
        expect(null, { accessToken.user.website })
    }
}