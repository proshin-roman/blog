package org.proshin.blog.page.admin;

import java.time.LocalDateTime;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.NotBlank;
import org.proshin.blog.Url;
import org.proshin.blog.exception.PostNotFoundException;
import org.proshin.blog.model.PersistentPost;
import org.proshin.blog.model.Post;
import org.proshin.blog.model.Posts;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@Controller
@RequestMapping(value = "/admin/posts")
public class AdminPostsPagesController {

    private final Posts posts;

    public AdminPostsPagesController(Posts posts) {
        this.posts = posts;
    }

    @GetMapping({ "", "/" })
    public ModelAndView getPosts() {
        return new SmartModelAndView("admin/posts/list")
            .with("posts", posts.page(0, 20, false));
    }

    @PostMapping("/create")
    public ModelAndView create(@RequestParam Url url, @RequestParam String title) {
        return new SmartModelAndView(
            String.format("redirect:/admin/posts/%s/edit",
                posts.create(url, title, "It's a draft")
                    .url().encoded()
            ));
    }

    @GetMapping("/{url}/edit")
    public ModelAndView edit(@PathVariable("url") Url url) {
        Post post =
            posts.postByUrl(url)
                .orElseThrow(() -> new PostNotFoundException(url));
        return new SmartModelAndView("/admin/posts/edit")
            .with("post",
                new ChangedPost(
                    post.url().decoded(),
                    post.title(),
                    post.shortcut().orElse(""),
                    post.creationDate(),
                    post.publicationDate().orElse(null),
                    post.published(),
                    post.content().orElse("")
                )
            );
    }

    @GetMapping("/{url}/publish")
    public ModelAndView publish(@PathVariable("url") Url url) {
        posts.postByUrl(url)
            .ifPresent(post -> post
                .publish()
                .update());
        return new SmartModelAndView("redirect:/admin/posts/");
    }

    @GetMapping("/{url}/unpublish")
    public ModelAndView unpublish(@PathVariable("url") Url url) {
        posts.postByUrl(url)
            .ifPresent(post -> post
                .unpublish()
                .update());
        return new SmartModelAndView("redirect:/admin/posts/");
    }

    @GetMapping("/{url}/delete")
    public ModelAndView delete(@PathVariable("url") Url url) {
        posts.postByUrl(url)
            .ifPresent(PersistentPost::delete);
        return new SmartModelAndView("redirect:/admin/posts/");
    }

    @PostMapping("/{originalUrl}/save")
    public ModelAndView save(@PathVariable("originalUrl") Url originalUrl,
        @Valid @ModelAttribute("post") ChangedPost post,
        BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            return this.edit(originalUrl);
        }
        return new SmartModelAndView("redirect:/admin/posts");
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(LocalDateTime.class, new Html5LocalDateTimeEditorSupport());
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ChangedPost {
        private String url;
        @NotBlank
        private String title;
        @NotBlank
        private String shortcut;
        private LocalDateTime creationDate;
        private LocalDateTime publicationDate;
        private boolean published;
        @NotBlank
        private String content;
    }
}
