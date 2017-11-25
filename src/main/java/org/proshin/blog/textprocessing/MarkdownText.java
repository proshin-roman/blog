package org.proshin.blog.textprocessing;

import lombok.NonNull;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;

public class MarkdownText {
    private final String markdown;

    public MarkdownText(@NonNull String markdown) {
        this.markdown = markdown;
    }

    @NonNull
    public String getAsHtml() {
        Parser parser = Parser.builder().build();
        Node document = parser.parse(markdown);
        HtmlRenderer renderer = HtmlRenderer.builder().build();
        return renderer.render(document);
    }
}
