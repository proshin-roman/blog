package org.proshin.blog.page;

import lombok.NonNull;
import org.proshin.blog.dao.Posts;
import org.proshin.blog.textprocessing.MarkdownText;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class GuestPagesController {

    private final Posts posts;

    public GuestPagesController(@NonNull Posts posts) {
        this.posts = posts;
    }

    @GetMapping(value = {"", "/"})
    public ModelAndView getIndex(@RequestParam(defaultValue = "20") int count) {
        return new SmartModelAndView("index")
            .with("posts", posts.selectPage(0, count, true));
    }

    @GetMapping("/post/{id:[\\d]+}")
    public ModelAndView getPost(@PathVariable Long id) {
        return new SmartModelAndView("post")
            .with("post", posts.selectOne(id))
            .with("content", new MarkdownText(posts.selectOne(id).getContent()).getAsHtml());
    }
}