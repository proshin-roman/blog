package org.proshin.blog.page

import org.springframework.stereotype.Controller
import org.springframework.ui.ModelMap
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod

@Controller
class Index {
    @RequestMapping(value = *arrayOf("", "/"), method = arrayOf(RequestMethod.GET))
    fun get(model: ModelMap): String {
        return "index"
    }
}