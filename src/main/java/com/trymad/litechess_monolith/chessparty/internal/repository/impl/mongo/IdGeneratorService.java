package com.trymad.litechess_monolith.chessparty.internal.repository.impl.mongo;

import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Random;

@Service
public class IdGeneratorService {

    private static final String ALPHABET = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static final int ID_LENGTH = 8;
    private static final int MAX_ATTEMPTS = 10;

    private final MongoOperations mongoOperations;
    private final Random random = new SecureRandom();

    private final String COLLECTION_NAME = "chessParty";

    public IdGeneratorService(MongoOperations mongoOperations) {
        this.mongoOperations = mongoOperations;
    }

    public String generateUniqueId() {
        for (int attempt = 0; attempt < MAX_ATTEMPTS; attempt++) {
            String candidate = randomId();
            boolean exists = mongoOperations.exists(
                Query.query(Criteria.where("_id").is(candidate)),
                COLLECTION_NAME
            );
            if (!exists) {
                return candidate;
            }
        }
        throw new RuntimeException("Не удалось сгенерировать уникальный ID после " + MAX_ATTEMPTS + " попыток");
    }

    private String randomId() {
        StringBuilder sb = new StringBuilder(ID_LENGTH);
        for (int i = 0; i < ID_LENGTH; i++) {
            sb.append(ALPHABET.charAt(random.nextInt(ALPHABET.length())));
        }
        return sb.toString();
    }
}
