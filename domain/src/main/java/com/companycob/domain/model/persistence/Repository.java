package com.companycob.domain.model.persistence;

import java.util.List;
import java.util.Optional;

public interface Repository<Type, Key> {

	Type save(Type entity);
	List<Type> saveAll(List<Type> entities);
	void remove(Type entity);
	Optional<Type> findById(Key key);
	List<Type> findAll();
	
}
