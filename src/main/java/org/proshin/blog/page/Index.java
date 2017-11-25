package org.proshin.blog.page;

import java.util.HashMap;
import java.util.Map;
import lombok.NonNull;
import org.proshin.blog.dao.Posts;
import org.proshin.blog.model.Post;
import org.proshin.blog.textprocessing.MarkdownText;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class Index {

    private final Posts posts;

    public Index(@NonNull Posts posts) {
        this.posts = posts;
    }

    @GetMapping(value = { "", "/" })
    public ModelAndView getIndex(@RequestParam(defaultValue = "20") int count) {
        Map<String, Object> model = new HashMap<>();
        model.put("posts", posts.selectPage(0, count));
        return new ModelAndView("index", model);
    }

    @GetMapping("/post/{id:[\\d]+}")
    public ModelAndView getPost(@PathVariable Long id) {
        Post post = posts.selectOne(id);
        Map<String, Object> model = new HashMap<>();
        model.put("post", post);
        model.put("content", new MarkdownText(post.getContent()).getAsHtml());
        return new ModelAndView("post", model);
    }
}