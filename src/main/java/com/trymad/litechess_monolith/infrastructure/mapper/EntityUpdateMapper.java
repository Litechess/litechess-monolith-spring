package com.trymad.litechess_monolith.infrastructure.mapper;

public interface EntityUpdateMapper<E, D> {
	
	void updateFromDto(E entity, D dto);

}
