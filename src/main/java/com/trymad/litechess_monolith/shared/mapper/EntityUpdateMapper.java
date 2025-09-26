package com.trymad.litechess_monolith.shared.mapper;

public interface EntityUpdateMapper<E, D> {
	
	void updateFromDto(E entity, D dto);

}
