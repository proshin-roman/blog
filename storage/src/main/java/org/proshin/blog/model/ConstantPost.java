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
package org.proshin.blog.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;
import org.proshin.blog.Url;

public final class ConstantPost implements Post {

    private final Long id;
    private final Url url;
    private final String title;
    private final Optional<String> shortcut;
    private final LocalDateTime creationDate;
    private final Optional<LocalDateTime> publicationDate;
    private final boolean published;
    private final Optional<String> content;

    public ConstantPost(final ResultSet rs) throws SQLException {
        this.id = rs.getLong("id");
        this.url = new Url(rs.getString("url"));
        this.title = rs.getString("title");
        this.shortcut = Optional.ofNullable(rs.getString("shortcut"));
        this.creationDate = rs.getTimestamp("creation_dt").toLocalDateTime();
        this.publicationDate =
            Optional.ofNullable(rs.getTimestamp("publication_dt"))
                .map(Timestamp::toLocalDateTime);
        this.published = rs.getBoolean("published");
        this.content = Optional.ofNullable(rs.getString("content"));
    }

    @Override
    public Long id() {
        return this.id;
    }

    @Override
    public Url url() {
        return this.url;
    }

    @Override
    public String title() {
        return this.title;
    }

    @Override
    public Optional<String> shortcut() {
        return this.shortcut;
    }

    @Override
    public LocalDateTime creationDate() {
        return this.creationDate;
    }

    @Override
    public Optional<LocalDateTime> publicationDate() {
        return this.publicationDate;
    }

    @Override
    public boolean published() {
        return this.published;
    }

    @Override
    public Optional<String> content() {
        return this.content;
    }
}
