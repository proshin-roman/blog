package org.proshin.blog.configuration;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.client.RestTemplate;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final ConfigParameters configParameters;
    private final RestTemplate restTemplate;

    @Autowired
    public WebSecurityConfig(@NonNull ConfigParameters configParameters, @NonNull RestTemplate restTemplate) {
        this.configParameters = configParameters;
        this.restTemplate = restTemplate;
    }

    public void configure(HttpSecurity http) throws Exception {
        SimpleUrlAuthenticationFailureHandler failureHandler =
                new SimpleUrlAuthenticationFailureHandler("/login");
        // @formatter:off
        http
            .csrf()
                .disable()
            .authorizeRequests()
                .antMatchers("/admin/**")
                    .hasRole("ADMIN")
            .and()
            .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/admin")
                .failureHandler(failureHandler)
                .permitAll()
            .and()
            .logout()
                .permitAll()
            .and()
            .addFilterBefore(
                    new ReCaptchaFilter(failureHandler, configParameters.getReCaptcha().getSecret(), restTemplate),
                    UsernamePasswordAuthenticationFilter.class);
        // @formatter:on
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth, ConfigParameters configParameters) throws Exception {
        auth
                .inMemoryAuthentication()
                .withUser(configParameters.getAdmin().getUsername())
                .password(configParameters.getAdmin().getPassword())
                .roles("ADMIN");
    }
}
