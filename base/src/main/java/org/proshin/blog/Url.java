package org.proshin.blog;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import lombok.SneakyThrows;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class Url {

    private final String decodedUrl;

    @SneakyThrows(UnsupportedEncodingException.class)
    public Url(String original) {
        this.decodedUrl = URLDecoder.decode(original, StandardCharsets.UTF_8.displayName());
    }

    @SneakyThrows(UnsupportedEncodingException.class)
    public String encoded() {
        return URLEncoder.encode(decodedUrl, StandardCharsets.UTF_8.displayName());
    }

    public String decoded() {
        return decodedUrl;
    }

    @Override
    public String toString() {
        return encoded();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Url url = (Url) o;

        return new EqualsBuilder()
            .append(decodedUrl, url.decodedUrl)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
            .append(decodedUrl)
            .toHashCode();
    }
}
