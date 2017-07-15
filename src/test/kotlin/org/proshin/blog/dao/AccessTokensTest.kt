package org.proshin.blog.dao

import org.junit.Test
import org.junit.runner.RunWith
import org.proshin.blog.model.AccessToken.Provider.INSTAGRAM
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.jdbc.JdbcTestUtils
import kotlin.test.expect

@SpringBootTest
@RunWith(SpringRunner::class)
class AccessTokensTest {

    @Autowired
    lateinit var accessTokens: AccessTokens
    @Autowired
    lateinit var jdbcTemplate: JdbcTemplate

    @Test
    fun testThatAccessTokenIsCreated() {
        val accessToken = accessTokens.create(INSTAGRAM, "0123456789")

        expect(1, { accessToken.id })
        expect(INSTAGRAM, { accessToken.provider })
        expect("0123456789", { accessToken.token })

        expect(1, { JdbcTestUtils.countRowsInTable(jdbcTemplate, "access_token") })
    }
}

