package org.proshin.blog.dao

import org.proshin.blog.model.AccessToken
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.support.GeneratedKeyHolder
import org.springframework.stereotype.Repository

@Repository
open class AccessTokens(val jdbcTemplate: JdbcTemplate) {

    fun create(provider: AccessToken.Provider, token: String): AccessToken {
        val keyHolder = GeneratedKeyHolder()
        jdbcTemplate.update(
                { connection ->
                    val ps = connection.prepareStatement(
                            "insert into access_token (provider, token) values (?, ?)", arrayOf("id"))
                    ps.setString(1, provider.name)
                    ps.setString(2, token)
                    ps
                },
                keyHolder)
        return AccessToken(keyHolder.key.toLong(), jdbcTemplate)
    }
}