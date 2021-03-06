/**        
 * @author: 焦祥宇 马黎明
 * @date:   createDate：2017年5月22日 上午10:48:47   
 * @Description:  
 * 
 */
package com.future.order.dao;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import com.future.order.base.BaseDao;
import com.future.order.entity.Order;
import com.future.order.service.IOrderService;
import com.future.order.util.PageCut;

@Service
public class OrderDao extends BaseDao<Order> implements IOrderService {

	@Override
	public List<Order> getAll() {

		List<Order> list = new ArrayList<Order>();
		try {
			String hql = "from Order as a order by a.createDate asc";
			list = this.getEntityList(hql);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;

	}

	@Override
	public boolean deletOrder(int id) {
		boolean sign = false;
		try {
			String hql = "delete from Order o Where o.id='" + id + "'";
			int mark = this.executeUpdate(hql);
			if (mark == 1) {
				sign = true;
			} else {
				sign = false;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return sign;
	}

	@Override
	public boolean payOrder(int id) {
		boolean sign = false;
		try {
			String status = "已付款";
			String hql = "update from Order o set o.status='" + status + "' Where o.id='" + id + "'";
			int mark = this.executeUpdate(hql);
			if (mark == 1) {
				sign = true;
			} else {
				sign = false;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return sign;
	}

	// wqj
	@Override
	public boolean updetemenu(int id, int idd, String UserName) {
		Order order = this.getEntity(id);
		String status = "处理中";
		order.setCookId(idd);
		order.setCookName(UserName);
		order.setStatus(status);
		boolean menus = this.updateEntity(order);
		return true;
	}

	@Override
	public boolean updateOrder(int id) {
		Order order = this.getEntity(id);
		String status = "未付款";
		order.setStatus(status);
		boolean menus = this.updateEntity(order);
		return true;
	}

	@Override
	public List<Order> finish() {
		List<Order> list = new ArrayList<Order>();
		String status = "已付款";
		String hql = "from Order o where o.status='" + status + "' order by o.createDate asc";
		list = this.getEntityList(hql);
		return list;
	}

	@Override
	public PageCut<Order> getFinishPagcut(int currentPage, int pageSize) {
		String status = "未付款";
		String status1 = "已付款";
		String hql;
		int count = 0;
		hql = "select count(*) from Order o where o.status='" + status + "'";
		count = ((Long) this.uniqueResult(hql)).intValue();
		PageCut<Order> pc = new PageCut<Order>(currentPage, pageSize, count);
		pc.setData(this.getEntityLimitList(
				"from Order o where o.status='" + status + "' or o.status='" + status1 + "'  order by o.createDate asc",
				(currentPage - 1) * pageSize, pageSize));
		return pc;
	}

	@Override
	public PageCut<Order> getUnfinishPagCut(int currentPage, int pageSize) {
		String status1 = "未处理";
		String status2 = "处理中";
		String hql; 
		int count = 0;
		hql = "select count(*) from Order o where o.status='" + status1 + "' or o.status='" + status2
				+ "'  order by o.createDate asc";
		count = ((Long) this.uniqueResult(hql)).intValue();
		PageCut<Order> pc = new PageCut<Order>(currentPage, pageSize, count);
		pc.setData(this.getEntityLimitList("from Order o where o.status='" + status1 + "' or o.status='" + status2
				+ "' order by o.status desc , o.id asc", (currentPage - 1) * pageSize, pageSize));
		return pc;
	}

	@Override
	public Order checkById(int id) {
		@SuppressWarnings("unused")
		Order order = new Order();
		try {
			String hql = "from Order o where o.id='" + id + "'";
			order = (Order) this.uniqueResult(hql);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return order;
	}

	@Override
	public boolean updateOrder(Order orders) {
		boolean sign = false;
		try {
			sign = this.updateEntity(orders);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sign;
	}

	@Override
	public PageCut<Order> getPageCut(int currentPage, int pageSize) {
		String hql;
		int count = 0;
		String status = "未处理";
		hql = "select count(*) from Order";
		count = ((Long) this.uniqueResult(hql)).intValue();
		PageCut<Order> pc = new PageCut<Order>(currentPage, pageSize, count);
		pc.setData(this.getEntityLimitList("from Order as o order by o.status desc , o.createDate asc",
				(currentPage - 1) * pageSize, pageSize));
		return pc;
	}

	@Override
	public PageCut<Order> getNoPageCut(int currentPage, int pageSize) {
		String hql;
		int count = 0;
		hql = "select count(*) from Order o where o.status <> '已付款'";// 当查询条件为未付款时，张金高改
		count = ((Long) this.uniqueResult(hql)).intValue();
		PageCut<Order> pc = new PageCut<Order>(currentPage, pageSize, count);
		pc.setData(
				this.getEntityLimitList("from Order o where o.status<> '已付款'", (currentPage - 1) * pageSize, pageSize));
		return pc;
	}

	@Override
	public PageCut<Order> getPage(int currentPage, int pageSize) {
		String status = "已付款";
		String hql;
		int count = 0;
		hql = "select count(*) from Order o where o.status='" + status + "'";
		count = ((Long) this.uniqueResult(hql)).intValue();
		PageCut<Order> pc = new PageCut<Order>(currentPage, pageSize, count);
		pc.setData(this.getEntityLimitList("from Order o where o.status='" + status + "'", (currentPage - 1) * pageSize,
				pageSize));
		return pc;
	}

	@Override
	public PageCut<Order> searchOrder(String input, int pageSize, int currPage) {
		StringBuilder sb = new StringBuilder("from Order as o where");
		if (input.equals(null)) {
			return null;
		}
		if (input != null && input.length() > 0) {
			sb.append(String.format(" o.id like '%%%s%%'", input));
			sb.append(String.format(" or o.status like '%%%s%%'", input));
			sb.append(String.format(" or o.tableId like '%%%s%%'", input));
			sb.append(String.format(" or o.tableName like '%%%s%%'", input));
			sb.append(String.format(" or o.cookName like '%%%s%%'", input));
		}
		int count = this.getEntityNum("select count(*) " + sb.toString());
		PageCut<Order> pc = new PageCut<>(currPage, pageSize, count);
		if (count > 0) {
			pc.setData(this.getEntityLimitList(sb.toString(), pageSize * (currPage - 1), pageSize));
		}
		return pc;
	}

	@Override
	public Order get(int tableId) {
		String hql = "from Order s where s.tableId=" + tableId;
		List<Order> order = this.getEntityList(hql);
		if (order.size() > 0) {
			return (Order) order.toArray()[0];
		} else {
			return null;
		}
	}

	@Override
	public Boolean update(Order order) {
		return this.updateEntity(order);
	}

	@Override
	public Boolean save(Order order) {
		return this.saveEntity(order);
	}

	@Override
	public List<Order> getFinal(int tableId, int num) {
		String sql = "select * from tb_order s where s.tableId=" + tableId + " order by total desc limit " + num;
		return this.executeSQLQuery(sql);
	}

	@Override
	public boolean deleteOrder(int tableId) {
		boolean li = false;
		try {
			String hql = "delete from Order o Where o.tableId='" + tableId + "'";
			int mark = this.executeUpdate(hql);
			if (mark == 1) {
				li = true;
			} else {
				li = false;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return li;// 返回底层方法，底层中有与数据库连接的增删改查的方法。
	}

	@Override
	public List<Order> getOrder(int tableId) {
		String sql = "from Order s where s.tableId=" + tableId;
		return this.getEntityList(sql);
	}

	@Override
	public Order getOrder1(int tableId) {
//		String hql = "from Order where id=(select max(id) from Order) and tableId ='" + tableId + "'";
		String hql = "from Order where tableId ='" + tableId + "' and status != '已付款'";
		Order order = (Order) uniqueResult(hql);
		return order;
	}

	@Override
	public PageCut<Order> getSomePageCut(int currentPage, int pageSize, String inquiry) {
		String hql;
		String selecthql;
		hql = "select count(*) from Order where concat (tableName,',',cookName,',',payway,',',cashierName,',',total,',',status,',',id) like '%"+inquiry+"%'";
		selecthql="from Order where concat (tableName,',',cookName,',',payway,',',cashierName,',',total,',',status,',',id) like '%"+inquiry+"%'";
		int count = ((Long) this.uniqueResult(hql)).intValue();
		PageCut<Order> pc = new PageCut<Order>(currentPage, pageSize, count);
		pc.setData(this.getEntityLimitList(selecthql,
				(currentPage - 1) * pageSize, pageSize));
		return pc;
	}

	@Override
	public PageCut<Order> getPagegain(int currentPage, int pageSize, String starttime, String endtime, String sign) {
		String status = null;
		String hql = null;
		String selecthql = null;
		int count = 0;
		if (sign.equals("all")) {
			selecthql = "from Order o where o.createDate between '" + starttime + "' and '" + endtime + "'";
			hql = "select count(*) from Order where createDate between '" + starttime + "' and '" + endtime + "'";
		} else if (sign.equals("yes")) {
			status = "已付款";
			selecthql = "from Order o where o.createDate between '" + starttime + "' and '" + endtime + "' and status='"
					+ status + "'";
			hql = "select count(*) from Order where createDate between '" + starttime + "' and '" + endtime
					+ "' and status='" + status + "'";
		} else if (sign.equals("no")) {
			status = "未付款";
			selecthql = "from Order o where o.createDate between '" + starttime + "' and '" + endtime + "' and status='"
					+ status + "'";
			hql = "select count(*) from Order where createDate between '" + starttime + "' and '" + endtime
					+ "' and status='" + status + "'";
		}
		count = ((Long) this.uniqueResult(hql)).intValue();
		PageCut<Order> pc = new PageCut<Order>(currentPage, pageSize, count);
		pc.setData(this.getEntityLimitList(selecthql, (currentPage - 1) * pageSize, pageSize));
		return pc;
	}

	@Override
	public List<Order> getGain(String starttime, String endtime, String sign) {
		String status = null;
		String hql = null;
		String selecthql = null;
		if (sign.equals("all")) {
			selecthql = "from Order o where o.createDate between '" + starttime + "' and '" + endtime + "'";
		} else if (sign.equals("yes")) {
			status = "已付款";
			selecthql = "from Order o where o.createDate between '" + starttime + "' and '" + endtime + "' and status='"
					+ status + "'";
		} else if (sign.equals("no")) { // 当查询条件为未付款时，张金高改
			selecthql = "from Order o where o.createDate between '" + starttime + "' and '" + endtime
					+ "' and status <> '已付款'";
		}
		return getEntityList(selecthql);
	}

	@Override
	public List<Order> getPrice(String inquiry) {
		String hql = "from Order where concat (tableName,',',cookName,',',payway,',',cashierName,',',total,',',status,',',id) like '%"+inquiry+"%'";
		return getEntityList(hql);
	}

	@Override
	public boolean delete(int id) {
		boolean sign = false;
		try {
			String hql = "delete from Order o Where o.id='" + id + "'";
			int mark = this.executeUpdate(hql);
			if (mark == 1) {
				sign = true;
			} else {
				sign = false;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return sign;
	}

	@Override
	public Order selectOrder(int id, String sign) {
		String hql;
		if (sign.equals("table")) {
			hql = "from Order o where o.status<> '已付款' and o.tableId='" + id + "'";
		} else {
			hql = "from Order o where o.status<> '已付款' and o.id='" + id + "'";
		}

			return (Order) uniqueResult(hql);
	}
}
