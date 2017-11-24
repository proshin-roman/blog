package org.proshin.blog.socialnetwork.instagram.oauth;

import java.util.HashMap;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.proshin.blog.configuration.ConfigParameters;
import org.proshin.blog.dao.AccessTokens;
import static org.proshin.blog.model.AccessToken.Provider.INSTAGRAM;
import org.proshin.blog.socialnetwork.instagram.UsersEndpoint;
import org.proshin.blog.socialnetwork.instagram.model.User;
import org.proshin.blog.socialnetwork.instagram.oauth.response.AccessToken;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Controller
@AllArgsConstructor
public class RedirectEndpoint {
    private static final String ACCESS_TOKEN_ENDPOINT = "https://api.instagram.com/oauth/access_token";

    private final ConfigParameters configParameters;
    private final AccessTokens accessTokens;
    private final RestTemplate restTemplate;

    @RequestMapping(value = "/oauth/instagram/redirect")
    public ResponseEntity<?> processCodeForInstagram(@RequestParam(name = "code", required = true) String code) {
        Map<String, String> request = new HashMap<>();
        request.put("client_id", configParameters.getInstagram().getClientId());
        request.put("client_secret", configParameters.getInstagram().getClientSecret());
        request.put("grant_type", "authorization_code");
        request.put("redirect_uri", configParameters.getInstagram().getRedirectUri());
        request.put("code", code);
        ResponseEntity<String> response = restTemplate.postForEntity(ACCESS_TOKEN_ENDPOINT, request, String.class);
        if (response.getStatusCode() == HttpStatus.OK) {
            org.proshin.blog.model.AccessToken accessToken =
                    accessTokens.create(INSTAGRAM,
                            new AccessToken(response.getBody()).getAccessToken());
            log.info("Access token has been successfully received: {}", accessToken);

            User user =
                    new UsersEndpoint(accessToken.getToken(), restTemplate)
                            .getSelf();
            log.info("User has been received: {}", user);
            return ResponseEntity.ok(user);
        }

        throw new Error(response.getBody());
    }
}