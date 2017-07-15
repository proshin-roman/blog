package org.proshin.blog.page.admin

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod

@Controller
@RequestMapping(value = "/admin")
open class Dashboard {
    @RequestMapping(value = "/dashboard", method = arrayOf(RequestMethod.GET))
    fun get(): String {
        return "admin/dashboard";
    }
}