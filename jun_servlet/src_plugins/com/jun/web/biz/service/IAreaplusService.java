package com.jun.web.biz.service;

import java.util.List;

import com.jun.web.biz.beans.Areaplus;

public interface IAreaplusService {

	public List<Areaplus> listByUpid(int upid) ;
	public List<Areaplus> listByKeyWord(String kw) ;
}
