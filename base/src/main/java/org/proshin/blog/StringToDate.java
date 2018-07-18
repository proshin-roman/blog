package org.proshin.blog;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class StringToDate {
    private final static DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");

    private final String string;

    public StringToDate(String string) {
        this.string = string;
    }

    public LocalDateTime toLocalDateTime() {
        return LocalDateTime.parse(string, DATE_TIME_FORMATTER);
    }
}
