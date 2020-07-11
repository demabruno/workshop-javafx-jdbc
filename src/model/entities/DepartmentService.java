package model.entities;

import java.util.ArrayList;
import java.util.List;

import model.application.Department;

public class DepartmentService {
	public List<Department> findAll(){
		List<Department> listaDepartamentos = new ArrayList();
		listaDepartamentos.add(new Department(1, "Almoxarifado"));
		listaDepartamentos.add(new Department(2, "Contabilidade"));
		listaDepartamentos.add(new Department(3, "Auditoria"));
		listaDepartamentos.add(new Department(4, "TI"));
		
		return listaDepartamentos;
	}
	
	
}
