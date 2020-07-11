package model.entities;

import java.util.ArrayList;
import java.util.List;

import model.application.Seller;
import model.dao.DaoFactory;
import model.dao.SellerDao;

public class SellerService {

	private SellerDao dao = DaoFactory.createSellerDao();
	
	public List<Seller> findAll(){
		return dao.findAll();
	}
	
	public void saveOrUpdate(Seller Seller) {
		if(Seller.getId() == null) {
			dao.insert(Seller);
		}
		else
		{
			dao.update(Seller);
		}
	}
	
	public void removeEntity(Seller seller) {
		dao.delete(seller.getId());
	}
	
}
