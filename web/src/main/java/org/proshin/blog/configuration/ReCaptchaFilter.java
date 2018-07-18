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

@Slf4j class ReCaptchaFilter implements Filter {

    private final SimpleUrlAuthenticationFailureHandler failureHandler;
    private final ReCaptchaCodeVerifier codeVerifier;

    ReCaptchaFilter(SimpleUrlAuthenticationFailureHandler failureHandler,
        String reCaptchaSecret, RestTemplate restTemplate
    ) {
        this.failureHandler = failureHandler;
        this.codeVerifier = new ReCaptchaCodeVerifier(restTemplate, reCaptchaSecret);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
        throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
        String reCaptchaResponse = httpServletRequest.getParameter("g-recaptcha-response");

        if (reCaptchaResponse != null && !codeVerifier.isResponseValid(reCaptchaResponse)) {
            log.warn("There was an attempt to log in using incorrect reCaptcha code '{}' from IP='{}'",
                reCaptchaResponse, httpServletRequest.getRemoteAddr()
            );
            failureHandler.onAuthenticationFailure(httpServletRequest, httpServletResponse,
                new BadReCaptchaCodeException("Invalide reCaptcha response: " + reCaptchaResponse)
            );
            return;
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
    }
}
