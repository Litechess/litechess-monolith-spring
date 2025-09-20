package com.trymad.litechess_monolith.shared.mapper;

import java.util.List;

public interface ToEntityMapper<E, D> {
	
	E toEntity(D dto);

	default List<E> toEntity(List<D> dtos) {
		return dtos.stream().map(this::toEntity).toList();
	}

	void updateEntityFromDto(E entity, D dto);

}	
