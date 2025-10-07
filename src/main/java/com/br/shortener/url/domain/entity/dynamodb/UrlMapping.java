package com.br.shortener.url.domain.entity.dynamodb;

import lombok.Getter;
import lombok.Setter;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

@Getter
@Setter
@DynamoDbBean
public class UrlMapping {
    private String shortCode;
    private String longUrl;
    private Long expirationTime;
    private Integer numberOfAccesses;

    @DynamoDbPartitionKey
    public String getShortCode() {
        return shortCode;
    }
}
