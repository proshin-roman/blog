package org.proshin.blog.configuration;

import javax.annotation.PostConstruct;
import no.api.freemarker.java8.Java8ObjectWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.freemarker.FreeMarkerAutoConfiguration;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FreemarkerConfig extends FreeMarkerAutoConfiguration.FreeMarkerWebConfiguration {

    @Autowired
    private freemarker.template.Configuration configuration;

    @PostConstruct
    public void postConstruct() {
        configuration.setObjectWrapper(
            new Java8ObjectWrapper(freemarker.template.Configuration.getVersion())); // VERSION_2_3_26
    }

}
