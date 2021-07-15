package tos.assetinventory.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;

import tos.assetinventory.service.DataService;
import tos.bean.Equipmentinventory;
import tos.bean.MaterielInventory;
import tos.common.config.SystemConfig;
import tos.common.util.SerializeTools;
import tos.common.util.WebClientUtil;

@Service("dataService")
public class DataServiceImpl implements DataService{

	@Resource
	public SystemConfig systemConfig;
	
	@Override
	public List<MaterielInventory> getAssetinventoryList(String json) {
		String result=WebClientUtil.dataServicePost(json, "getAssetinventoryList", systemConfig.getWebserviceUrl());
		JSONObject jsonReuslt=JSONObject.parseObject(result);
		List<Object> listMap=jsonReuslt.getJSONArray("Data");
		List<MaterielInventory> assetinventoryList=getForumList(listMap);
		return assetinventoryList;
	}

	
	//格式化资产清单列表
	@SuppressWarnings({ "unused", "unchecked" })
	private List<MaterielInventory> getForumList(List<Object> listMap) {
		List<MaterielInventory> listForum=new ArrayList<MaterielInventory>();
		for(int i =0;i<listMap.size();i++){
			MaterielInventory assetinventory=new MaterielInventory();
			if(listMap.get(i)!=null){
				assetinventory=(MaterielInventory) SerializeTools.getObjectForJson((Map<String, Object>) listMap.get(i), assetinventory);
				listForum.add(assetinventory);
			}
		}
		return listForum;
	}


	/**
	 */
	@Override
	public List<Object> getEnergyList(String jsonString) {
		String result=WebClientUtil.dataServicePost(jsonString, "getEnergyList", systemConfig.getWebserviceUrl());
		JSONObject jsonReuslt=JSONObject.parseObject(result);
		List<Object> listMap=jsonReuslt.getJSONArray("Data");
		List<Object> assetinventoryList=getForumList1(listMap);
		return assetinventoryList;
	}
	
	//格式化资产清单列表
	@SuppressWarnings({ "unused", "unchecked" })
	private List<Object> getForumList1(List<Object> listMap) {
		List<Object> listForum=new ArrayList<Object>();
		for(int i =0;i<listMap.size();i++){
			Object assetinventory=new Object();
			if(listMap.get(i)!=null){
				assetinventory= SerializeTools.getObjectForJson((Map<String, Object>) listMap.get(i), assetinventory);
				listForum.add(assetinventory);
			}
		}
		return listForum;
	}

	/**
	 * 获取设备清单表
	 */
	@Override
	public List<Equipmentinventory> getEquipmentList(String jsonString) {
		String result=WebClientUtil.dataServicePost(jsonString, "getEquipmentList", systemConfig.getWebserviceUrl());
		JSONObject jsonReuslt=JSONObject.parseObject(result);
		List<Object> listMap=jsonReuslt.getJSONArray("Data");
		List<Equipmentinventory> equipmentList=getForumList2(listMap);
		return equipmentList;
	}


	private List<Equipmentinventory> getForumList2(List<Object> listMap) {
		List<Equipmentinventory> listForum=new ArrayList<Equipmentinventory>();
		for(int i =0;i<listMap.size();i++){
			Equipmentinventory equipment=new Equipmentinventory();
			if(listMap.get(i)!=null){
				equipment=(Equipmentinventory) SerializeTools.getObjectForJson((Map<String, Object>) listMap.get(i), equipment);
				listForum.add(equipment);
			}
		}
		return listForum;
	}
	
	


}
