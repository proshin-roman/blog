package org.proshin.blog.page.admin;

import java.beans.PropertyEditorSupport;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.proshin.blog.dao.Posts;
import org.proshin.blog.model.Post;
import org.proshin.blog.page.SmartModelAndView;
import org.springframework.jdbc.core.JdbcTemplate;
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

    private final Posts posts;
    private final JdbcTemplate jdbcTemplate;

    public AdminPostsPagesController(@NonNull Posts posts, @NonNull JdbcTemplate jdbcTemplate) {
        this.posts = posts;
        this.jdbcTemplate = jdbcTemplate;
    }

    @GetMapping({ "", "/" })
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

    @PostMapping("/{id:[\\d]+}/save")
    public ModelAndView save(@PathVariable("id") Long id, @ModelAttribute("post") Post post,
            BindingResult bindingResult) {
        new Post(id, post.getTitle(), post.getCreationDate(), post.getPublicationDate(), post.isPublished(),
                post.getContent()).save(jdbcTemplate);
        return new SmartModelAndView("redirect:/admin/posts");
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(LocalDateTime.class, new Html5LocalDateTimeEditor());
    }

    public static class Html5LocalDateTimeEditor extends PropertyEditorSupport {

        private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");

        @Override
        public String getAsText() {
            try {
                return Optional.ofNullable(getValue())
                        .map(value -> ((LocalDateTime) getValue()).format(dateTimeFormatter))
                        .orElse(null);
            } catch (Exception e) {
                log.error("Couldn't convert date '{}' to string", getValue(), e);
                return null;
            }
        }

        @Override
        public void setAsText(String text) throws IllegalArgumentException {
            Optional.ofNullable(text)
                    .ifPresent(nonEmptyText -> {
                        try {
                            LocalDateTime newValue =
                                    LocalDateTime.parse(nonEmptyText, dateTimeFormatter);
                            setValue(newValue);
                        } catch (Exception e) {
                            log.error("Couldn't convert string '{}' to date", nonEmptyText, e);
                        }
                    });
        }
    }
}
