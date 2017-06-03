package com.future.order.action.customer;


import java.util.List;

import com.future.order.base.BaseAction;

import com.future.order.entity.Menu;
import com.future.order.entity.MenuMaterial;
import com.future.order.entity.MenuType;
import com.future.order.entity.Order;
import com.future.order.entity.ShopCart;
import com.future.order.entity.StockDetails;

/**
 * @author 安李杰 
 *
 */

public class CustomerAction extends BaseAction {

	/**这里写了用于对于菜单的信息显示获取菜单的详细信息的action
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private int id;
		
	//进入首页
	public String toIndex() throws Exception{
		//把顾客桌号存在session
		session.put("tableId", id);
		System.out.println("桌号:"+id);
		//获得推荐的菜品
		List<Menu> menus=menuService.getRecommend(8);
		session.put("menus", menus);
		return "toIndex";
	}
	//根据菜品类型id获得菜品
	public String getMenuByTypeId() throws Exception{
		List<Menu> menus=menuService.getByTypeId(id);
		session.put("menus", menus);
		return "getMenuByTypeId";
	}
	//获得菜品详情和菜品配料
	public String getMenuMaterial() throws Exception {		
		Menu menu =menuService.get(id);
		request.put("menu", menu);
		List<MenuMaterial> menumaterials=menuMaterialService.getByMenuId(id);		
		request.put("menumaterials",menumaterials);	
		return "getMenuMaterial";
	}
	//获得进货时间列表
	public String getStockDate() throws Exception {
		List<StockDetails> stockDetails=stockDetailsService.getByIngId(id);
		request.put("stockDetails", stockDetails);
		return "getStockDate";
	}
	//加入购物车
	public String joinCart() throws Exception {
		Menu menu=menuService.get(id);
		int tableId=(int) session.get("tableId");//从session中取出桌子编号
		String tableName=tablesService.get(tableId).getName();//根据桌子编号获得桌子的名称		
		ShopCart shopCart=new ShopCart(tableId, tableName, id, menu.getName(), 1, menu.getPrice());
		Boolean bool=shopCartService.add(shopCart);
		
		return "joinCart";
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	
	


	/*public void setName(String name) {
		this.name = name;
	}*/
/*	public String getMenuMaterial() throws Exception {
		List<MenuMaterial> list=menuMaterialService.getAll();
		request.put("MenuMaterial",list);
		System.out.println(list);
		return "getMenuMaterial";
>>>>>>> 3a83ffce9b3c4e9e1fcfd6d2d7c0209cc68bf35a
	}
	
	public String getMenuType() throws Exception{
		List<MenuType> list=menuTypeService.getAllMenuType();
		request.put("list",list);
		List<Menu> list1=menuService.getAll();
		request.put("menu1",list1);
		return "getMenuType";
	}
	
	public String getOrder() throws Exception {
		List<Order> list=orderService.getAll();
		request.put("order",list);
		return "getOrder";
	}

	
	public String ByName() throws Exception{
		List<Menu> menu=menuService.ByName(name);
		request.put("menu",menu);
		return "ByName";
	}
	public String menu() throws Exception {				
		Menu menu=menuService.get(id);
		List<MenuMaterial> list=menuMaterialService.getByMenuId(id);
		request.put("menu",menu);
		request.put("list",list);
		return "menu";
	}
	
	public String StockDetails() throws Exception {
		List<StockDetails> list1=stockDetailsService.getBycreateDate(ingId);
		request.put("list1",list1);
		return "StockDetails";
	}
*/
}
