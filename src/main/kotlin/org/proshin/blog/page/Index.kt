package org.proshin.blog.page

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.ResponseBody

@Controller
open class Index {
    @ResponseBody
    @RequestMapping(value = *arrayOf("", "/"), method = arrayOf(RequestMethod.GET))
    fun get(): String {
        return "Hello, Kotlin world!"
    }
}


