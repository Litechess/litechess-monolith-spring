package com.trymad.litechess_monolith.matchmaking.internal.repository.impl;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.springframework.stereotype.Component;

import com.trymad.litechess_monolith.matchmaking.internal.model.Challenge;
import com.trymad.litechess_monolith.matchmaking.internal.repository.ChallengeRepository;

@Component
public class InMemoryChallengeRepository implements ChallengeRepository {

    private final ConcurrentMap<String, Challenge> storage = new ConcurrentHashMap<>();

    @Override
    public Optional<Challenge> findById(String id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public Challenge save(Challenge challenge) {
        storage.put(challenge.getId(), challenge);
        return challenge;
    }

    @Override
    public boolean exists(String id) {
        return storage.containsKey(id);
    }

    @Override
    public void delete(String id) {
        storage.remove(id);
    }
}
