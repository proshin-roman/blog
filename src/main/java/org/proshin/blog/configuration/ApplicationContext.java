package org.proshin.blog.configuration;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder.EndpointConfiguration;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import io.sentry.spring.SentryExceptionResolver;
import io.sentry.spring.SentryServletContextInitializer;
import lombok.extern.log4j.Log4j;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.HandlerExceptionResolver;

@Log4j
@Configuration
public class ApplicationContext {

    public ApplicationContext() {
        log.info("A new instance of application context has been instantiated");
    }

    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }

    @Bean
    public DynamoDB getDynamoDB(ConfigParameters configParameters) {
        ConfigParameters.Dynamo dynamoConfig = configParameters.getDynamo();

        AmazonDynamoDBClientBuilder clientBuilder =
                AmazonDynamoDBClientBuilder
                        .standard();

        if (dynamoConfig.getEndpoint() != null) {
            clientBuilder
                    .withEndpointConfiguration(
                            new EndpointConfiguration(
                                    dynamoConfig.getEndpoint(),
                                    dynamoConfig.getRegion()));
        } else {
            clientBuilder
                    .withRegion(dynamoConfig.getRegion());
        }

        if (dynamoConfig.getAccessKey() != null && dynamoConfig.getSecretKey() != null) {
            clientBuilder
                    .withCredentials(
                            new AWSStaticCredentialsProvider(
                                    new BasicAWSCredentials(
                                            dynamoConfig.getAccessKey(),
                                            dynamoConfig.getSecretKey())));
        }

        return new DynamoDB(clientBuilder.build());
    }

    @Bean
    public HandlerExceptionResolver sentryExceptionResolver() {
        return new SentryExceptionResolver();
    }

    @Bean
    public ServletContextInitializer sentryServletContextInitializer() {
        return new SentryServletContextInitializer();
    }
}
