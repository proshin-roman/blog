package org.proshin.blog.configuration;

import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

public class MemoryTokenRepositoryImpl implements PersistentTokenRepository {
    private final Map<String, PersistentRememberMeToken> seriesTokens = new ConcurrentHashMap<>();

    public void createNewToken(PersistentRememberMeToken token) {
        PersistentRememberMeToken previousValue = seriesTokens.putIfAbsent(token.getSeries(), token);
        if (previousValue != null) {
            throw new RuntimeException("Series Id '" + token.getSeries() + "' already exists!");
        }
    }

    public void updateToken(String series, String tokenValue, Date lastUsed) {
        PersistentRememberMeToken token = getTokenForSeries(series);

        PersistentRememberMeToken newToken =
                new PersistentRememberMeToken(
                        token.getUsername(), series, tokenValue, new Date());

        // Store it, overwriting the existing one.
        seriesTokens.put(series, newToken);
    }

    public PersistentRememberMeToken getTokenForSeries(String seriesId) {
        return seriesTokens.get(seriesId);
    }

    public void removeUserTokens(String username) {
        Iterator<String> series = seriesTokens.keySet().iterator();

        while (series.hasNext()) {
            String seriesId = series.next();

            PersistentRememberMeToken token = seriesTokens.get(seriesId);

            if (username.equals(token.getUsername())) {
                series.remove();
            }
        }
    }
}
