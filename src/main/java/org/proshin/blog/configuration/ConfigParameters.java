package org.proshin.blog.configuration;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import lombok.NonNull;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@ToString
@Component
@Validated
@ConfigurationProperties("blog")
public class ConfigParameters {
    @Valid
    private Admin admin;
    @Valid
    private Dynamo dynamo;

    public Admin getAdmin() {
        return admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }

    public Dynamo getDynamo() {
        return dynamo;
    }

    public void setDynamo(Dynamo dynamo) {
        this.dynamo = dynamo;
    }

    @ToString
    public static class Admin {
        @NotNull
        private String username;
        @NotNull
        private String password;

        @NonNull
        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        @NonNull
        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }

    @ToString
    public static class Dynamo {
        private String accessKey;
        private String secretKey;
        private String endpoint;

        public String getAccessKey() {
            return accessKey;
        }

        public void setAccessKey(String accessKey) {
            this.accessKey = accessKey;
        }

        public String getSecretKey() {
            return secretKey;
        }

        public void setSecretKey(String secretKey) {
            this.secretKey = secretKey;
        }

        public String getEndpoint() {
            return endpoint;
        }

        public void setEndpoint(String endpoint) {
            this.endpoint = endpoint;
        }
    }
}
