package com.br.shortener.url.infrastructure.adapters;

import com.br.shortener.url.domain.ports.outbound.EncrypterPort;
import com.br.shortener.url.exceptions.NoSuchAlgorithmException;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;

import java.math.BigInteger;
import java.security.MessageDigest;

@Component
public class EncrypterAdapter implements EncrypterPort {

    private final String encryptMethod;

    public EncrypterAdapter(@Value("${encrypt.method:MD5}") String encryptMethod) {
        this.encryptMethod = encryptMethod;
    }

    @Override
    public String encrypt(String toBeEncrypted) {
        try {
            MessageDigest messageDigestInstance = getInstance(encryptMethod);

            byte[] messageDigestCalculated = calculateMessageDigest(messageDigestInstance, toBeEncrypted);

            BigInteger signumRepresentation = ConvertArrayIntoSignum(messageDigestCalculated);

            return ConvertMessageIntoHexValue(signumRepresentation);
        }

        catch (java.security.NoSuchAlgorithmException e) {
            throw new NoSuchAlgorithmException("Invalid or unsupported commit algorithm: ", e);
        }
    }

    private MessageDigest getInstance(String encryptMethod) throws java.security.NoSuchAlgorithmException {
        return MessageDigest.getInstance(encryptMethod);
    }

    private byte[] calculateMessageDigest(MessageDigest messageDigestInstance, String toBeEncrypted) {
        return  messageDigestInstance.digest(toBeEncrypted.getBytes());
    }

    private BigInteger ConvertArrayIntoSignum(byte[] messageDigestCalculated) {
        return new BigInteger(1, messageDigestCalculated);
    }

    private String ConvertMessageIntoHexValue(BigInteger signumRepresentation) {
        StringBuilder hashtext = new StringBuilder(signumRepresentation.toString(16));

        while (hashtext.length() < 32) {
            hashtext.insert(0, "0");
        }

        return hashtext.toString();
    }
}
