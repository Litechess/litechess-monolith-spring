package com.trymad.litechess_monolith.livegame.internal.controller.filter;

import java.util.UUID;

public record LiveGameFilter(UUID ownerId, UUID oponentId) {

}
