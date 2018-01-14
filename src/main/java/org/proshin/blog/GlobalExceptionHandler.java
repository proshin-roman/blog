package org.proshin.blog;

import lombok.extern.slf4j.Slf4j;
import org.proshin.blog.exception.PostNotFoundException;
import org.proshin.blog.page.SmartModelAndView;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Throwable.class)
    public ModelAndView catchThrowable(Throwable throwable) {
        log.error("Uncaught error!", throwable);
        return new SmartModelAndView("error");
    }

    @ExceptionHandler(PostNotFoundException.class)
    public ModelAndView catchPostNotFoundException(PostNotFoundException exception) {
        return new SmartModelAndView("error/404")
                .withStatus(HttpStatus.NOT_FOUND);
    }
}
