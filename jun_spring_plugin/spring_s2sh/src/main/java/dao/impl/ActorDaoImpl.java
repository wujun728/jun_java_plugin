package dao.impl;

import org.springframework.stereotype.Repository;

import po.Actor;
import common.dao.BaseDaoImpl;
import dao.ActorDao;
@Repository("actorDao")
public class ActorDaoImpl extends BaseDaoImpl<Actor> implements ActorDao{

}
