package com.br.shortener.url.domain.services;

import com.br.shortener.url.domain.ports.outbound.EncrypterPort;
import com.br.shortener.url.domain.ports.outbound.UrlEncoderPort;
import com.devskiller.friendly_id.FriendlyId;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Random;
import java.util.UUID;

// Encapsular depois esses metodos em um outro -> codificador_de_url()

@Service
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

    private String hashEncrypt(String toBeEncrypted) {
        return encrypter.encrypt(toBeEncrypted);
    }

    private String base62Encode(String toBeEncoded) {

        return "";
    }

    // Verificar possibilidade de separar esse método
    private String swapAndPick(String number) {

        return "";
    }

    public String generateShortUrl(String number) {
        // 7 characters string
        String urlUtf8Pattern =  urlEncoder.encodeToUtf8(number);

        String urlEncrypted = encrypter.encrypt(urlUtf8Pattern);
        try {
            //tratar excesoes
            UUID uuidFromUrl = UUID.nameUUIDFromBytes(urlEncrypted.getBytes(StandardCharsets.UTF_8));
            String friendlyIdFromUrlUuid = FriendlyId.toFriendlyId(uuidFromUrl);


            return swapAndPickRandomNumbers(friendlyIdFromUrlUuid, "InputToBePutAtApplicationYaml");
            //Verificar se faz sentido o swap ficar aqui na arquitetura

        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return "null";
    }

    public String swapAndPickRandomNumbers(String stringWith22Characters, String seedInput) {
        long seed = seedInput.hashCode();
        Random random = new Random(seed);

        StringBuilder result = new StringBuilder();
        for (int i = 0; i < 7; i++) {
            int randomIndex = random.nextInt(stringWith22Characters.length());
            result.append(stringWith22Characters.charAt(randomIndex));
        }

        return result.toString();
    }
}
