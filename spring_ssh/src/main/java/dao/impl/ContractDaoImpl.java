package dao.impl;

import org.springframework.stereotype.Repository;

import po.Contract;

import common.dao.BaseDaoImpl;

import dao.ContractDao;
@Repository("contractdao")
public class ContractDaoImpl extends BaseDaoImpl<Contract> implements ContractDao{

}
