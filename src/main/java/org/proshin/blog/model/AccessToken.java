package org.proshin.blog.model;

import lombok.Value;

@Value
public class AccessToken {
    Long id;
    Provider provider;
    String token;

    public AccessToken(Long id, Provider provider, String token) {
        this.id = id;
        this.token = token;
        this.provider = provider;
    }

    public enum Provider {
        INSTAGRAM
    }
}
