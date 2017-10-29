package org.proshin.blog.socialnetwork.instagram.oauth.response

import com.github.salomonbrys.kotson.get
import com.github.salomonbrys.kotson.int
import com.github.salomonbrys.kotson.string

class Error(json: String) {

    val errorType: String
    val code: Int
    val errorMessage: String

    init {
        val jsonObject = com.google.gson.JsonParser().parse(json)
        this.errorType = jsonObject["error_type"].string
        this.code = jsonObject["code"].int
        this.errorMessage = jsonObject["error_message"].string
    }

    override fun toString(): String {
        return "Error(errorType='$errorType', code=$code, errorMessage='$errorMessage')"
    }
}