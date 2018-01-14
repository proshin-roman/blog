package org.proshin.blog;

import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.model.AttributeDefinition;
import com.amazonaws.services.dynamodbv2.model.KeySchemaElement;
import com.amazonaws.services.dynamodbv2.model.KeyType;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.model.ScalarAttributeType;
import static java.util.Collections.singletonList;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Rule;
import org.junit.runner.RunWith;
import org.proshin.blog.dynamodb.DynamoPosts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public abstract class AbstractIntegrationTest {
    @Autowired
    private MockMvc mvc;
    @Rule
    public DynamoRunner dynamoRunner = new DynamoRunner();
    @Autowired
    private DynamoDB dynamoDB;

    @Before
    public void setUpTestEnvironment() throws Exception {
        dynamoDB.createTable(
                DynamoPosts.TABLE_NAME,
                singletonList(new KeySchemaElement("id", KeyType.HASH)),
                singletonList(new AttributeDefinition("id", ScalarAttributeType.S)),
                new ProvisionedThroughput()
                        .withWriteCapacityUnits(1L)
                        .withReadCapacityUnits(1L));
        log.info("Table {} has been created", DynamoPosts.TABLE_NAME);
    }

    protected MockMvc getMvc() {
        return mvc;
    }
}
