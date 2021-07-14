package cn.ipanel.apps.portalBackOffice.util;

import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import cn.ipanel.apps.portalBackOffice.dao.IPortalSubstanceDao;
import cn.ipanel.apps.portalBackOffice.dao.IResourceDataDao;
import cn.ipanel.apps.portalBackOffice.dao.IResourcesDao;
import cn.ipanel.apps.portalBackOffice.domain.PortalSubstance;
import cn.ipanel.apps.portalBackOffice.domain.Resources;
import cn.ipanel.apps.portalBackOffice.domain.ResourcesDate;
import cn.ipanel.apps.portalBackOffice.jibx.XmlParse;
import cn.ipanel.apps.portalBackOffice.jibx.pojo.Defaultstruct;
import cn.ipanel.apps.portalBackOffice.jibx.pojo.MenuStruct;
import cn.ipanel.apps.portalBackOffice.jibx.pojo.PortalApp;
import cn.ipanel.apps.portalBackOffice.jibx.pojo.PortalStruct;
import cn.ipanel.apps.portalBackOffice.jibx.pojo.Position;
import cn.ipanel.apps.portalBackOffice.jibx.pojo.ResourceData;
import cn.ipanel.apps.portalBackOffice.jibx.pojo.Station;
import cn.ipanel.apps.portalBackOffice.jibx.pojo.Defaultstruct.DefaultstructInner;
import cn.ipanel.apps.portalBackOffice.jibx.pojo.Defaultstruct.DefaultstructInner.Images;
import cn.ipanel.apps.portalBackOffice.jibx.pojo.Defaultstruct.DefaultstructInner.Images.Image;
import cn.ipanel.apps.portalBackOffice.jibx.pojo.MenuStruct.MenuStructInner;
import cn.ipanel.apps.portalBackOffice.jibx.pojo.MenuStruct.MenuStructInner.Menu;
import cn.ipanel.apps.portalBackOffice.jibx.pojo.PortalApp.ResourceList;
import cn.ipanel.apps.portalBackOffice.jibx.pojo.PortalApp.ResourceList.ResourceItem;
import cn.ipanel.apps.portalBackOffice.jibx.pojo.PortalStruct.PortalStructInner;
import cn.ipanel.apps.portalBackOffice.jibx.pojo.PortalStruct.PortalStructInner.Resource;
import cn.ipanel.apps.portalBackOffice.jibx.pojo.ResourceData.Item;
import cn.ipanel.apps.portalBackOffice.service.IPoserService;
import cn.ipanel.apps.portalBackOffice.service.impl.RelationServiceImpl;
import cn.ipanel.apps.portalBackOffice.service.serviceBean.PictureBean;
import cn.ipanel.apps.portalBackOffice.util.Base64Fiend;
import cn.ipanel.apps.portalBackOffice.util.CommonsFiend;
import cn.ipanel.apps.portalBackOffice.util.PorserUtil;
import cn.ipanel.apps.portalBackOffice.util.ZipExtractor;
import cn.ipanel.apps.portalBackOffice.util.md5.MD5;

public class EditUtil {
		

	private static Logger logger = Logger.getLogger(EditUtil.class);
	
	IResourcesDao resourceDao ;
	
	IResourceDataDao resDataDao ;
	
	/**
	 * 更新portalAppxml
	 * 
	 * @author tianwl
	 * @param portalId
	 * @param positionId
	 * @return
	 */
	public static String updataPortalSubstance(int portalId,String resId,String resourceId,IPortalSubstanceDao psDao){
		
		if((resourceId == null) || (resourceId.equals(""))){
			resourceId = CommonsFiend.getUniqueId(10);
		}
		PortalSubstance ps = psDao.selectPortalSubstance(portalId);
		String portalAppXml = ps.getPortalAppXML();
		PortalApp portalApp = (PortalApp) XmlParse.parse(portalAppXml, PortalApp.class);
		 List<ResourceList> resList = portalApp.getResourceLists();
		 Iterator<ResourceList> iterator = resList.iterator();
		 while(iterator != null && iterator.hasNext()){
			 ResourceList resource = iterator.next();
			  List<ResourceItem> resItem =  resource.getResourceItems();
			  Iterator<ResourceItem> iter = resItem.iterator();
			  while(iter != null && iter.hasNext()){
				  ResourceItem item = iter.next();
				 if(resId == item.getResourceId()){
					 item.setResourceId(resourceId);
				 }
			  }
		 psDao.updatePortalSubstance(ps);
		 }
		return resourceId;
	}
	
	/**
	 * 生成resourceData
	 * @author tianwl
	 * @param resourceDataId
	 * @param data
	 * @param type
	 */
	public static void updataResData(String resourceDataId,String data,String type,IResourceDataDao resDataDao ){
		ResourcesDate resData = new ResourcesDate();
		resData.setCreateTime(CommonsFiend.stringToDate(CommonsFiend.getCurrentDateTime()));
		resData.setData(data);
		resData.setVersion("v1.0");
		resData.setResourceDataId(resourceDataId);
		resData.setType(type);
		resData.setUser(null);
		resDataDao.addResourcesDate(resData);
	}
	
	/**
	 * 根据key 得到dataId
	 * 
	 * @author tianwl
	 * @param resourceId
	 * @param key
	 * @return
	 */
//	public static String getResourceData(String resourceDataXml,String key,IResourcesDao resourceDao ){
//		String data = "";
//		//String resourceDataXml = new String(Base64Fiend.decode(resourceDao.getResourceDataXml(resourceId)));
////		logger.info(resourceDataXml);
//		ResourceData resourceData = (ResourceData) XmlParse.parse(resourceDataXml, ResourceData.class);
//		List<Item> itemList = resourceData.getItems();
//		Iterator<Item> iter = itemList.iterator();
//		while(iter != null && iter.hasNext()){
//			Item it = iter.next();
//			if((it.getKey() == key) || (it.getKey().equals(key))){
//				data = it.getDataId();
//				break;
//			}
//		}
//		return data;
//	}
	
	/**
	 *根据resourceDataXml 和key来解析出
	 * @author tianwl
	 * @param resourceDataXml 此数据没有经过解析
	 * @param key
	 * @return
	 */
	public static String getResourceData(String resourceDataXml,String key){
		String data = "";
		resourceDataXml = new String(Base64Fiend.decodeToString(resourceDataXml));
		ResourceData resourceData = (ResourceData) XmlParse.parse(resourceDataXml, ResourceData.class);
		List<Item> itemList = resourceData.getItems();
		Iterator<Item> item = itemList.iterator();
		while(item != null && item.hasNext()){
			Item it = item.next();
			if((it.getKey().startsWith(key)) || (it.getKey() == key) || (it.getKey().equals(key))){
				data = it.getDataId();
				break;
			}
		}
		
		return data;
	}
	/**
	 * 根据positionId 得到resourceId
	 * @author tianwl
	 * @param ps
	 * @param positionId
	 * @return
	 */
	public static String getResourceId(PortalSubstance ps,String positionId,String type){
		String resourceId = "";
		String portalAppXml = new String(Base64Fiend.decode(ps.getPortalAppXML()));
//		logger.info(portalAppXml);
		PortalApp portalApp = (PortalApp) XmlParse.parse(portalAppXml, PortalApp.class);
		List<ResourceList> resourceList = portalApp.getResourceLists();
		Iterator<ResourceList> iter = resourceList.iterator();
		while(iter != null && iter.hasNext()){
			ResourceList resList = iter.next();
			List<ResourceItem> itemList = resList.getResourceItems();
			Iterator<ResourceItem> it = itemList.iterator();
			while(it != null && it.hasNext()){
				ResourceItem item = it.next();
				if(item.getPositionId().equals(positionId)){
					if((item.getType().equals(type)) || (item.getType() == type)){
						return item.getResourceId();
					}
				}
			}
		}
		return resourceId;
	}
	/**
	 * 得到resourceId 的list 列表，因为 一级菜单会对应多个 resourceId
	 * @author tianwl
	 * @param ps
	 * @param positionId
	 * @return
	 */
	public static List<String> getResourceIdList(PortalSubstance ps,String positionId,String type){
		List<String> resourceIdList = new ArrayList<String>();
		String portalAppXml = new String(Base64Fiend.decode(ps.getPortalAppXML()));
		PortalApp portalApp = (PortalApp) XmlParse.parse(portalAppXml, PortalApp.class);
		List<ResourceList> resourceList = portalApp.getResourceLists();
		Iterator<ResourceList> iter = resourceList.iterator();
		while(iter != null && iter.hasNext()){
			ResourceList resList = iter.next();
			List<ResourceItem> itemList = resList.getResourceItems();
			Iterator<ResourceItem> it = itemList.iterator();
			while(it != null && it.hasNext()){
				ResourceItem item = it.next();
				if(item.getPositionId().equals(positionId)){
					if(item.getType().startsWith(type)){
						resourceIdList.add(item.getResourceId());
					}
				}
			}
		}
		return resourceIdList;
	}
	/**
	 * 得到所有的一级菜单的positionId
	 * @author tianwl
	 * @param ps
	 * @return
	 */
	public static List<String> getOnePositionId(PortalSubstance ps){
	
		List<String> list = new ArrayList<String>();
		
		String structXml = new String(Base64Fiend.decode(ps.getPortalStructXML()));
		PortalStruct portalStruct = (PortalStruct) XmlParse.parse(structXml, PortalStruct.class);
		List<PortalStructInner> posList = portalStruct.getPortalStructs();
		Iterator<PortalStructInner> iterator = posList.iterator();
		while(iterator != null && iterator.hasNext()){
			PortalStructInner posi = iterator.next();
			Resource resource = posi.getResource();
			List<Position> positionList = resource.getPositions();
			Iterator<Position> iter = positionList.iterator();
			while(iter != null && iter.hasNext()){
				Position position = iter.next();
				String positionId = position.getId();
				list.add(positionId);
			}
		}
		return list;
	}
	
	/**
	 * 得到所有的一级菜单和二级菜单的positionId
	 * @author tianwl
	 * @param ps
	 * @return
	 */
	public static List<String> getPositionId(PortalSubstance ps){
	
		List<String> list = new ArrayList<String>();
		
		String structXml = new String(Base64Fiend.decode(ps.getPortalStructXML()));
		PortalStruct portalStruct = (PortalStruct) XmlParse.parse(structXml, PortalStruct.class);
		List<PortalStructInner> posList = portalStruct.getPortalStructs();
		Iterator<PortalStructInner> iterator = posList.iterator();
		while(iterator != null && iterator.hasNext()){
			PortalStructInner posi = iterator.next();
			Resource resource = posi.getResource();
			List<Position> positionList = resource.getPositions();
			Iterator<Position> iter = positionList.iterator();
			while(iter != null && iter.hasNext()){
				Position position = iter.next();
				String positionId = position.getId();
				list.add(positionId);
				//得到二级菜单
				List<Position> pList = resource.getPositions();
				Iterator<Position> it = pList.iterator();
				while(it != null && it.hasNext()){
					Position pos = it.next();
					String posId = pos.getId();
					if(posId != null){
						list.add(posId);
					}
				}
			}
		}
		return list;
	}
	
	/**
	 * 得到所有的一级菜单和二级菜单的positionId
	 * @author tianwl
	 * @param ps
	 * @return
	 */
	public static List<String> getTwoPositionId(PortalSubstance ps){
	
		List<String> list = new ArrayList<String>();
		
		String structXml = new String(Base64Fiend.decode(ps.getPortalStructXML()));
		PortalStruct portalStruct = (PortalStruct) XmlParse.parse(structXml, PortalStruct.class);
		List<PortalStructInner> posList = portalStruct.getPortalStructs();
		Iterator<PortalStructInner> iterator = posList.iterator();
		while(iterator != null && iterator.hasNext()){
			PortalStructInner posi = iterator.next();
			Resource resource = posi.getResource();
			List<Position> positionList = resource.getPositions();
			Iterator<Position> iter = positionList.iterator();
			while(iter != null && iter.hasNext()){
				Position position = iter.next();
				//得到二级菜单
				List<Position> pList = position.getPositions();
				Iterator<Position> it = pList.iterator();
				while(it != null && it.hasNext()){
					Position pos = it.next();
					String posId = pos.getId();
					list.add(posId);
				}
			}
		}
		return list;
	}
	
	/**
	 * 根据positionid,得到所有的对应的资源Id
	 * @author tianwl
	 * @param ps
	 * @param positionId
	 * @return
	 */
	public static List<String> getResourceIdByPositionId(PortalSubstance ps,String positionId,String key){
		List<String> list = new ArrayList<String>();
		String portalAppXml = new String(Base64Fiend.decode(ps.getPortalAppXML()));
		PortalApp portalApp = (PortalApp) XmlParse.parse(portalAppXml,PortalApp.class);
		List<ResourceList> resList = portalApp.getResourceLists();
		Iterator<ResourceList> iterator = resList.iterator();
		while(iterator != null && iterator.hasNext()){
			ResourceList res = iterator.next();
			List<ResourceItem> itemList = res.getResourceItems();
			Iterator<ResourceItem> iter = itemList.iterator();
			while(iter != null && iter.hasNext()){
				ResourceItem item = iter.next();
				String posId = item.getPositionId();
				if(item.getType().startsWith(key)){
					if((posId.equals(positionId)) || (posId == positionId)){
						list.add(item.getResourceId());
					}
				}
			}
		}
		
		return list ;
	}
	
	/**
	 * 根据一级菜单的index得到positionId
	 * @author tianwl
	 * @param portalStructXml
	 * @param index
	 * @return
	 */
	public static String getOnePositionIdByIndex(String portalStructXml,String index){
		portalStructXml = Base64Fiend.decodeToString(portalStructXml);
		PortalStruct ps = (PortalStruct) XmlParse.parse(portalStructXml, PortalStruct.class);
		List<PortalStructInner> list = ps.getPortalStructs();
		Iterator<PortalStructInner> iterator = list.iterator();
		while(iterator != null && iterator.hasNext()){
			PortalStructInner inner = iterator.next();
			Resource resource = inner.getResource();
			List<Position> posList = resource.getPositions();
			Iterator<Position> it = posList.iterator();
			while(it != null && it.hasNext()){
				Position pos = it.next();
				if(pos.getIndex().equals(index)){
					return pos.getId();
				}
			}
		}
		return "";
	}
	
	/**
	 * 有一级菜单的位置得到resourceId
	 * @author tianwl
	 * @param portalStructXml
	 * @param portaAppXml
	 * @param onePosition
	 * @param position
	 * @return
	 */
	public static String getResourceId(String portalStructXml,String portaAppXml,String onePosition,String position){
		String haibao = position; //liuyh 修改
		/*if(position.equals("left")){
			haibao = "haibao0";
		}else if(position.equals("right")){
			haibao = "haibao1";
		}*/
		String positionId = EditUtil.getOnePositionIdByIndex(portalStructXml, onePosition);
		String portalAppXml = new String(Base64Fiend.decode(portaAppXml));
		
		PortalApp portalApp = (PortalApp) XmlParse.parse(portalAppXml,PortalApp.class);
		List<ResourceList> resList = portalApp.getResourceLists();
		Iterator<ResourceList> iterator = resList.iterator();
		while(iterator != null && iterator.hasNext()){
			ResourceList res = iterator.next();
			List<ResourceItem> itemList = res.getResourceItems();
			Iterator<ResourceItem> iter = itemList.iterator();
			while(iter != null && iter.hasNext()){
				ResourceItem item = iter.next();
				String posId = item.getPositionId();
				if((posId.equals(positionId)) || (posId == positionId)){
					if(item.getType().equals(haibao)){
						return item.getResourceId();
					}
				}
			}
		}
		return null ;
	}
	
	/**
	 * 有自己拼的字符串等到相应的positionId或者相关的字符串
	 * @author tianwl
	 * @param str
	 * @param portalStructXml
	 * @return
	 */
	public static String getPositionId(String str,String portalStructXml){
		String positionId = null ;
		String[] st = str.split(",");
		String index = st[0];
		portalStructXml = Base64Fiend.decodeToString(portalStructXml);
		PortalStruct ps = (PortalStruct) XmlParse.parse(portalStructXml, PortalStruct.class);
		if(st.length == 1){
			
			List<PortalStructInner> list = ps.getPortalStructs();
			Iterator<PortalStructInner> iterator = list.iterator();
			while(iterator != null && iterator.hasNext()){
				PortalStructInner inner = iterator.next();
				Resource resource = inner.getResource();
				List<Position> posList = resource.getPositions();
				Iterator<Position> it = posList.iterator();
				while(it != null && it.hasNext()){
					Position pos = it.next();
					if(pos.getIndex().equals(index)){
						return pos.getId();
					}
				}
			}
		}
		if(st.length == 2){
			String index2 = st[1];
			if(index2 != null && index2.startsWith("haibao")){ //leigq修改
//			if(index2.equals("left") || index2.equals("right")){
				List<PortalStructInner> list = ps.getPortalStructs();
				Iterator<PortalStructInner> iterator = list.iterator();
				while(iterator != null && iterator.hasNext()){
					PortalStructInner inner = iterator.next();
					Resource resource = inner.getResource();
					List<Position> posList = resource.getPositions();
					Iterator<Position> it = posList.iterator();
					while(it != null && it.hasNext()){
						Position pos = it.next();
						if(pos.getIndex().equals(index)){
							return (pos.getId() + index2);
						}
					}
				}
			}else {
				List<PortalStructInner> list = ps.getPortalStructs();
				Iterator<PortalStructInner> iterator = list.iterator();
				while(iterator != null && iterator.hasNext()){
					PortalStructInner inner = iterator.next();
					Resource resource = inner.getResource();
					List<Position> posList = resource.getPositions();
					Iterator<Position> it = posList.iterator();
					while(it != null && it.hasNext()){
						Position pos = it.next();
						if(pos.getIndex().equals(index)){
							List<Position> pList = pos.getPositions();
							Iterator<Position> ite = pList.iterator();
							while(ite != null && ite.hasNext()){
								Position p = ite.next();
								if(p.getIndex().equals(index2)){
									return p.getId();
								}
							}
							
						}
					}
				}
			} 
		}
		return positionId ;
	}
	/**
	 * 得到海报走马灯的resourceId
	 * @author tianwl
	 * @param portalAppXml
	 * @param positionId
	 * @return
	 */
	public static String getPosId(String portalAppXml,String positionId,String position){
		
		String resourceId = EditUtil.getResourceId(portalAppXml, positionId,position);
		portalAppXml = new String(Base64Fiend.decode(portalAppXml));
		
		PortalApp portalApp = (PortalApp) XmlParse.parse(portalAppXml,PortalApp.class);
		List<ResourceList> resList = portalApp.getResourceLists();
		Iterator<ResourceList> iterator = resList.iterator();
		while(iterator != null && iterator.hasNext()){
			ResourceList res = iterator.next();
			List<ResourceItem> itemList = res.getResourceItems();
			Iterator<ResourceItem> iter = itemList.iterator();
			while(iter != null && iter.hasNext()){
				ResourceItem item = iter.next();
				String posId = item.getPositionId();
				if((posId.equals(resourceId)) || (posId == resourceId)){
					if(item.getType().startsWith("marquee")){
						return posId;
					}
				}
			}
		}
		return "";
	}
	
	public static String getResourceId(String portalAppXml,String positionId,String position){
		String haibao = null;
		
/*		if(position.equals("left")){
			haibao = "haibao0";
		}else if(position.equals("right")){
			haibao = "haibao1";
		}
*/
		haibao = position;  //leigq 修改
		
		portalAppXml = new String(Base64Fiend.decode(portalAppXml));
		
		PortalApp portalApp = (PortalApp) XmlParse.parse(portalAppXml,PortalApp.class);
		List<ResourceList> resList = portalApp.getResourceLists();
		Iterator<ResourceList> iterator = resList.iterator();
		while(iterator != null && iterator.hasNext()){
			ResourceList res = iterator.next();
			List<ResourceItem> itemList = res.getResourceItems();
			Iterator<ResourceItem> iter = itemList.iterator();
			while(iter != null && iter.hasNext()){
				ResourceItem item = iter.next();
				String posId = item.getPositionId();
				if(positionId.startsWith(posId)){
					if(item.getType().startsWith(haibao)){
						return item.getResourceId();
					}
				}
			}
		}
		return "";
	}
	
	/**
	 * 有二级菜单的resourceId和portalAppxml 得到二级菜单的positionid
	 * @author tianwl
	 * @param portalAppXml
	 * @param resourceId
	 * @return
	 */
	public static String getTwoPositionId(String portalAppXml,String resourceId){
		String onePositionId = "";
		PortalApp pa = (PortalApp) XmlParse.parse(Base64Fiend.decodeToString(portalAppXml),PortalApp.class);
		List<ResourceList> list = pa.getResourceLists();
		Iterator<ResourceList> iterator = list.iterator();
		while(iterator != null && iterator.hasNext()){
			ResourceList res = iterator.next();
			List<ResourceItem> itemList = res.getResourceItems();
			Iterator<ResourceItem> iter = itemList.iterator();
			while(iter != null && iter.hasNext()){
				ResourceItem item = iter.next();
				String resId = item.getResourceId();
				if(resId.equals(resourceId)){
					if(item.getType().startsWith("portal")){
						return item.getPositionId();
					}
				}
			}
		}
		return "";
	}
	/**
	 * 有二级菜单的positionId 得到一级菜单的positionId
	 * @author tianwl
	 * @param portalStructXml
	 * @param TwoPositionId
	 * @return
	 */
	public static String getOnePositionId(String portalStructXml,String TwoPositionId){
		portalStructXml = Base64Fiend.decodeToString(portalStructXml);
		PortalStruct ps = (PortalStruct) XmlParse.parse(portalStructXml, PortalStruct.class);
		List<PortalStructInner> list = ps.getPortalStructs();
		Iterator<PortalStructInner> iterator = list.iterator();
		while(iterator != null && iterator.hasNext()){
			PortalStructInner inner = iterator.next();
			Resource resource = inner.getResource();
			List<Position> posList = resource.getPositions();
			Iterator<Position> it = posList.iterator();
			while(it != null && it.hasNext()){
				Position pos = it.next();
				List<Position> pList = pos.getPositions();
				Iterator<Position> ite = pList.iterator();
				while(ite != null && ite.hasNext()){
					Position p = ite.next();
					if(p.getId().equals(TwoPositionId)){
						return pos.getId();
					}
				}
			}
		}
		return "";
	}
	
	/**
	 * 得到一二级栏目关联应用的字符串，避免应为空格等其他内容引起的结构不关联
	 * @author tianwl
	 * @param menustructXml
	 * @return
	 */
	public static String getMenuString(String menustructXml){
		StringBuffer menuStr = new StringBuffer();
		StationComparator com = new StationComparator();
		
		menustructXml = Base64Fiend.decodeToString(menustructXml);
		MenuStruct ms = (MenuStruct) XmlParse.parse(menustructXml, MenuStruct.class);
		List<MenuStructInner> innerList = ms.getMenuStructs();
		Iterator<MenuStructInner> iterator = innerList.iterator();
		while(iterator != null && iterator.hasNext()){
			MenuStructInner inner = iterator.next();
			Menu menu = inner.getMenu();
			List<Station> stationList = menu.getStations();
			Iterator<Station> iter = stationList.iterator();
			while(iter != null && iter.hasNext()){
				Station sta = iter.next();
				menuStr.append(sta.getIndex());
				menuStr.append(sta.getName());
				List<Station> sList = sta.getStations();
				Collections.sort(sList, com);
				Iterator<Station> it = sList.iterator();
				while(it != null && it.hasNext()){
					Station s = it.next();
					menuStr.append(s.getIndex());
					menuStr.append(s.getName());
					menuStr.append(s.getUrl());
					menuStr.append(s.getChannelType());
					menuStr.append(s.getService());
				}
			}
		}
		logger.info(new String(menuStr));
		String menuStruct = new String(menuStr);
		return menuStruct.replaceAll(" ", "");
	}
	
	
	
}
