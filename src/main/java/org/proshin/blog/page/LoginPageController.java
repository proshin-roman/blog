package org.proshin.blog.page;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginPageController {
    @GetMapping("/login")
    public ModelAndView get() {
        return new SmartModelAndView("login");
    }
}
