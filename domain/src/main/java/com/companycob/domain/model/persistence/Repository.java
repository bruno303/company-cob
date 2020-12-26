package com.companycob.domain.model.persistence;

import java.util.List;
import java.util.Optional;

public interface Repository<T, K> {

	T save(T entity);

	List<T> saveAll(List<T> entities);

	void remove(T entity);

	Optional<T> findById(K key);

	List<T> findAll();

	void removeAll();

}
