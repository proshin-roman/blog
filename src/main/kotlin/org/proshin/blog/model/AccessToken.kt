package org.proshin.blog.model

import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.jdbc.core.JdbcTemplate

class AccessToken {

    val id: Long
    val provider: Provider
    val token: String

    constructor(id: Long, jdbcTemplate: JdbcTemplate) {
        try {
            val map = jdbcTemplate
                .queryForMap("select id, provider, token from access_token where id = ?", id)
            this.id = map["id"] as Long
            this.provider = Provider.valueOf(map["provider"] as String)
            this.token = map["token"] as String
        } catch (e: EmptyResultDataAccessException) {
            throw IllegalStateException("Couldn't select access_token with ID=$id", e)
        }
    }

    constructor(id: Long, provider: Provider, token: String) {
        this.id = id
        this.token = token
        this.provider = provider
    }

    enum class Provider {
        INSTAGRAM
    }

    override fun toString(): String = "AccessToken(id=$id, provider=$provider, token='$token')"
}