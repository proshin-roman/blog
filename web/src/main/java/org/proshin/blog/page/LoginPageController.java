package org.proshin.blog.page;

import org.proshin.blog.configuration.ConfigParameters;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginPageController {

    private final ConfigParameters configParameters;

    public LoginPageController(ConfigParameters configParameters) {
        this.configParameters = configParameters;
    }

    @GetMapping("/login")
    public ModelAndView get() {
        return new SmartModelAndView("login")
            .with("reCaptchaKey", configParameters.getReCaptcha().getKey());
    }
}
