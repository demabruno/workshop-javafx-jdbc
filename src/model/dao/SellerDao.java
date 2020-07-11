package model.dao;

import java.util.List;

import model.application.Department;
import model.application.Seller;

public interface SellerDao {
	void insert (Seller obj);
	void update (Seller obj);
	Seller findById(Integer id);
	List<Seller> findAll();
	void delete (Integer id);
	List<Seller> findByDepartment(Department department);
}
