package org.proshin.blog.page

import org.hamcrest.Matchers.`is`
import org.junit.Assert.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner

@SpringBootTest
@RunWith(SpringRunner::class)
open class IndexTest {

    @Autowired
    lateinit var index: Index

    @Test
    fun testGet() {
        assertThat(index.get(), `is`("Hello, Kotlin world!"))
    }
}


