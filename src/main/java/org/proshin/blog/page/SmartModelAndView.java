package org.proshin.blog.page;

import org.springframework.web.servlet.ModelAndView;

public class SmartModelAndView extends ModelAndView {
    public SmartModelAndView(String view) {
        super(view);
    }

    public SmartModelAndView with(String name, Object value) {
        addObject(name, value);
        return this;
    }
}
