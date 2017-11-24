package org.proshin.blog.socialnetwork.instagram.oauth.response;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.Value;

@Value
public class Error {

    String errorType;
    Integer code;
    String errorMessage;

    public Error(String json) {
        JsonObject jsonObject = new JsonParser().parse(json).getAsJsonObject();
        this.errorType = jsonObject.get("error_type").getAsString();
        this.code = jsonObject.get("code").getAsInt();
        this.errorMessage = jsonObject.get("error_message").getAsString();
    }
}
