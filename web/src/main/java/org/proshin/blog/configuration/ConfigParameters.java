package org.proshin.blog.configuration;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

@Data
@Validated
@Configuration
@ConfigurationProperties("blog")
public class ConfigParameters {
    @Valid
    private Admin admin;
    @Valid
    private BuildInfo buildInfo;
    @Valid
    private ReCaptcha reCaptcha;

    @Data
    public static class Admin {
        @NotNull
        private String username;
        @NotNull
        private String password;
    }

    @Data
    public static class BuildInfo {
        @NotNull
        private String version;
        @NotNull
        private String commit;
        @NotNull
        private String repositoryUrl;
    }

    @Data
    public static class ReCaptcha {
        @NotNull
        private String key;
        @NotNull
        private String secret;
    }
}
