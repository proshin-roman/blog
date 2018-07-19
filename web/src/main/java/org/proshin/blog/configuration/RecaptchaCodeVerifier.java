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
public class RecaptchaCodeVerifier {

    private static final String RECAPTCHA_VALIDATION_URL_TEMPLATE =
        "https://www.google.com/recaptcha/api/siteverify";

    private final RestTemplate restTemplate;
    private final String secret;

    public RecaptchaCodeVerifier(RestTemplate restTemplate, String secret) {
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

        Map recaptchaResponse =
            restTemplate
                .exchange(
                    RECAPTCHA_VALIDATION_URL_TEMPLATE,
                    POST,
                    new HttpEntity<>(parameters, headers),
                    Map.class
                )
                .getBody();

        if (recaptchaResponse.containsKey("success") && recaptchaResponse.get("success").equals(true)) {
            return true;
        }

        if (recaptchaResponse.containsKey("error-codes")) {
            log.warn("Failed attempt to verify recaptcha response. Error codes are: {}",
                recaptchaResponse.get("error-codes")
            );
        }
        return false;
    }
}
