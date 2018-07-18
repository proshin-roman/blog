/*
 * Copyright 2018 Roman Proshin
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.proshin.blog.mysql;

import java.sql.Types;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.proshin.blog.Url;
import org.proshin.blog.model.ConstantPost;
import org.proshin.blog.model.PersistentPost;
import org.proshin.blog.model.Posts;
import org.springframework.jdbc.core.JdbcTemplate;

@RequiredArgsConstructor
public final class MsPosts implements Posts {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public Optional<PersistentPost> postByUrl(Url url) {
        return Optional.ofNullable(
            jdbcTemplate.queryForObject(
                "select * from post where url = ?",
                new Object[]{ url },
                new int[]{ Types.VARCHAR },
                (rs, index) -> new MsPost(new ConstantPost(rs), jdbcTemplate)
            )
        );
    }

    @Override
    public List<PersistentPost> page(int offset, int count, boolean publishedOnly) {
        return jdbcTemplate.query(
            "select * from post where published = ? limit ? offset ?",
            new Object[]{ publishedOnly, count, offset },
            new int[]{ Types.BOOLEAN, Types.BIGINT, Types.BIGINT },
            (rs, index) -> new MsPost(new ConstantPost(rs), jdbcTemplate)
        );
    }

    @Override
    public PersistentPost create(Url url, String title, String content) {
        throw new UnsupportedOperationException("This method is not implemented yet");
    }
}
