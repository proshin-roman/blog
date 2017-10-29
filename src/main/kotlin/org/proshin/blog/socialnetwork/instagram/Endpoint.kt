package org.proshin.blog.socialnetwork.instagram

enum class Endpoint(val url: String) {
    ACCESS_TOKEN("https://api.instagram.com/oauth/access_token"),
    USERS("https://api.instagram.com/v1/users/self/"),
    GET_RECENT_MEDIA_ENDPOINT("https://api.instagram.com/v1/users/self/media/recent/")
}