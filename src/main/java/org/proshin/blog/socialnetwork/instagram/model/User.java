package org.proshin.blog.socialnetwork.instagram.model;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.NonNull;
import lombok.Value;

@Value
public class User {
    @NonNull
    Long id;
    @NonNull
    String username;
    @NonNull
    String fullName;
    @NonNull
    String profilePicture;
    String bio;
    String website;
    Counts counts;

    public User(String json) {
        this(new JsonParser().parse(json).getAsJsonObject());
    }

    public User(JsonObject json) {
        id = json.get("id").getAsLong();
        username = json.get("username").getAsString();
        fullName = json.get("full_name").getAsString();
        profilePicture = json.get("profile_picture").getAsString();
        if (json.has("bio")) {
            this.bio = json.get("bio").getAsString();
        } else {
            this.bio = null;
        }
        if (json.has("website") && json.get("website").isJsonPrimitive()) {
            this.website = json.get("website").getAsString();
        } else {
            this.website = null;
        }
        if (json.has("counts")) {
            JsonObject counts = json.get("counts").getAsJsonObject();
            this.counts =
                    new Counts(
                            counts.get("media").getAsLong(),
                            counts.get("follows").getAsLong(),
                            counts.get("followed_by").getAsLong());
        } else {
            counts = new Counts(0L, 0L, 0L);
        }
    }

    @Value
    public static class Counts {
        Long media;
        Long follows;
        Long followedBy;
    }
}
