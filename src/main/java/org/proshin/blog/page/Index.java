package org.proshin.blog.page;

import lombok.AllArgsConstructor;
import org.proshin.blog.dao.Posts;
import org.proshin.blog.model.Post;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@AllArgsConstructor
public class Index {

    private final JdbcTemplate jdbcTemplate;

    @GetMapping(value = { "", "/" })
    public String get(@RequestParam(defaultValue = "20") int count, Model model) {
        model.addAttribute("posts", new Posts(jdbcTemplate).selectPage(0, count));
        return "index";
    }

    @GetMapping("/post/{id:[\\d]+}")
    public String getPost(@PathVariable Long id, Model model) {
        Post post = new Posts(jdbcTemplate).selectOne(id);
        model.addAttribute("post", post);
        return "post";
    }
}