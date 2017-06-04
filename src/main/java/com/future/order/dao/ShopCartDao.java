/**        
 * @author: 焦祥宇 
 * @date:   createDate：2017年5月22日 上午10:47:37   
 * @Description:  
 * 
 */  
package com.future.order.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.future.order.base.BaseDao;
import com.future.order.entity.ShopCart;
import com.future.order.service.IShopCartService;

/**
 * @author 安李杰
 * 
 *
 */
@Service
public class ShopCartDao extends BaseDao<ShopCart> implements IShopCartService {

	@Override
	public List<ShopCart> getAll() {
		List<ShopCart> list=new ArrayList<ShopCart>();
		try{
			String hql="from ShopCart";
			list=this.getEntityList(hql);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public boolean delete(int id) {		
		boolean li = false;
		try{
			String hql="delete from ShopCart o Where o.id='"+id+"'";
			int mark=this.executeUpdate(hql);
		if(mark==1){
			li=true;
		}else{
			li=false;
		}
		
		}catch (Exception e) {
			e.printStackTrace();
		}
		return li;//返回底层方法，底层中有与数据库连接的增删改查的方法。
	}

	@Override
	public boolean deleteAll(ShopCart shopcart) {
		return deleteEntity(shopcart);//返回底层方法，底层中有与数据库连接的增删改查的方法。
	}

	/* 焦祥宇加
	 */
	@Override
	public boolean update(ShopCart shopCart) {		
		return this.updateEntity(shopCart);
	}

	
	@Override
	public List<ShopCart> getByTableId(int tableId) {
		String hql="from ShopCart s where s.tableId="+tableId;
		return this.getEntityList(hql);
	}
	
	@Override
	public ShopCart getByT_M_Id(int tableId, int menuId) {
		String hql="from ShopCart s where s.tableId="+tableId+" and s.menuId="+menuId;
		List<ShopCart> shopCarts=this.getEntityList(hql);
		if(shopCarts.size()>0){
			return (ShopCart)shopCarts.toArray()[0];
		}
		else{
			return null;
		}	
	}

	/* (non-Javadoc)
	 * @see com.future.order.service.IShopCartService#add(com.future.order.entity.ShopCart)
	 */
	@Override
	public boolean add(ShopCart shopCart) {
		
		return this.saveEntity(shopCart);
	}
	
}
