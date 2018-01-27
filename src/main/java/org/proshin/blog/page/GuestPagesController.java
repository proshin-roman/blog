package org.proshin.blog.page;

import lombok.NonNull;
import org.proshin.blog.Url;
import org.proshin.blog.exception.PostNotFoundException;
import org.proshin.blog.model.PersistentPosts;
import org.proshin.blog.model.Post;
import org.proshin.blog.textprocessing.MarkdownText;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class GuestPagesController {

    private final PersistentPosts posts;

    public GuestPagesController(@NonNull PersistentPosts posts) {
        this.posts = posts;
    }

    @GetMapping(value = { "", "/" })
    public ModelAndView getIndex(@RequestParam(defaultValue = "20") int count) {
        return new SmartModelAndView("index")
                .with("posts", posts.page(0, count, true));
    }

    @GetMapping("/post/{url}")
    public ModelAndView getPost(@PathVariable Url url) {
        Post post =
                posts
                        .postByUrl(url)
                        .orElseThrow(() -> new PostNotFoundException(url));
        if (!post.published()) {
            throw new PostNotFoundException(url);
        }
        return new SmartModelAndView("post")
                .with("post", post)
                .with("content", new MarkdownText(post.content()).getAsHtml());
    }
}