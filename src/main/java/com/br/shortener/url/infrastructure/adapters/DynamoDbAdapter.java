// DynamoDbAdapter.java - Versão Corrigida e Final

package com.br.shortener.url.infrastructure.adapters;

import com.br.shortener.url.api.dto.ShortCodeInfoResponse;
import com.br.shortener.url.domain.entity.dynamodb.UrlMapping;
import com.br.shortener.url.domain.ports.outbound.StorageUrlPort;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.*;

import java.util.Map;
import java.util.Optional;

@Component
public class DynamoDbAdapter implements StorageUrlPort {

    private final DynamoDbClient dynamoDbClient;
    private final DynamoDbTable<UrlMapping> urlMappingTable;
    private final String tableName;
    private final TableSchema<UrlMapping> urlMappingSchema = TableSchema.fromBean(UrlMapping.class);

    public DynamoDbAdapter(DynamoDbClient dynamoDbClient,
                           DynamoDbTable<UrlMapping> urlMappingTable) {
        this.dynamoDbClient = dynamoDbClient;
        this.urlMappingTable = urlMappingTable;
        this.tableName = urlMappingTable.tableName();
    }

    @Override
    public void saveUrl(String shortCode, String longUrl, Long optionalExpirationTime) {
        UrlMapping newMapping = setNewUrlMapping(shortCode, longUrl, optionalExpirationTime);
        urlMappingTable.putItem(newMapping);
    }

    private UrlMapping setNewUrlMapping(String shortCode, String longUrl, Long optionalExpirationTime) {
        UrlMapping newMapping = new UrlMapping();
        newMapping.setShortCode(shortCode);
        newMapping.setLongUrl(longUrl);
        newMapping.setNumberOfAccesses(0);

        if (optionalExpirationTime != null) {
            newMapping.setExpirationTime(optionalExpirationTime);
        }
        return newMapping;
    }

    @Override
    public ShortCodeInfoResponse getShortUrlInfo(String shortCode) {
        GetItemRequest request = GetItemRequest.builder()
                .tableName(tableName)
                .key(Map.of("shortCode", AttributeValue.builder().s(shortCode).build()))
                .build();

        GetItemResponse response = dynamoDbClient.getItem(request);

        Map<String, AttributeValue> item = response.item();

        if (response.hasItem()) {
            return createUrlResponse(item);
        } else {
            return null;
        }
    }

    private ShortCodeInfoResponse createUrlResponse(Map<String, AttributeValue> item ) {
        String longUrlResponse = Optional.ofNullable(item.get("longUrl"))
                .map(AttributeValue::s)
                .orElse(null);

        String shortCodeResponse = Optional.ofNullable(item.get("shortCode"))
                .map(AttributeValue::s)
                .orElse(null);

        Long numberOfAccessesResponse = Optional.ofNullable(item.get("numberOfAccesses"))
                .map(AttributeValue::n)
                .map(Long::parseLong)
                .orElse(null);

        Integer expirationTimeResponse = Optional.ofNullable(item.get("expirationTime"))
                .map(AttributeValue::n)
                .map(Integer::parseInt)
                .orElse(null);

        return new ShortCodeInfoResponse(longUrlResponse, shortCodeResponse, numberOfAccessesResponse, expirationTimeResponse);

    }

    @Override
    public String getUrl(String shortCode) {
        Map<String, AttributeValue> key = createKey(shortCode);

        UpdateItemRequest request = createRequestToUpdate(this.tableName, key);

        try {
            UpdateItemResponse response = this.dynamoDbClient.updateItem(request);

            if (!response.hasAttributes()) {
                return null;
            }

            UrlMapping oldItem = this.urlMappingSchema.mapToItem(response.attributes());
            return oldItem.getLongUrl();

        } catch (ResourceNotFoundException e) { // Tipo do erro: Item não encontrado para o shortCode
            return null;
        } catch (DynamoDbException e) { // Tipo do erro: Erro ao atualizar item no DynamoDB
            return null;
        }
    }

    private Map<String, AttributeValue> createKey(String shortCode) {
        return Map.of(
                "shortCode", AttributeValue.builder().s(shortCode).build()
        );
    }

    private UpdateItemRequest createRequestToUpdate(String tableName, Map<String, AttributeValue> key) {
        String updateExpression = "SET #na = #na + :incr";
        Map<String, String> expressionAttributeNames = Map.of("#na", "numberOfAccesses");
        Map<String, AttributeValue> expressionAttributeValues = Map.of(
                ":incr", AttributeValue.builder().n("1").build()
        );

        return UpdateItemRequest.builder()
                .tableName(tableName)
                .key(key)
                .updateExpression(updateExpression)
                .expressionAttributeNames(expressionAttributeNames)
                .expressionAttributeValues(expressionAttributeValues)
                .returnValues(ReturnValue.ALL_OLD)
                .build();
    }


}