package org.proshin.blog.page;

import lombok.AllArgsConstructor;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.ui.ExtendedModelMap;

@SpringBootTest
@AllArgsConstructor
@RunWith(SpringRunner.class)
public class IndexTest {

    private final Index index;

    @Test
    public void testGet() throws Exception {
        assertThat(index.get(20, new ExtendedModelMap()), is("index"));
    }
}
