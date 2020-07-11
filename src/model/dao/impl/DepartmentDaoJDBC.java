package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import db.DB;
import db.DBException;
import model.application.Department;
import model.application.Seller;
import model.dao.DepartmentDao;
import model.dao.SellerDao;

public class DepartmentDaoJDBC implements DepartmentDao{

	private Connection conn;
	
	public DepartmentDaoJDBC(Connection conn) {
		this.conn = conn;
	}
	
	@Override
	public void insert(Department obj) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try
		{   
			st = conn.prepareStatement("INSERT INTO DEPARTMENT " +
					"(Name) " +
					"VALUES " +
					"(?)",
					Statement.RETURN_GENERATED_KEYS);
			
			st.setString(1, obj.getName());
							
			int rowsAffected = st.executeUpdate();
			if (rowsAffected > 0)
			{
				rs = st.getGeneratedKeys();
				while(rs.next()) {
					obj.setId(rs.getInt(1));
				}
			}
			else {
				throw new DBException("Nenhum registro afetado!!");
			}
		}
		catch(SQLException ex) {
			ex.printStackTrace();
		}
		finally{
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

	@Override
	public void update(Department obj) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try
		{   
			st = conn.prepareStatement("UPDATE DEPARTMENT " + 
					"SET Name = ? " + 
					"WHERE Id = ?");
			
			st.setString(1, obj.getName());
			st.setInt(2, obj.getId());
							
			st.executeUpdate();
			
		}
		catch(SQLException ex) {
			ex.printStackTrace();
		}
		finally{
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
		
	}
	/*
	@Override
	public Seller findById(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement("SELECT seller.*,department.Name as DepName " + 
					"FROM seller INNER JOIN department " + 
					"ON seller.DepartmentId = department.Id " + 
					"WHERE seller.Id = ?");
			st.setInt(1, id);
			rs = st.executeQuery();
			if (rs.next()) {	
				Department dep = instantiateDepartment(rs);
				Seller seller = instantiateSeller(rs, dep);	
				
				return seller;
			}
			return null;
		}
		catch(SQLException ex) {
			throw new DBException(ex.getMessage());
		}
		finally{
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}
	
	private Department instantiateDepartment(ResultSet rs) throws SQLException{
		Department dep = new Department();
			dep.setId(rs.getInt("DepartmentId"));
			dep.setName(rs.getString("Name"));
		return dep;
	}
	
	private Seller instantiateSeller(ResultSet rs, Department dep) throws SQLException {
		Seller seller = new Seller();
			seller.setId(rs.getInt("Id"));
			seller.setName(rs.getString("Name"));
			seller.setEmail(rs.getString("Email"));
			seller.setBaseSalary(rs.getDouble("baseSalary"));
			seller.setBirthDate(rs.getDate("birthDate"));
			seller.setDepartent(dep);		
		return seller;
	}
*/
	@Override
	public List<Department> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement("SELECT * FROM department " + 
					" ORDER BY Id");
			
			List<Department> listaDeRetorno = new ArrayList();
			rs = st.executeQuery();
			while (rs.next()) {	
				
				Department dep = instantiateDepartment(rs);
				listaDeRetorno.add(dep);
			}
			return listaDeRetorno;
		}
		catch(SQLException ex) {
			throw new DBException(ex.getMessage());
		}
		finally{
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}
/*
	@Override
	public void delete(Integer id) {
		PreparedStatement st = null;
		try
		{   
			st = conn.prepareStatement("DELETE FROM seller " + 
					"WHERE Id = ?");
			
			st.setInt(1, id);

			st.executeUpdate();
			
		}
		catch(SQLException ex) {
			ex.printStackTrace();
		}
		finally{
			DB.closeStatement(st);
		}
		
	}

	@Override
	public List<Seller> findByDepartment(Department department) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement("SELECT seller.*,department.Name as DepName " + 
					"FROM seller INNER JOIN department " + 
					"ON seller.DepartmentId = department.Id " + 
					"WHERE DepartmentId = ? " + 
					"ORDER BY Name");
			st.setInt(1, department.getId());
			List<Seller> listaDeRetorno = new ArrayList();
			Map<Integer, Department> mapDepartment =new HashMap<Integer, Department>();
			rs = st.executeQuery();
			while (rs.next()) {	
				
				Department dep = mapDepartment.get(rs.getInt("DepartmentId"));
				if  (dep == null) {
					dep = instantiateDepartment(rs);
					mapDepartment.put(rs.getInt("DepartmentId"), dep);
				}
				Seller seller = instantiateSeller(rs, dep);	
				listaDeRetorno.add(seller);
			}
			return listaDeRetorno;
		}
		catch(SQLException ex) {
			throw new DBException(ex.getMessage());
		}
		finally{
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}*/
	
	private Department instantiateDepartment(ResultSet rs) throws SQLException{
		Department dep = new Department();
			dep.setId(rs.getInt("Id"));
			dep.setName(rs.getString("Name"));
		return dep;
	}

	@Override
	public Department findById(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void delete(Integer id) {
		// TODO Auto-generated method stub
		
	}

}
