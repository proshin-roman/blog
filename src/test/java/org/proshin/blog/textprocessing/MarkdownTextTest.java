package org.proshin.blog.textprocessing;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import org.junit.Test;

public class MarkdownTextTest {
    @Test
    public void testThatMarkdownIsConvertedToHtml() throws Exception {
        assertThat(new MarkdownText("This is *Sparta*").getAsHtml(),
            is("<p>This is <em>Sparta</em></p>\n"));
    }
}