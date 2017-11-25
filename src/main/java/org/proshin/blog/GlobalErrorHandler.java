package org.proshin.blog;

import lombok.extern.slf4j.Slf4j;
import org.proshin.blog.page.SmartModelAndView;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@ControllerAdvice
public class GlobalErrorHandler {

    @ExceptionHandler(Throwable.class)
    public ModelAndView catchThrowable(Throwable throwable) {
        log.error("Uncatched error!", throwable);
        return new SmartModelAndView("error");
    }
}
