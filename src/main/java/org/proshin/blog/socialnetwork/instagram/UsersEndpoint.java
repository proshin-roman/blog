package org.proshin.blog.socialnetwork.instagram;

import static java.lang.String.format;
import java.util.HashMap;
import java.util.Map;
import lombok.AllArgsConstructor;
import org.proshin.blog.socialnetwork.CommunicationException;
import org.proshin.blog.socialnetwork.instagram.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@AllArgsConstructor
public class UsersEndpoint {
    private final static String ENDPOINT_URL = "https://api.instagram.com/v1/users";
    private final String accessToken;
    private final RestTemplate restTemplate;

    public User getSelf() {
        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("accessToken", accessToken);
        ResponseEntity<String> responseEntity =
                restTemplate.getForEntity(ENDPOINT_URL + "/self", String.class, queryParams);
        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            return new User(responseEntity.getBody());
        }
        throw new CommunicationException(
                format("Couldn't GET %s/self - result was %s",
                        ENDPOINT_URL, responseEntity.getBody()));
    }
}
