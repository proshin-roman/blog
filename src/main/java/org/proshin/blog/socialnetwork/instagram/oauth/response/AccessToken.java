package org.proshin.blog.socialnetwork.instagram.oauth.response;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.Value;
import org.proshin.blog.socialnetwork.instagram.model.User;

@Value
public class AccessToken {

    String accessToken;
    User user;

    public AccessToken(String json) {
        JsonObject jsonObject = new JsonParser().parse(json).getAsJsonObject();
        this.accessToken = jsonObject.get("access_token").getAsString();
        this.user = new User(jsonObject.get("user").getAsJsonObject());
    }
}
