package org.proshin.blog.socialnetwork.instagram.model

import com.github.salomonbrys.kotson.get
import com.github.salomonbrys.kotson.long
import com.github.salomonbrys.kotson.string
import com.google.gson.JsonObject
import com.google.gson.JsonParser

class User(json: JsonObject) {

    val id: Long
    val username: String
    val fullName: String
    val profilePicture: String
    val bio: String?
    val website: String?
    val counts: Counts?

    init {
        id = json["id"].long
        username = json["username"].string
        fullName = json["full_name"].string
        profilePicture = json["profile_picture"].string
        if (json.has("bio")) {
            this.bio = json["bio"].string
        } else {
            this.bio = null
        }
        if (json.has("website")) {
            this.website = json["website"].string
        } else {
            this.website = null
        }
        if (json.has("counts")) {
            counts = Counts(
                    json["counts"]["media"].long,
                    json["counts"]["follows"].long,
                    json["counts"]["followed_by"].long)
        } else {
            counts = Counts(0, 0, 0)
        }
    }

    constructor(json: String) : this(JsonParser().parse(json).asJsonObject)
}

open class Counts(val media: Long, val follows: Long, val followedBy: Long)
