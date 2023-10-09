package com.example.javadynamo.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.services.sqs.SqsAsyncClient;
import software.amazon.awssdk.services.sqs.model.GetQueueUrlRequest;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@Configuration
public class SqsConfig {
    final static Logger LOG = LoggerFactory.getLogger(SqsConfig.class);

    @Bean
    public SqsAsyncClient sqsClient() {
        return SqsAsyncClient.builder()
                .build();
    }
}
