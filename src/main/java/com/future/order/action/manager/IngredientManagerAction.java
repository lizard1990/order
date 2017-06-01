package com.future.order.action.manager;

import java.util.Date;
import java.util.List;

import com.future.order.base.BaseAction;
import com.future.order.entity.Ingredient;
import com.future.order.entity.User;
import com.future.order.util.PageCut;

public class IngredientManagerAction extends BaseAction {

	private  Ingredient ingredient;
	private int page=1;
	
	public String execute(){
		PageCut<Ingredient> pCut=ingerdientService.getPageCut(page,3);
		request.put("allIngredient", pCut);
		return SUCCESS;
	}
	
	
	//增加配料
	public String addIngredient(){
		ingredient.setCreateDate(new Date());
		boolean boo = ingerdientService.addIngredient(ingredient);
		if(boo){
			request.put("addIngerdientMsg", "添加成功");
		} else {
			request.put("addIngerdientMsg", "添加失败");
		}
		return "addIngredient";
	}
	//查询被修改配料信息，到达修改界面
	public String toUpdateIngredient(){
		int id = ingredient.getId();
		Ingredient ingredient = ingerdientService.getById(id);
		request.put("updateIngredient", ingredient);
		return "toUpdateIngredient";
	}
	//修改配料信息
	public String updateIngredient(){
		boolean boo = ingerdientService.updateIngredient(ingredient);
		if(boo){
			request.put("updateIngredientMsg", "修改成功");
		} else {
			request.put("updateIngredientMsg", "修改失败");
		}
		List<Ingredient> list = ingerdientService.getAll();
		request.put("allIngredient", list);
		return "updateIngredient";
	}
	//删除配料
	public String deleteIngredient(){
		boolean boo = ingerdientService.deleteIngredient(ingredient);
		if(boo){
			request.put("deleteIngredientMsg", "删除成功");
		} else {
			request.put("deleteIngredientMsg", "删除失败");
		}
		List<Ingredient> list = ingerdientService.getAll();
		request.put("allIngredient", list);
		return "deleteIngredient";
	}
	
	
	public Ingredient getIngredient() {
		return ingredient;
	}

	public void setIngredient(Ingredient ingredient) {
		this.ingredient = ingredient;
	}

	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	
}