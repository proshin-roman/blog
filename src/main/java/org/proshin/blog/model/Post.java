package org.proshin.blog.model;

import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.sql.Types;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

@Value
@AllArgsConstructor
public class Post {
    Long id;
    @NonNull
    String title;
    @NonNull
    LocalDateTime creationDate;
    LocalDateTime publicationDate;
    boolean published;
    @NonNull
    String content;

    public Post(JdbcTemplate jdbcTemplate) {
        this.title = "Черновик статьи";
        this.creationDate = LocalDateTime.now();
        this.published = false;
        this.content = "# Это черновик статьи";
        this.publicationDate = null;
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(
            connection -> {
                PreparedStatement ps =
                    connection.prepareStatement("" +
                            "insert into post (title, creation_dt, publication_dt, published, content) " +
                            "  values (?, ?, ?, ?, ?)",
                        new String[]{"id"});
                ps.setString(1, title);
                ps.setTimestamp(2, Timestamp.valueOf(creationDate));
                ps.setNull(3, Types.TIMESTAMP);
                ps.setBoolean(4, published);
                ps.setString(5, content);
                return ps;
            },
            keyHolder);

        this.id = keyHolder.getKey().longValue();
    }
}
