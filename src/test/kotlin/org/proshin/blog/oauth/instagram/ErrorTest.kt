package org.proshin.blog.oauth.instagram

import org.junit.Test
import org.proshin.blog.socialnetwork.instagram.oauth.response.Error
import kotlin.test.expect

class ErrorTest {
    @Test
    fun testThatErrorIsParsedFromJson() {
        val error = Error("""{
                "error_type": "OAuthException",
                "code": 400,
                "error_message": "You must provide a client_id"
            }""")

        expect(400, { error.code })
        expect("OAuthException", { error.errorType })
        expect("You must provide a client_id", { error.errorMessage })
    }
}