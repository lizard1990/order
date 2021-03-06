/**        
 * @author: 焦祥宇 

 * @date:   createDate：2017年5月22日 上午10:50:32   
 * @Description:  
 * 
 */
package com.future.order.dao;

import java.io.UnsupportedEncodingException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.future.order.base.BaseDao;

import com.future.order.entity.Menu;
import com.future.order.service.IMenuService;
import com.future.order.util.PageCut;

@Service
public class MenuDao extends BaseDao<Menu> implements IMenuService {

	// wqj获取订单
	@Override
	public Menu Doorder(int id) {
		Menu menu = this.getEntity(id);
		return menu;
	}

	@Override
	public List<Menu> getAll() {
		List<Menu> list = new ArrayList<Menu>();
		try {
			String hql = "from Menu";
			list = this.getEntityList(hql);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<Menu> unfinish() {
		List<Menu> list = new ArrayList<Menu>();
		int exist = 0;
		String hql = "from Menu m where m.exist='" + exist + "'";
		list = this.getEntityList(hql);
		return list;
	}

	@Override
	public List<Menu> CheckDetails(int id) {// 根据订单id查询所有该菜单的详细信息
		@SuppressWarnings("unused")
		List<Menu> list = new ArrayList<Menu>();
		try {
			String hql = "from Menu m where m.id='" + id + "'";
			list = this.getEntityList(hql);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	////根据前台传过来的id去获取每道菜的信息 安李杰用
	public Menu get(int id) {
		String hql = "from Menu m where m.id='" + id + "'";
		Menu menu = (Menu) this.uniqueResult(hql);
		return menu;
	}

	@Override
	public List<Menu> ByName(String typeName) {
		if (XXX(typeName, "iso8859-1")) {
			try {
				typeName = new String(typeName.getBytes("iso8859-1"), "utf8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}

		String hql = "from Menu m where m.typeName='" + typeName + "'";
		return this.getEntityList(hql);
	}

	// 张金高加
	@Override
	public boolean addMenu(Menu menu) {// 根据菜品判断数据库里是否已存在该菜
		String hql = "from Menu where name='" + menu.getName() + "'";
		Menu menuDataBase = (Menu) this.uniqueResult(hql);
		if (menuDataBase == null) {
			menu.setCreateDate(new Date());
			return this.saveEntity(menu);
		}
		return false;
	}

	@Override
	public PageCut<Menu> getPageCut(int curr, int pageSize) {
		String hql = "select count(*) from Menu";
		String selectHql =  "from Menu";
		int count = ((Long) this.uniqueResult(hql)).intValue();
		PageCut<Menu> pc = new PageCut<Menu>(curr, pageSize, count);
		pc.setData(this.getEntityLimitList(selectHql, (curr - 1) * pageSize, pageSize));
		return pc;
	}

	@Override
	public boolean updateUser(Menu menu) {
		return this.updateEntity(menu);
	}

	@Override
	public boolean deleteMenu(Menu menu) {
		return this.deleteEntity(menu);
	}

	@Override
	public Menu inquiryByName(String inquiry) {
		String hql = "from Menu where name = '" + inquiry + "'";
		return (Menu) this.uniqueResult(hql);
	}

	/**
	 * @author 丁赵雷
	 * @date 2017/5/30 21:17
	 * @param str
	 *            需要判断的字符串
	 * @param charset
	 *            字符集
	 * @return 判断该字符串是哪种字符集
	 */
	public static boolean XXX(String str, String charset) {
		boolean flag = false;

		try {
			if (str.equals(new String(str.getBytes(charset), charset))) {
				return true;
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return flag;
	}

	/*
	 * 获取推荐菜品除(6,7,8),安李杰用
	 */
	@Override
	public List<Menu> getRecommend(int num) {
		String sql = "select * from tb_menu where exist=1 and typeId not in(6,7,8) order by num desc limit " + num ;
		return this.executeSQLQuery(sql);
	}
	//根据菜品类型id获得菜品 安李杰用
	@Override
	public List<Menu> getByTypeId(int typeId) {
		String hql = "from Menu m where m.exist=1 and m.typeId=" + typeId + " order by m.id desc";
		return this.getEntityList(hql);
	}

	@Override
	public boolean updatemenu(int id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public PageCut<Menu> getSomePageCut(int curr, int pageSize, String inquiry) {//菜名/类型/价格
		String hql = "select count(*) from Menu where concat(name,',',typeName,',',price) like '%"+inquiry+"%'";
		String selectHql = "from Menu where concat(name,',',typeName,',',price) like '%"+inquiry+"%'";
		int count = ((Long) this.uniqueResult(hql)).intValue();
		PageCut<Menu> pc = new PageCut<Menu>(curr, pageSize, count);
		pc.setData(this.getEntityLimitList(selectHql, (curr - 1) * pageSize, pageSize));
		return pc;
	}

	@Override
	public Boolean update(Menu m) {
		
		return this.updateEntity(m);
	}

}
