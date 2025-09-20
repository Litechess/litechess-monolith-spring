package com.trymad.litechess_monolith.shared.mapper;

import java.util.List;

public interface ToDtoMapper<E, D> {

	D toDto(E entity);

	default List<D> toDto(List<E> entity) {
		return entity.stream().map(this::toDto).toList();
	};
}
