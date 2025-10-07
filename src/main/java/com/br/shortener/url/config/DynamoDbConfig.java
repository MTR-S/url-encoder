package com.br.shortener.url.config;

import com.br.shortener.url.domain.entity.dynamodb.UrlMapping;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

@Configuration
public class DynamoDbConfig {
    @Bean
    public DynamoDbClient dynamoDbClient() {
        return DynamoDbClient.builder()
                .region(Region.of("sa-east-1"))
                .build();
    }

    @Bean
    public DynamoDbEnhancedClient dynamoDbEnhancedClient(DynamoDbClient client) {
        return DynamoDbEnhancedClient.builder()
                .dynamoDbClient(client)
                .build();
    }

    @Bean
    public DynamoDbTable<UrlMapping> myItemTable(DynamoDbEnhancedClient enhancedClient) {
        String tableName = System.getenv("TABLE_NAME");

        return enhancedClient.table(tableName, TableSchema.fromBean(UrlMapping.class));
    }
}
