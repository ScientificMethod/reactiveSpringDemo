package com.example.javadynamo.models;

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSecondaryPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSortKey;

@DynamoDbBean
public class ExamplePojo {
    private String id;
    private String sort;

    private String gsiId;

    @DynamoDbPartitionKey
    public String getId() {
        return id;
    }


    public void setId(String id) {
        this.id = id;
    }

    @DynamoDbSortKey
    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    @DynamoDbSecondaryPartitionKey(indexNames = {"gsiExampleIndex"})
    public String getGsiId() {
        return gsiId;
    }

    public void setGsiId(String gsiId) {
        this.gsiId = gsiId;
    }
}
