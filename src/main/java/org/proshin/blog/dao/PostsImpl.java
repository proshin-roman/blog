package org.proshin.blog.dao;

import static java.lang.String.format;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import static java.sql.Types.BIGINT;
import static java.sql.Types.TIMESTAMP;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import lombok.NonNull;
import org.proshin.blog.exception.PostNotFoundException;
import org.proshin.blog.model.Post;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class PostsImpl implements Posts {

    @NonNull
    private final JdbcTemplate jdbcTemplate;
    @NonNull
    private final PostRowMapper postRowMapper = new PostRowMapper();

    public PostsImpl(@NonNull JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @NonNull
    @Override
    public Post selectOne(@NonNull Long id) throws PostNotFoundException {
        try {
            return jdbcTemplate
                .queryForObject(
                    "select id, title, creation_dt, publication_dt, published, content from post where id = ?",
                    new Object[]{id},
                    new int[]{BIGINT},
                    postRowMapper);
        } catch (Exception e) {
            throw new PostNotFoundException(format("Post %d not found", id), e);
        }
    }

    @NonNull
    @Override
    public List<Post> selectPage(int offset, int count, boolean publishedOnly) {
        return jdbcTemplate.query("" +
                "select id, title, creation_dt, publication_dt, published, content " +
                " from post p " +
                (publishedOnly
                    ? " where p.published is true "
                    : "") +
                " order by publication_dt desc " +
                " limit ?, ?",
            new Object[]{offset, count},
            postRowMapper);
    }

    @Override
    public Post create() {
        return new Post(jdbcTemplate);
    }

    @Override
    public void publish(Long id) throws PostNotFoundException {
        int updatedRows = jdbcTemplate
            .update("update post set published = true, publication_dt = ? where id = ?",
                new Object[]{Timestamp.valueOf(LocalDateTime.now()), id},
                new int[]{TIMESTAMP, BIGINT});
        if (updatedRows == 0) {
            throw new PostNotFoundException(format("Post %d not found", id));
        }
    }

    @Override
    public void unpublish(Long id) throws PostNotFoundException {
        int updatedRows = jdbcTemplate
            .update("update post set publication_dt = null, published = false where id = ?",
                new Object[]{id},
                new int[]{BIGINT});
        if (updatedRows == 0) {
            throw new PostNotFoundException(format("Post %d not found", id));
        }
    }

    @Override
    public void delete(Long id) throws PostNotFoundException {
        int updatedRows = jdbcTemplate
            .update("delete from post where id = ?",
                new Object[]{id},
                new int[]{BIGINT});
        if (updatedRows == 0) {
            throw new PostNotFoundException(format("Post %d not found", id));
        }
    }

    private static class PostRowMapper implements RowMapper<Post> {
        @Override
        public Post mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Post(
                rs.getLong("id"),
                rs.getString("title"),
                rs.getTimestamp("creation_dt").toLocalDateTime(),
                Optional.ofNullable(rs.getTimestamp("publication_dt"))
                    .map(Timestamp::toLocalDateTime)
                    .orElse(null),
                rs.getBoolean("published"),
                rs.getString("content"));
        }
    }
}
