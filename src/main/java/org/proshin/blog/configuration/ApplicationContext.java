package org.proshin.blog.configuration;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder.EndpointConfiguration;
import static com.amazonaws.regions.Regions.EU_CENTRAL_1;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
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
        AmazonDynamoDB amazonDynamoDB;
        if (configParameters.getDynamo() != null && configParameters.getDynamo().getEndpoint() != null) {
            amazonDynamoDB =
                    AmazonDynamoDBClientBuilder
                            .standard()
                            .withEndpointConfiguration(
                                    new EndpointConfiguration(
                                            configParameters.getDynamo().getEndpoint(),
                                            EU_CENTRAL_1.getName()))
                            .withCredentials(
                                    new AWSStaticCredentialsProvider(
                                            new BasicAWSCredentials(
                                                    configParameters.getDynamo().getAccessKey(),
                                                    configParameters.getDynamo().getSecretKey())))
                            .build();
        } else {
            amazonDynamoDB =
                    AmazonDynamoDBClientBuilder
                            .standard()
                            .withRegion(EU_CENTRAL_1)
                            .build();
        }
        return new DynamoDB(amazonDynamoDB);
    }
}
