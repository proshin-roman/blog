package org.proshin.blog.socialnetwork.instagram.oauth.response

import com.github.salomonbrys.kotson.get
import com.github.salomonbrys.kotson.obj
import com.github.salomonbrys.kotson.string
import org.proshin.blog.socialnetwork.instagram.model.User

class AccessToken(json: String) {

    val accessToken: String
    val user: User

    init {
        val jsonElement = com.google.gson.JsonParser().parse(json)
        this.accessToken = jsonElement["access_token"].string
        this.user = User(jsonElement["user"].obj)
    }
}