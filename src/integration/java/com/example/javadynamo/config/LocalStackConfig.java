package com.example.javadynamo.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.testcontainers.containers.localstack.LocalStackContainer;
import org.testcontainers.utility.DockerImageName;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbAsyncTable;
import software.amazon.awssdk.enhanced.dynamodb.model.CreateTableEnhancedRequest;
import software.amazon.awssdk.enhanced.dynamodb.model.EnhancedGlobalSecondaryIndex;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbAsyncClient;
import software.amazon.awssdk.services.dynamodb.model.Projection;
import software.amazon.awssdk.services.dynamodb.model.ProjectionType;
import software.amazon.awssdk.services.sqs.SqsAsyncClient;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.testcontainers.containers.localstack.LocalStackContainer.Service.DYNAMODB;
import static org.testcontainers.containers.localstack.LocalStackContainer.Service.SQS;

@TestConfiguration
public class LocalStackConfig {
    final static Logger LOG = LoggerFactory.getLogger(LocalStackConfig.class);

    @Bean(initMethod = "start")
    public LocalStackContainer localStackContainer() {
        return new LocalStackContainer(DockerImageName.parse("localstack/localstack"))
                .withServices(DYNAMODB, SQS);
    }

    @Bean
    @Primary
    public DynamoDbAsyncClient localStackDynamo(LocalStackContainer container) {
        return DynamoDbAsyncClient.builder()
                .endpointOverride(container.getEndpointOverride(DYNAMODB))
                .credentialsProvider(StaticCredentialsProvider.create(
                        AwsBasicCredentials.create(
                                container.getAccessKey(), container.getSecretKey()
                        )))
                .region(Region.of(container.getRegion()))
                .build();
    }

    @Bean
    @Primary
    public SqsAsyncClient localStackSqs(LocalStackContainer container) {
        return SqsAsyncClient.builder()
                .endpointOverride(container.getEndpointOverride(SQS))
                .credentialsProvider(StaticCredentialsProvider.create(
                        AwsBasicCredentials.create(
                                container.getAccessKey(), container.getSecretKey()
                        )))
                .region(Region.of(container.getRegion()))
                .build();
    }

    /**
     * Uses spring beans to lazily orchestrate test setup
     *
     * This method depends on DynamoDbAsyncTable, which depends on DynamoDbAsyncClient, which depends on LocalStackContainer
     */
    @Bean // Use spring bean nesting to lazily orchestrate test setup
    public String createLocalDynamoTable(DynamoDbAsyncTable<Object> tablePojo) throws ExecutionException, InterruptedException, TimeoutException {
        return tablePojo.createTable(
                CreateTableEnhancedRequest.builder()
                        .globalSecondaryIndices(EnhancedGlobalSecondaryIndex.builder()
                                .indexName("exampleIndex1")
                                .projection(Projection.builder().projectionType(ProjectionType.KEYS_ONLY).build())
                                .build())
                        .build())
                .thenApply(voidArg -> {
                    LOG.info("Successfully created local table.");
                    return "Done";
                })
                .exceptionally(exception -> {
                    LOG.error("Failed to create local table.", exception);
                    return "Error";
                })
                .get(15, SECONDS);
    }
}
