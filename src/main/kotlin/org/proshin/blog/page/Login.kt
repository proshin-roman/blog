package org.proshin.blog.page

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod

@Controller
class Login {

    @RequestMapping(value = "/login", method = arrayOf(RequestMethod.GET))
    fun get(): String {
        return "login";
    }
}