package org.proshin.blog;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import org.junit.Test;

public class MarkdownTextTest {
    @Test
    public void testThatMarkdownIsConvertedToHtml() throws Exception {
        assertThat(new MarkdownText("This is *Sparta*").asHtml(),
            is("<p>This is <em>Sparta</em></p>\n")
        );
    }
}