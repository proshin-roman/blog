package org.proshin.blog.page.admin;

import java.time.LocalDateTime;
import lombok.Data;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.proshin.blog.dynamodb.DynamoPost;
import org.proshin.blog.dynamodb.DynamoPosts;
import org.proshin.blog.model.Post;
import org.proshin.blog.page.SmartModelAndView;
import org.proshin.blog.page.bind.Html5LocalDateTimeEditorSupport;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@Controller
@RequestMapping(value = "/admin/posts")
public class AdminPostsPagesController {

    private final DynamoPosts posts;

    public AdminPostsPagesController(@NonNull DynamoPosts posts) {
        this.posts = posts;
    }

    @GetMapping({ "", "/" })
    public ModelAndView getPosts() {
        return new SmartModelAndView("admin/posts/list")
                .with("posts", posts.selectPage(0, 20, false));
    }

    @GetMapping("/create")
    public ModelAndView create() {
        return new SmartModelAndView(
                String.format("redirect:/admin/posts/%s/edit",
                        posts.create("New post", "It's a draft").getId()));
    }

    @GetMapping("/{id}/edit")
    public ModelAndView edit(@PathVariable("id") String id) {
        return new SmartModelAndView("/admin/posts/edit")
                .with("post", posts.selectOne(id));
    }

    @GetMapping("/{id}/publish")
    public ModelAndView publish(@PathVariable("id") String id) {
        posts.selectOne(id)
                .publish()
                .save();
        return new SmartModelAndView("redirect:/admin/posts/");
    }

    @GetMapping("/{id}/unpublish")
    public ModelAndView unpublish(@PathVariable("id") String id) {
        posts.selectOne(id)
                .unpublish()
                .save();
        return new SmartModelAndView("redirect:/admin/posts/");
    }

    @GetMapping("/{id}/delete")
    public ModelAndView delete(@PathVariable("id") String id) {
        posts.delete(id);
        return new SmartModelAndView("redirect:/admin/posts/");
    }

    @PostMapping("/{id}/save")
    public ModelAndView save(@PathVariable("id") String id, @ModelAttribute("post") ChangedPost post,
            BindingResult bindingResult) {
        new DynamoPost(posts.getTable(), id, post.getTitle(), post.getCreationDate(), post.getPublicationDate(),
                post.isPublished(), post.getContent())
                        .save();
        return new SmartModelAndView("redirect:/admin/posts");
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(LocalDateTime.class, new Html5LocalDateTimeEditorSupport());
    }

    @Data
    private static class ChangedPost implements Post {
        private String id;
        private String title;
        private LocalDateTime creationDate;
        private LocalDateTime publicationDate;
        private boolean published;
        private String content;
    }
}
