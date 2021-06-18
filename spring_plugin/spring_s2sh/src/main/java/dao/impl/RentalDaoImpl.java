package dao.impl;

import org.springframework.stereotype.Repository;

import po.Rental;
import common.dao.BaseDaoImpl;
import dao.RentalDao;

@Repository("rentalDao")
public class RentalDaoImpl extends BaseDaoImpl<Rental> implements RentalDao{

}
