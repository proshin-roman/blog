package org.proshin.blog.configuration;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder.EndpointConfiguration;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ApplicationContext {

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
}
