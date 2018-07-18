package org.proshin.blog.configuration;

import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import static org.apache.commons.lang3.StringUtils.isBlank;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import static org.springframework.http.HttpMethod.POST;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Slf4j
public class ReCaptchaCodeVerifier {

    private static final String RECAPTCHA_VALIDATION_URL_TEMPLATE =
        "https://www.google.com/recaptcha/api/siteverify";

    private final RestTemplate restTemplate;
    private final String secret;

    public ReCaptchaCodeVerifier(RestTemplate restTemplate, String secret) {
        this.restTemplate = restTemplate;
        this.secret = secret;
    }

    public boolean isResponseValid(String response) {
        if (isBlank(response)) {
            return false;
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
        parameters.add("secret", secret);
        parameters.add("response", response);

        Map reCaptchaResponse =
            restTemplate
                .exchange(
                    RECAPTCHA_VALIDATION_URL_TEMPLATE,
                    POST,
                    new HttpEntity<>(parameters, headers),
                    Map.class
                )
                .getBody();

        if (reCaptchaResponse.containsKey("success") && reCaptchaResponse.get("success").equals(true)) {
            return true;
        }

        if (reCaptchaResponse.containsKey("error-codes")) {
            log.warn("Failed attempt to verify reCaptcha response. Error codes are: {}",
                reCaptchaResponse.get("error-codes")
            );
        }
        return false;
    }
}
