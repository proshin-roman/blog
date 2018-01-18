package org.proshin.blog.configuration;

import freemarker.template.TemplateExceptionHandler;
import freemarker.template.TemplateModelException;
import javax.annotation.PostConstruct;
import no.api.freemarker.java8.Java8ObjectWrapper;
import static org.apache.commons.lang3.StringUtils.left;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.freemarker.FreeMarkerAutoConfiguration;
import org.springframework.context.annotation.Configuration;

@Configuration
@SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
public class FreemarkerConfig extends FreeMarkerAutoConfiguration.FreeMarkerWebConfiguration {

    @Autowired
    private freemarker.template.Configuration configuration;
    @Autowired
    private ConfigParameters configParameters;

    @PostConstruct
    public void postConstruct() throws TemplateModelException {
        configuration
                .setObjectWrapper(
                        new Java8ObjectWrapper(freemarker.template.Configuration.getVersion())); // VERSION_2_3_26

        configuration.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);

        configuration.setSharedVariable("version", configParameters.getBuildInfo().getVersion());
        configuration.setSharedVariable("commit", left(configParameters.getBuildInfo().getCommit(), 6));
        configuration.setSharedVariable("repositoryURL", configParameters.getBuildInfo().getRepositoryUrl());
    }
}
