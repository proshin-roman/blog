package org.proshin.blog.page.admin;

import static java.util.Collections.singletonMap;
import java.util.List;
import org.proshin.blog.dao.Posts;
import org.proshin.blog.model.Post;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/admin")
public class AdminPagesController {

    private final Posts posts;

    public AdminPagesController(Posts posts) {
        this.posts = posts;
    }

    @GetMapping({"", "/dashboard"})
    public String get() {
        return "admin/dashboard";
    }

    @GetMapping("/posts")
    public ModelAndView getPosts() {
        List<Post> postsPage = posts.selectPage(0, 20, false);
        return new ModelAndView("admin/posts",
            singletonMap("posts", postsPage));
    }
}
