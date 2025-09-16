package com.br.shortener.url.domain.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;

import java.util.Map;

@Service
public class S3Service {

    private final S3Client s3Client;
    private final String bucketName = "matheus-url-shortener-bucket-1401";

    public S3Service(S3Client s3Client) {
        this.s3Client = s3Client;
    }

    public void saveUrl(String shortCode, String longUrl) {
        String content = "{ \"longUrl\": \"" + longUrl + "\" }";

        PutObjectRequest putRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(shortCode + ".json")
                .contentType("application/json")
                .build();

        s3Client.putObject(putRequest, software.amazon.awssdk.core.sync.RequestBody.fromString(content));
    }

    public String getUrl(String shortCode) {
        try {
            GetObjectRequest getRequest = GetObjectRequest.builder()
                    .bucket(bucketName)
                    .key(shortCode + ".json")
                    .build();

            String json = s3Client.getObjectAsBytes(getRequest).asUtf8String();

            ObjectMapper mapper = new ObjectMapper();
            Map<String, String> map = mapper.readValue(json, Map.class);
            return map.get("longUrl");

        } catch (S3Exception e) {
            if (e.statusCode() == 404) {
                throw new RuntimeException("URL não encontrada para o código: " + shortCode);
            }
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar URL do S3", e);
        }
    }



}
