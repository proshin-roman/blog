package org.proshin.blog.configuration

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component
import org.springframework.validation.annotation.Validated
import javax.validation.Valid
import javax.validation.constraints.NotNull

@Component
@Validated
@ConfigurationProperties("blog")
open class ConfigParameters {
    @Valid
    open var admin: Admin = Admin();
    @Valid
    open var instagram: Instagram = Instagram();

    open class Admin {
        @NotNull
        open var username: String? = null;
        @NotNull
        open var password: String? = null;
    }

    open class Instagram {
        open var clientId: String? = null;
        open var clientSecret: String? = null;
        open var redirectUri: String? = null;
    }
}