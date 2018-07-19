package org.proshin.blog.configuration;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.web.client.RestTemplate;

@Slf4j
public class RecaptchaFilter implements Filter {

    private final SimpleUrlAuthenticationFailureHandler failureHandler;
    private final RecaptchaCodeVerifier codeVerifier;

    RecaptchaFilter(SimpleUrlAuthenticationFailureHandler failureHandler,
        String recaptchaSecret, RestTemplate restTemplate
    ) {
        this.failureHandler = failureHandler;
        this.codeVerifier = new RecaptchaCodeVerifier(restTemplate, recaptchaSecret);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
        throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
        String recaptchaResponse = httpServletRequest.getParameter("g-recaptcha-response");

        if (recaptchaResponse != null && !codeVerifier.isResponseValid(recaptchaResponse)) {
            log.warn("There was an attempt to log in using incorrect recaptcha code '{}' from IP='{}'",
                recaptchaResponse, httpServletRequest.getRemoteAddr()
            );
            failureHandler.onAuthenticationFailure(httpServletRequest, httpServletResponse,
                new BadRecaptchaCodeException("Invalide recaptcha response: " + recaptchaResponse)
            );
            return;
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
    }
}
