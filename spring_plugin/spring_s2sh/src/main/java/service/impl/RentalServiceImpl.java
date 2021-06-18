package service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import po.Rental;
import service.RentalService;
import dao.RentalDao;
@Transactional(propagation=Propagation.REQUIRED,isolation=Isolation.DEFAULT,timeout=5)
@Service("rentalservice")
public class RentalServiceImpl implements RentalService{
	@Autowired
	private RentalDao rentalDao;
	public RentalDao getRentalDao() {
		return rentalDao;
	}

	public void setRentalDao(RentalDao rentalDao) {
		this.rentalDao = rentalDao;
	}

	public List<Rental> getAllRenters() {
		return rentalDao.find("from Rental");
	}

	public List<Rental> getpageRentals(int page, int pagesize) {
		List<Rental> list=rentalDao.findByPage("from Rental", page, pagesize);
		return list;
	}

	public void updaterental(Rental a) {
		rentalDao.update(a);
	}

	public void deleterental(int id) {
		Rental r=rentalDao.get(Rental.class, id);
		rentalDao.delete(r);
	}

	public Rental getRentalByid(int id) {
		Rental r=rentalDao.get(Rental.class, id);
		return r;
	}

	public void addrental(Rental r) {
		rentalDao.save(r);
		
	}

}
