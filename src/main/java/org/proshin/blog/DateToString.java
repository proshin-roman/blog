package org.proshin.blog;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.NonNull;

public class DateToString {
    private final static DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");

    private final LocalDateTime localDateTime;

    public DateToString(@NonNull LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

    @NonNull
    @Override
    public String toString() {
        return localDateTime.format(DATE_TIME_FORMATTER);
    }
}
