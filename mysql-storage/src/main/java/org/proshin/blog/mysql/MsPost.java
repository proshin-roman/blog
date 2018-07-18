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

import java.time.LocalDateTime;
import java.util.Optional;
import org.proshin.blog.Url;
import org.proshin.blog.model.PersistentPost;
import org.proshin.blog.model.Post;
import org.springframework.jdbc.core.JdbcTemplate;

public final class MsPost implements PersistentPost {

    private final Post orig;
    private final JdbcTemplate jdbcTemplate;

    public MsPost(final Post orig, final JdbcTemplate jdbcTemplate) {
        this.orig = orig;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Long id() {
        return orig.id();
    }

    @Override
    public Url url() {
        return orig.url();
    }

    @Override
    public String title() {
        return orig.title();
    }

    @Override
    public Optional<String> shortcut() {
        return orig.shortcut();
    }

    @Override
    public LocalDateTime creationDate() {
        return orig.creationDate();
    }

    @Override
    public Optional<LocalDateTime> publicationDate() {
        return orig.publicationDate();
    }

    @Override
    public boolean published() {
        return orig.published();
    }

    @Override
    public Optional<String> content() {
        return orig.content();
    }

    @Override
    public MsPost publish() {
        throw new UnsupportedOperationException("This method is not implemented yet");
    }

    @Override
    public MsPost unpublish() {
        throw new UnsupportedOperationException("This method is not implemented yet");
    }

    @Override
    public MsPost persist() {
        throw new UnsupportedOperationException("This method is not implemented yet");
    }

    @Override
    public MsPost update() {
        throw new UnsupportedOperationException("This method is not implemented yet");
    }

    @Override
    public void delete() {
        throw new UnsupportedOperationException("This method is not implemented yet");
    }
}
