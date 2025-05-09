package com.br.shortener.url.domain.services;

import com.br.shortener.url.ports.outbound.EncrypterPort;
import com.br.shortener.url.ports.outbound.UrlEncoderPort;

// Encapsular depois esses metodos em um outro -> codificador_de_url()

public class UrlShortenerService {
    UrlEncoderPort urlEncoder;
    EncrypterPort encrypter;

    public UrlShortenerService(UrlEncoderPort urlEncoderAdapter,
                               EncrypterPort encrypter) {
        this.urlEncoder = urlEncoderAdapter;
        this.encrypter = encrypter;
    }

    private String encodeToUtf8(String url) {
        return urlEncoder.encodeToUtf8(url);
    }

    private String appendSequencialNumber(String number) {
        /* *
        * Avaliar se vale a pena
        * Seria necessário caso fosse decidido que urls iguais feitas por requisições diferentes
        * necessitam serem diferentes (decidir)
        * */

        return "";
    }

    private String md5Hash(String toBeEncrypted) {
        return encrypter.encrypt(toBeEncrypted);
    }

    private String base62Encode(String number) {

        return "";
    }

    // Verificar possibilidade de separar esse método
    private String swapAndPick(String number) {

        return "";
    }

    public String generateShortUrl(String number) {
        // 7 characters string
        return "";
    }

}
