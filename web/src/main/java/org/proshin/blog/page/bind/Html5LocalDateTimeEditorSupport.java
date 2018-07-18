package org.proshin.blog.page.bind;

import java.beans.PropertyEditorSupport;
import java.time.LocalDateTime;
import java.util.Optional;
import org.proshin.blog.DateToString;
import org.proshin.blog.StringToDate;

public class Html5LocalDateTimeEditorSupport extends PropertyEditorSupport {
    @Override
    public String getAsText() {
        return Optional.ofNullable(getValue())
            .map(value -> new DateToString((LocalDateTime) getValue()).toString())
            .orElse(null);
    }

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        Optional.ofNullable(text)
            .ifPresent(nonEmptyText -> {
                setValue(new StringToDate(nonEmptyText).toLocalDateTime());
            });
    }
}
