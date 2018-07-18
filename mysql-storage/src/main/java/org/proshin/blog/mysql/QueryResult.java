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

import com.google.common.collect.ImmutableMap;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Date;
import java.util.Map;
import java.util.Optional;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.jdbc.support.rowset.SqlRowSetMetaData;

public final class QueryResult {

    private final Map<String, Object> columns;

    public QueryResult(final ResultSet resultSet) throws SQLException {
        ResultSetMetaData metaData = resultSet.getMetaData();
        int cols = metaData.getColumnCount();
        final ImmutableMap.Builder<String, Object> builder = ImmutableMap.builder();
        if (resultSet.next()) {
            for (int i = 1; i <= cols; i++) {
                builder.put(metaData.getColumnName(i), resultSet.getObject(i));
            }
        }
        this.columns = builder.build();
    }

    public QueryResult(final SqlRowSet row) {
        final SqlRowSetMetaData metaData = row.getMetaData();
        int cols = metaData.getColumnCount();
        final ImmutableMap.Builder<String, Object> builder = ImmutableMap.builder();
        if (row.next()) {
            for (int i = 1; i <= cols; i++) {
                builder.put(metaData.getColumnName(i), row.getObject(i));
            }
        }
        this.columns = builder.build();
    }

    public Long getLong(final String name) {
        return (Long) this.columns.get(name);
    }

    public Optional<Long> getLongOptional(final String name) {
        return Optional.ofNullable(this.columns.get(name))
            .map(value -> (Long) value);
    }

    public String getString(final String name) {
        return this.columns.get(name).toString();
    }

    public Optional<String> getStringOptional(final String name) {
        return Optional.ofNullable(this.columns.get(name))
            .map(Object::toString);
    }

    public Date getDate(final String name) {
        return null;
    }
}
