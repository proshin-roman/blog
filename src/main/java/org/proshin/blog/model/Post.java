package org.proshin.blog.model;

import java.sql.PreparedStatement;
import java.sql.Timestamp;
import static java.sql.Types.BIGINT;
import static java.sql.Types.BOOLEAN;
import static java.sql.Types.LONGVARCHAR;
import static java.sql.Types.TIMESTAMP;
import static java.sql.Types.VARCHAR;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.NonNull;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

@Data
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

    public Post() {
    }

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
                                    new String[] { "id" });
                    ps.setString(1, title);
                    ps.setTimestamp(2, Timestamp.valueOf(creationDate));
                    ps.setNull(3, TIMESTAMP);
                    ps.setBoolean(4, published);
                    ps.setString(5, content);
                    return ps;
                },
                keyHolder);

        this.id = keyHolder.getKey().longValue();
    }

    public Post(@NonNull Long id, @NonNull String title, @NonNull LocalDateTime creationDate,
            LocalDateTime publicationDate, boolean published, @NonNull String content) {
        this.id = id;
        this.title = title;
        this.creationDate = creationDate;
        this.publicationDate = publicationDate;
        this.published = published;
        this.content = content;
    }

    public void save(@NonNull JdbcTemplate jdbcTemplate) {
        jdbcTemplate
                .update("" +
                        "update post " +
                        "  set title = ?, creation_dt = ?, publication_dt = ?, published = ?, content = ? " +
                        "  where id = ?",
                        new Object[] { title, creationDate, publicationDate, published, content, id },
                        new int[] { VARCHAR, TIMESTAMP, TIMESTAMP, BOOLEAN, LONGVARCHAR, BIGINT });
    }
}
