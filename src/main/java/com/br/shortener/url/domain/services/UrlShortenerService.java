package com.br.shortener.url.domain.services;

import com.br.shortener.url.domain.ports.outbound.EncrypterPort;
import com.br.shortener.url.domain.ports.outbound.UrlEncoderPort;
import com.br.shortener.url.domain.ports.outbound.SwapperPort;
import com.br.shortener.url.exceptions.InvalidUuidException;
import com.devskiller.friendly_id.FriendlyId;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Service
public class UrlShortenerService {
    UrlEncoderPort urlEncoder;
    EncrypterPort encrypter;
    SwapperPort swapper;

    public UrlShortenerService(UrlEncoderPort urlEncoderAdapter,
                               EncrypterPort encrypter,
                               SwapperPort swapper) {
        this.urlEncoder = urlEncoderAdapter;
        this.encrypter = encrypter;
        this.swapper = swapper;
    }

    public String generateShortUrl(String originalUrl) {
        try {
            String utf8Encoded = urlEncoder.encodeToUtf8(originalUrl);
            String encrypted = encrypter.encrypt(utf8Encoded);

            UUID uuid = UUID.nameUUIDFromBytes(encrypted.getBytes(StandardCharsets.UTF_8));
            String friendlyId = FriendlyId.toFriendlyId(uuid);

            return swapper.pickRandomNumbers(friendlyId, originalUrl, 7);
        } catch (InvalidUuidException e) {
            throw new InvalidUuidException("Error creating UUID: " + e);
        }
    }

    public Long calculateExpirationTime(String expirationTime) {
        if (expirationTime == null || expirationTime.isBlank()) {
            return null;
        }

        expirationTime = expirationTime.trim().toLowerCase();

        // Número + unidade (minutes (m), hours (h), days (d))
        long value = Long.parseLong(expirationTime.replaceAll("[^0-9]", ""));
        String unit = expirationTime.replaceAll("[0-9]", "");

        Instant now = Instant.now();

        return switch (unit) {
            case "m" -> now.plus(value, ChronoUnit.MINUTES).getEpochSecond();
            case "h" -> now.plus(value, ChronoUnit.HOURS).getEpochSecond();
            case "d" -> now.plus(value, ChronoUnit.DAYS).getEpochSecond();
            default -> throw new IllegalArgumentException("Invalid expiration time format. Use m/h/d. Ex: 10m, 2h, 3d");
        };
    }
}
