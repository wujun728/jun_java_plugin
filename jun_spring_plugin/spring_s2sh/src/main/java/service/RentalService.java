package service;

import java.util.List;
import po.Rental;

public interface RentalService {
	List<Rental> getAllRenters();
	List<Rental> getpageRentals(int page,int pagesize);
	void updaterental(Rental a);
	void deleterental(int id);
	Rental getRentalByid(int id);
	void addrental(Rental r);
}
