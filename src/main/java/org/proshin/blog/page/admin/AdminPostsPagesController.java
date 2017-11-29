package org.proshin.blog.page.admin;

import lombok.NonNull;
import org.proshin.blog.dao.Posts;
import org.proshin.blog.model.Post;
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

    @GetMapping("/create")
    public ModelAndView create() {
        Post post = posts.create();
        return new SmartModelAndView(String.format("redirect:/admin/posts/%d/edit", post.getId()));
    }

    @GetMapping("/{id:[\\d]+}/edit")
    public ModelAndView edit(@PathVariable("id") Long id) {
        return new SmartModelAndView("/admin/posts/edit")
            .with("post", posts.selectOne(id));
    }

    @GetMapping("/{id:[\\d]+}/publish")
    public ModelAndView publish(@PathVariable("id") Long id) {
        posts.publish(id);
        return new SmartModelAndView("redirect:/admin/posts/");
    }

    @GetMapping("/{id:[\\d]+}/unpublish")
    public ModelAndView unpublish(@PathVariable("id") Long id) {
        posts.unpublish(id);
        return new SmartModelAndView("redirect:/admin/posts/");
    }

    @GetMapping("/{id:[\\d]+}/delete")
    public ModelAndView delete(@PathVariable("id") Long id) {
        posts.delete(id);
        return new SmartModelAndView("redirect:/admin/posts/");
    }
}
