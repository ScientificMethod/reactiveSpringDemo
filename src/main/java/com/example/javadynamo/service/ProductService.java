package com.example.javadynamo.service;

import com.example.javadynamo.models.ExamplePojo;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbAsyncTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.model.GetItemEnhancedRequest;

@Service
public class ProductService {

    private final DynamoDbAsyncTable<ExamplePojo> productTable;

    public ProductService(DynamoDbAsyncTable<ExamplePojo> productTable) {
        this.productTable = productTable;
    }

    public Mono<ExamplePojo> getProductCopy(String id) {
        return Mono.fromFuture(
                productTable.getItem(GetItemEnhancedRequest.builder()
                        .key(Key.builder()
                                .partitionValue(id)
                                .build())
                .build()));
    }
}
