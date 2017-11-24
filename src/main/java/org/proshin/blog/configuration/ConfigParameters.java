package org.proshin.blog.configuration;

import javax.validation.Valid;
import lombok.NonNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Component
@Validated
@ConfigurationProperties("blog")
public class ConfigParameters {
    @Valid
    private Admin admin;
    @Valid
    private Instagram instagram;

    public Admin getAdmin() {
        return admin;
    }

    public ConfigParameters setAdmin(Admin admin) {
        this.admin = admin;
        return this;
    }

    public Instagram getInstagram() {
        return instagram;
    }

    public ConfigParameters setInstagram(Instagram instagram) {
        this.instagram = instagram;
        return this;
    }

    public static class Admin {
        private String username;
        private String password;

        @NonNull
        public String getUsername() {
            return username;
        }

        public Admin setUsername(String username) {
            this.username = username;
            return this;
        }

        @NonNull
        public String getPassword() {
            return password;
        }

        public Admin setPassword(String password) {
            this.password = password;
            return this;
        }
    }

    public static class Instagram {
        private String clientId;
        private String clientSecret;
        private String redirectUri;

        public String getClientId() {
            return clientId;
        }

        public Instagram setClientId(String clientId) {
            this.clientId = clientId;
            return this;
        }

        public String getClientSecret() {
            return clientSecret;
        }

        public Instagram setClientSecret(String clientSecret) {
            this.clientSecret = clientSecret;
            return this;
        }

        public String getRedirectUri() {
            return redirectUri;
        }

        public Instagram setRedirectUri(String redirectUri) {
            this.redirectUri = redirectUri;
            return this;
        }
    }
}
