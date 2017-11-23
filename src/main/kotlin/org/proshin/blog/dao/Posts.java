package org.proshin.blog.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import static java.sql.Types.BIGINT;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.proshin.blog.exception.PostNotFoundException;
import org.proshin.blog.model.Post;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

@AllArgsConstructor
public class Posts {

    @NonNull
    private final JdbcTemplate jdbcTemplate;
    @NonNull
    private final PostRowMapper postRowMapper = new PostRowMapper();

    @NonNull
    public Post selectOne(@NonNull Long id) throws PostNotFoundException {
        try {
            return jdbcTemplate
                    .queryForObject(
                            "select id, title, creation_dt, publication_dt, published, content from post where id = ?",
                            new Object[] { id },
                            new int[] { BIGINT },
                            postRowMapper);
        } catch (Exception e) {
            throw new PostNotFoundException(String.format("Post %d not found", id), e);
        }
    }

    @NonNull
    public List<Post> selectPage(int offset, int count) {
        return jdbcTemplate.query("" +
                "select id, title, creation_dt, publication_dt, published, content " +
                " from post p " +
                " order by publication_dt desc " +
                " limit ?, ?",
                new Object[] { offset, count },
                postRowMapper);
    }

    private static class PostRowMapper implements RowMapper<Post> {
        @Override
        public Post mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Post(
                    rs.getLong("id"),
                    rs.getString("title"),
                    rs.getDate("creation_dt"),
                    rs.getDate("publication_dt"),
                    rs.getBoolean("published"),
                    rs.getString("content"));
        }
    }
}
