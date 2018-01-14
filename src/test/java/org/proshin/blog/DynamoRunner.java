package org.proshin.blog;

import com.amazonaws.services.dynamodbv2.local.main.ServerRunner;
import com.amazonaws.services.dynamodbv2.local.server.DynamoDBProxyServer;
import lombok.extern.slf4j.Slf4j;
import org.junit.rules.MethodRule;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;

@Slf4j
public class DynamoRunner implements MethodRule {

    public DynamoRunner() {
        System.setProperty("sqlite4java.library.path", "native-libs");
    }

    @Override
    public Statement apply(Statement statement, FrameworkMethod frameworkMethod, Object o) {
        return new Statement() {
            public void evaluate() throws Throwable {
                DynamoDBProxyServer server = null;
                try {
                    server = ServerRunner.createServerFromCommandLineArgs(new String[] { "-inMemory" });
                    server.start();
                    log.info("DynamoDB has been started");
                    statement.evaluate();
                } finally {
                    if (server != null) {
                        server.stop();
                        log.info("DynamoDB has been stopped");
                    }
                }
            }
        };
    }
}
