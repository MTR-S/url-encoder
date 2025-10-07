package com.br.shortener.url.infrastructure.adapters;

import com.br.shortener.url.domain.ports.outbound.SwapperPort;
import org.springframework.stereotype.Component;

import java.util.Random;


import java.util.Random;

@Component
public class SwapperAdapter implements SwapperPort {

    @Override
    public String pickRandomNumbers(String stringWith22Characters, String seedInput, int howManyNumbers) {
        Random random = createRandomInstance(seedInput);

        StringBuilder randomNumbers = new StringBuilder();
        for (int i = 0; i < howManyNumbers; i++) {
            int randomIndex = random.nextInt(stringWith22Characters.length());
            randomNumbers.append(stringWith22Characters.charAt(randomIndex));
        }

        return randomNumbers.toString();
    }

    private static Random createRandomInstance(String seedInput) {
        long seed = seedInput.hashCode();

        return new Random(seed);
    }
}
