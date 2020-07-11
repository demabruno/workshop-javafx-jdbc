package model.dao;

import java.util.Collection;
import java.util.List;

public interface GenericDao<T> extends Collection<T> {
	void insert (T obj);
	void update (T obj);
	T findById(Integer id);
	List<T> findAll();
	void delete (Integer id);
}
