/**
 * 
 */
package com.opensource.nredis.proxy.monitor.api;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.opensource.nredis.proxy.monitor.enums.LeafEnums;
import com.opensource.nredis.proxy.monitor.enums.SpreadEnums;
import com.opensource.nredis.proxy.monitor.model.PsMenu;
import com.opensource.nredis.proxy.monitor.model.PsUserMenu;
import com.opensource.nredis.proxy.monitor.platform.ResponseObject;
import com.opensource.nredis.proxy.monitor.platform.RestStatus;
import com.opensource.nredis.proxy.monitor.service.IPsMenuService;
import com.opensource.nredis.proxy.monitor.service.IPsUserMenuService;
import com.opensource.nredis.proxy.monitor.string.utils.StringUtil;

/**
 * @author liubing
 *
 */
@Controller
public class PsMenuController {
	
	@Autowired
	private IPsMenuService psMenuService;
	
	@Autowired
	private IPsUserMenuService psUserMenuService;
	/**
	 * 新增菜单
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/menu", method = RequestMethod.POST)
	@ResponseBody
	public ResponseObject menu(PsMenu psMenu) throws Exception{
		ResponseObject responseObject=new ResponseObject();
		if(psMenu.getParentId()!=null&&psMenu.getParentId()>0){
			PsMenu parentPsMenu=psMenuService.getEntityById(psMenu.getParentId());//查询父节点
			if(parentPsMenu!=null&&parentPsMenu.getId()!=null){
				parentPsMenu.setIsLeaf(LeafEnums.NO_LEAF.getCode());
				parentPsMenu.setSpread(SpreadEnums.TRUE.getCode());
				psMenuService.modifyEntityById(parentPsMenu);
			}
		}else{
			psMenu.setParentId(0);
		}
		if(StringUtil.isEmpty(psMenu.getHref())){
			psMenu.setHref(" ");
		}
		psMenuService.create(psMenu);
		responseObject.setStatus(RestStatus.SUCCESS.code);
		responseObject.setMessage(RestStatus.SUCCESS.message);
		return responseObject;
	}
	
	/**
	 * 通过菜单关联批量用户
	 * @param menuId
	 * @param userIds
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/link", method = RequestMethod.POST)
	@ResponseBody
	public ResponseObject link(@Validated Integer menuId,String userIds) throws Exception{
		ResponseObject responseObject=new ResponseObject();
		
		String [] users=userIds.split(",");
		if(StringUtil.isEmpty(userIds)||menuId==null){
			responseObject.setStatus(RestStatus.PARAMETER_REQUIRED.code);
			responseObject.setMessage(RestStatus.PARAMETER_REQUIRED.message);
			return responseObject;
		}
		PsUserMenu psUserMenu1=new PsUserMenu();
		psUserMenu1.setMenuId(menuId);
		List<PsUserMenu> list=psUserMenuService.queryEntityList(psUserMenu1);
		if(list!=null&&list.size()>0){
			for(PsUserMenu userMenu:list){
				psUserMenuService.deleteEntityById(userMenu.getId());//删除已经关联用户
			}
		}
		for(String userId:users){
			
			PsUserMenu psUserMenu=new PsUserMenu();
			psUserMenu.setMenuId(menuId);
			psUserMenu.setUserId(Integer.parseInt(userId));
			psUserMenuService.create(psUserMenu);
		}
		
		responseObject.setStatus(RestStatus.SUCCESS.code);
		responseObject.setMessage(RestStatus.SUCCESS.message);
		return responseObject;
	}
	
	@RequestMapping(value = "/searchSelectUserByMenuIdAndUserId", method = RequestMethod.GET)
	@ResponseBody
	public ResponseObject searchSelectUserByMenuIdAndUserId(@Validated Integer menuId,Integer userId) throws Exception{
		ResponseObject responseObject=new ResponseObject();
		if(menuId==null||menuId==0||userId==null||userId==0){
			responseObject.setStatus(RestStatus.SERVER_ERROR.code);
			responseObject.setMessage(RestStatus.SERVER_ERROR.message);
			return responseObject;
		}
		PsUserMenu psUserMenu=new PsUserMenu();
		psUserMenu.setMenuId(menuId);
		psUserMenu.setUserId(userId);
		List<PsUserMenu> psUserMenus=psUserMenuService.queryEntityList(psUserMenu);
		if(psUserMenus!=null&&psUserMenus.size()>0){
			responseObject.setStatus(RestStatus.SUCCESS.code);
			responseObject.setMessage(RestStatus.SUCCESS.message);
			return responseObject;
		}
		responseObject.setStatus(RestStatus.SERVER_ERROR.code);
		responseObject.setMessage(RestStatus.SERVER_ERROR.message);
		return responseObject;
	}
	
	/**
	 * 修改
	 * @param psMenu
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/editMenu", method = RequestMethod.POST)
	@ResponseBody
	public ResponseObject editMenu(PsMenu psMenu) throws Exception{
		ResponseObject responseObject=new ResponseObject();
		psMenuService.modifyEntityById(psMenu);
		responseObject.setStatus(RestStatus.SUCCESS.code);
		responseObject.setMessage(RestStatus.SUCCESS.message);
		return responseObject;
	}
	
	/**
	 * 查询详情
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/{id}/searchMenu", method = RequestMethod.GET)
	@ResponseBody
	public ResponseObject searchMenu(@PathVariable Integer id) throws Exception{
		ResponseObject responseObject=new ResponseObject();
		PsMenu psMenu=psMenuService.getEntityById(id);
		responseObject.setStatus(RestStatus.SUCCESS.code);
		responseObject.setMessage(RestStatus.SUCCESS.message);
		responseObject.setData(psMenu);
		return responseObject;
	}
	
	/**
	 * 删除
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/removeMenu", method = RequestMethod.POST)
	@ResponseBody
	public ResponseObject removeMenu(@Validated Integer id) throws Exception{
		ResponseObject responseObject=new ResponseObject();
		PsMenu psMenu=psMenuService.getEntityById(id);
		PsMenu parentPsMenu1=new PsMenu();
		parentPsMenu1.setParentId(id);
		List<PsMenu> psMenus1=psMenuService.queryEntityList(parentPsMenu1);
		if(psMenus1!=null&&psMenus1.size()>0){//不能删除根节点
			responseObject.setStatus(RestStatus.EXIST_NODE.code);
			responseObject.setMessage(RestStatus.EXIST_NODE.message);
			return responseObject;
		}
		
		psMenuService.deleteEntityById(id);//删除节点
		PsMenu parentPsMenu=psMenuService.getEntityById(psMenu.getParentId());//查询父节点
		if(parentPsMenu!=null&&parentPsMenu.getId()>0){
			PsMenu queryObject=new PsMenu();
			queryObject.setParentId(psMenu.getParentId());
			List<PsMenu> psMenus=psMenuService.queryEntityList(queryObject);//查询这个节点是否有子节点
			if(psMenus==null||psMenus.size()==0){//节点没有子节点
				parentPsMenu.setIsLeaf(LeafEnums.LEAF.getCode());
				parentPsMenu.setSpread(SpreadEnums.FALSE.getCode());
				psMenuService.modifyEntityById(parentPsMenu);
			}
		}
		responseObject.setStatus(RestStatus.SUCCESS.code);
		responseObject.setMessage(RestStatus.SUCCESS.message);
		return responseObject;
	}
	
	/**
	 * 查询所有
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/searchMenus", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public List<PsMenu> searchMenus() throws Exception{
		
		List<PsMenu> psMenus=psMenuService.queryEntityList(null);//查询所有菜单
		
		if(psMenus!=null&&psMenus.size()>0){
			List<PsMenu> results=buildTree(psMenus);
			return results;
		}
		
		
		return new ArrayList<PsMenu>();
	}
	
	/**
	 * 查询系统菜单，拥有权限
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/searchSystemMenus", method = RequestMethod.GET)
	@ResponseBody
	public ResponseObject searchSystemMenus(HttpServletRequest request, HttpServletResponse response) throws Exception{
		ResponseObject responseObject=new ResponseObject();
		Object userid=request.getSession().getAttribute("userid");
		if(userid==null){
			responseObject.setStatus(RestStatus.NOT_CURRENTUSER.code);
			responseObject.setMessage(RestStatus.NOT_CURRENTUSER.message);
			return responseObject;
		}
		PsMenu psSearchMenu=new PsMenu();
		psSearchMenu.setPsUserId(Integer.parseInt(String.valueOf(userid)));
		List<PsMenu> psMenus=psMenuService.getMenusByUserId(psSearchMenu);
		if(psMenus!=null&&psMenus.size()>0){
			List<PsMenu> results=buildTree(psMenus);
			responseObject.setData(results);
		}
		
		responseObject.setStatus(RestStatus.SUCCESS.code);
		responseObject.setMessage(RestStatus.SUCCESS.message);
		return responseObject;
	}
	/**
	 * 创建树
	 * @param psMenus
	 */
	private List<PsMenu> buildTree(List<PsMenu> psMenus){  
		List<PsMenu> firstMenus = new ArrayList<PsMenu>();
        for (PsMenu node : psMenus) {  
            if (node.getParentId() == null||node.getParentId()==0) {  
            	firstMenus.add(node);
            }  
        }
        psMenus.removeAll(firstMenus);
        buildMenuTree(firstMenus, psMenus);
        return firstMenus;
    }  
	
	private  void buildMenuTree(List<PsMenu> firstMenus, List<PsMenu> menus){
        List<PsMenu> copyMenus =new ArrayList<PsMenu>(menus);
        for(PsMenu menu : firstMenus){
            for(PsMenu m : copyMenus){
                if(m.getParentId().equals(menu.getId())){
                    menu.getChildren().add(m);
                    //copyMenus.remove(m);
                }
            }
        }
        for(PsMenu menu : firstMenus){
            if(menu.getChildren()!=null&&menu.getChildren().size()>0){
                buildMenuTree(menu.getChildren(), copyMenus);
            }
        }
    }
	

}
