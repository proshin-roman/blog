package org.proshin.blog;

import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;

public class MarkdownText {
    private final String markdown;

    public MarkdownText(String markdown) {
        this.markdown = markdown;
    }

    public String asHtml() {
        Parser parser = Parser.builder().build();
        Node document = parser.parse(markdown);
        HtmlRenderer renderer = HtmlRenderer.builder().build();
        return renderer.render(document);
    }
}
