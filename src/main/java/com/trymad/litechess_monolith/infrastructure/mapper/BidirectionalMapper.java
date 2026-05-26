package com.trymad.litechess_monolith.infrastructure.mapper;

public interface BidirectionalMapper<E, D> extends ToDtoMapper<E, D>, ToEntityMapper<E,D> {


}
