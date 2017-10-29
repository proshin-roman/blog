package org.proshin.blog.socialnetwork.instagram

import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.FuelError
import com.github.kittinunf.result.Result
import org.proshin.blog.socialnetwork.CommunicationException
import org.proshin.blog.socialnetwork.instagram.model.User

class UsersEndpoint(val accessToken: String) {

    val ENDPOINT_URL = "https://api.instagram.com/v1/users"

    fun getSelf(): User {
        val (_, _, result) = Fuel
                .get(ENDPOINT_URL + "/self", listOf("accessToken" to accessToken))
                .responseString()

        when (result) {
            is Result.Failure<String, FuelError> -> {
                throw CommunicationException("Couldn't GET $ENDPOINT_URL/self - result was $result")
            }
            is Result.Success<String, FuelError> -> {
                return User(result.value)
            }
        }
    }

    fun getByUserId(userId: String) {}

    fun getRecentMedia() {}

    fun getRecentMediaByUserId(userId: String) {}

    fun getLiked() {}

    fun getSearch() {}
}