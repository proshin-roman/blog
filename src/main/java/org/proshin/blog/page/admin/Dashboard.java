package org.proshin.blog.page.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/admin")
public class Dashboard {
    @GetMapping({ "", "/dashboard" })
    public String get() {
        return "admin/dashboard";
    }
}
