package com.br.shortener.url.config;

import com.br.shortener.url.domain.entity.dynamodb.UrlMapping;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

import java.net.URI;

@Configuration
public class DynamoDbConfig {
    @Bean
    public DynamoDbClient dynamoDbClient() {
        if ("true".equals(System.getenv("AWS_SAM_LOCAL"))) {
            return DynamoDbClient.builder()
                    .endpointOverride(URI.create("http://dynamodb-local:8000"))
                    .region(Region.of("sa-east-1"))
                    .build();
        } else {
            return DynamoDbClient.builder()
                    .region(Region.of("sa-east-1"))
                    .build();
        }
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

        if (tableName == null || tableName.trim().isEmpty()) {
            tableName = "UrlMappings";
        }

        return enhancedClient.table(tableName, TableSchema.fromBean(UrlMapping.class));
    }
}
