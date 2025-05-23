package com.br.shortener.url.domain.services;

import com.br.shortener.url.domain.ports.outbound.EncrypterPort;
import com.br.shortener.url.domain.ports.outbound.UrlEncoderPort;
import com.br.shortener.url.domain.ports.outbound.SwapperPort;
import com.br.shortener.url.exceptions.InvalidUuidException;
import com.devskiller.friendly_id.FriendlyId;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

import java.util.UUID;

// Encapsular depois esses metodos em um outro -> codificador_de_url()

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

    private String appendSequencialNumber(String number) {
        /* *
        * Avaliar se vale a pena
        * Seria necessário caso fosse decidido que urls iguais feitas por requisições diferentes
        * necessitam serem diferentes (decidir)
        * */

        return "";
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

}
