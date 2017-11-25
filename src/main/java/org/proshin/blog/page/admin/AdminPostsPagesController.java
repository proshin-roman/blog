package org.proshin.blog.page.admin;

import lombok.NonNull;
import org.proshin.blog.dao.Posts;
import org.proshin.blog.page.SmartModelAndView;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/admin/posts")
public class AdminPostsPagesController {

    private final Posts posts;

    public AdminPostsPagesController(@NonNull Posts posts) {
        this.posts = posts;
    }

    @GetMapping({"", "/"})
    public ModelAndView getPosts() {
        return new SmartModelAndView("admin/posts/list")
            .with("posts", posts.selectPage(0, 20, false));
    }

    @GetMapping("/{id:[\\d]+}/edit")
    public ModelAndView edit(@PathVariable("id") Long id) {
        return new SmartModelAndView("/admin/posts/editor")
            .with("post", posts.selectOne(id));
    }
}
