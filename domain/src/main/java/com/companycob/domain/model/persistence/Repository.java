package com.companycob.domain.model.persistence;

import com.companycob.domain.exception.CalcTypeNotFoundException;

import java.util.List;
import java.util.Optional;

public interface Repository<Type, Key> {

	Type save(Type entity) throws CalcTypeNotFoundException;
	List<Type> saveAll(List<Type> entities);
	void remove(Type entity);
	Optional<Type> findById(Key key);
	List<Type> findAll();
	
}
