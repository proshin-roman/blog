package org.proshin.blog.page.admin;

import org.proshin.blog.page.SmartModelAndView;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/admin")
public class AdminPagesController {

    @GetMapping({"", "/dashboard"})
    public ModelAndView get() {
        return new SmartModelAndView("admin/dashboard");
    }
}
