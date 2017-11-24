package org.proshin.blog.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.proshin.blog.model.AccessToken;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class AccessTokens {

    private final JdbcTemplate jdbcTemplate;
    private final AccessTokenRowMapper rowMapper = new AccessTokenRowMapper();

    public AccessToken create(AccessToken.Provider provider, String token) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(
                connection -> {
                    PreparedStatement ps =
                            connection.prepareStatement(
                                    "insert into access_token (provider, token) values (?, ?)",
                                    new String[] { "id" });
                    ps.setString(1, provider.name());
                    ps.setString(2, token);
                    return ps;
                },
                keyHolder);
        return selectOne(keyHolder.getKey().longValue());
    }

    public AccessToken selectOne(@NonNull Long id) {
        try {
            return jdbcTemplate
                    .queryForObject("select id, provider, token from access_token where id = ?",
                            new Object[] { id },
                            new int[] { Types.BIGINT },
                            rowMapper);
        } catch (EmptyResultDataAccessException e) {
            throw new IllegalStateException(String.format("Couldn't select access_token with ID=%d", id), e);
        }
    }

    private static class AccessTokenRowMapper implements RowMapper<AccessToken> {
        @Override
        public AccessToken mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new AccessToken(
                    rs.getLong("id"),
                    AccessToken.Provider.valueOf(rs.getString("provider")),
                    rs.getString("token"));
        }
    }
}
