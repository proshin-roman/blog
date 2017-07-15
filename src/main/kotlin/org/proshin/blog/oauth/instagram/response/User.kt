package org.proshin.blog.oauth.instagram.response

import com.github.salomonbrys.kotson.get
import com.github.salomonbrys.kotson.long
import com.github.salomonbrys.kotson.string
import com.google.gson.JsonElement
import com.google.gson.JsonParser

class User(json: JsonElement) {

    val id: Long = json["id"].long
    val username: String = json["username"].string
    val fullName: String = json["full_name"].string
    val profilePicture: String = json["profile_picture"].string
    val bio: String?
    val website: String?

    init {
        if (json["bio"].isJsonNull) {
            this.bio = null
        } else {
            this.bio = json["bio"].string
        }
        if (json["website"].isJsonNull) {
            this.website = null
        } else {
            this.website = json["website"].string
        }
    }

    constructor(json: String) : this(JsonParser().parse(json))
}