package org.proshin.blog.dao;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.proshin.blog.model.AccessToken;
import static org.proshin.blog.model.AccessToken.Provider.INSTAGRAM;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import static org.springframework.test.jdbc.JdbcTestUtils.countRowsInTable;

@SpringBootTest
@RunWith(SpringRunner.class)
public class AccessTokensTest {
    @Autowired
    private AccessTokens accessTokens;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    public void testThatAccessTokenIsCreated() {
        AccessToken accessToken = accessTokens.create(INSTAGRAM, "0123456789");

        assertThat(accessToken.getId(), is(1L));
        assertThat(accessToken.getProvider(), is(INSTAGRAM));
        assertThat(accessToken.getToken(), is("0123456789"));
        assertThat(countRowsInTable(jdbcTemplate, "access_token"), is(1));
    }
}
