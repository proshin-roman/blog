package org.proshin.blog.page;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.ui.ExtendedModelMap;

@SpringBootTest
@RunWith(SpringRunner.class)
public class IndexTest {

    @Autowired
    private Index index;

    @Test
    public void testGet() throws Exception {
        assertThat(index.get(20, new ExtendedModelMap()), is("index"));
    }
}
