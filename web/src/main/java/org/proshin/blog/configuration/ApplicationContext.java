package org.proshin.blog.configuration;

import lombok.extern.log4j.Log4j;
import org.proshin.blog.model.Posts;
import org.proshin.blog.mysql.MsPosts;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.client.RestTemplate;

@Log4j
@Configuration
public class ApplicationContext {

    public ApplicationContext() {
        log.info("A new instance of application context has been instantiated");
    }

    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }

    @Bean
    public Posts posts(final JdbcTemplate jdbcTemplate) {
        return new MsPosts(jdbcTemplate);
    }
}
