package org.proshin.blog.page;

import lombok.RequiredArgsConstructor;
import org.proshin.blog.MarkdownText;
import org.proshin.blog.Url;
import org.proshin.blog.exception.PostNotFoundException;
import org.proshin.blog.model.Post;
import org.proshin.blog.model.Posts;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
public class GuestPagesController {

    private final Posts posts;

    @GetMapping(value = { "", "/" })
    public ModelAndView getIndex(@RequestParam(defaultValue = "20") int count) {
        return new SmartModelAndView("index")
            .with("posts", posts.page(0, count, true));
    }

    @GetMapping("/post/{url}")
    public ModelAndView getPost(@PathVariable Url url, Authentication authentication) {
        Post post =
            posts
                .postByUrl(url)
                .orElseThrow(() -> new PostNotFoundException(url));
        if (!post.published() && (authentication == null || !authentication.isAuthenticated())) {
            throw new PostNotFoundException(url);
        }
        return new SmartModelAndView("post")
            .with("post", post)
            .with("content", post.content().map(content -> new MarkdownText(content).asHtml()).orElse(""));
    }
}