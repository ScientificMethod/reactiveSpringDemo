package com.example.javadynamo.config;

import com.example.javadynamo.models.ExamplePojo;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbAsyncTable;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedAsyncClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.http.async.SdkAsyncHttpClient;
import software.amazon.awssdk.http.nio.netty.NettyNioAsyncHttpClient;
import software.amazon.awssdk.services.dynamodb.DynamoDbAsyncClient;

@Configuration
public class DynamoDbConfig {
    @Bean
    public DynamoDbAsyncTable<ExamplePojo> examplePojoTable(DynamoDbEnhancedAsyncClient enhancedClient) {
        return enhancedClient.table("myExampleTable", TableSchema.fromBean(ExamplePojo.class));
    }

    @Bean
    public DynamoDbEnhancedAsyncClient dynamoDbEnhancedClient(DynamoDbAsyncClient dynamoClient) {
        return DynamoDbEnhancedAsyncClient.builder()
                .dynamoDbClient(dynamoClient)
                .build();
    }
    @Bean
    public DynamoDbAsyncClient dynamoDbClient(SdkAsyncHttpClient dynamoHttpClient) {
        return DynamoDbAsyncClient.builder()
                .httpClient(dynamoHttpClient)
                .build();
    }

    @Bean
    public SdkAsyncHttpClient dynamoHttpClient() {
        return NettyNioAsyncHttpClient.builder()
                .build();
    }
}
