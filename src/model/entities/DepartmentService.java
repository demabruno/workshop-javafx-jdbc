package model.entities;

import java.util.ArrayList;
import java.util.List;

import model.application.Department;
import model.dao.DaoFactory;
import model.dao.DepartmentDao;

public class DepartmentService {

	private DepartmentDao dao = DaoFactory.createDepartmentDao();
	
	public List<Department> findAll(){
		return dao.findAll();
	}
	
	
}
