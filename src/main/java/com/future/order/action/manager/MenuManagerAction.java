package com.future.order.action.manager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import org.apache.struts2.ServletActionContext;

import com.future.order.base.BaseAction;
import com.future.order.entity.Ingredient;
import com.future.order.entity.Menu;
import com.future.order.entity.MenuType;
import com.future.order.util.PageCut;

@SuppressWarnings("serial")
public class MenuManagerAction extends BaseAction {

	private Menu menu;
	private int page = 1;	//获得初始页数
	private String inquiry;//得到查询的内容
	private String ask;	//得到请求查询的条件
	private List<File> file;	// 上传文件集合
	private List<String> fileFileName;	// 上传文件名集合
	private List<String> fileContentType;	// 上传文件内容类型集合

	public String execute() {//遍历菜品，分页
		List<MenuType> typelist=menuTypeService.getAllMenuType();
		request.put("Typelist",typelist);
		PageCut<Menu> pCut = menuService.getPageCut(page, 7);
		if(pCut.getData().size()==0){
			String mark="没有菜品";
			request.put("deleteMenuMsg", mark);
		}
		request.put("allMenu", pCut);
		request.put("adss", "execute");
		return SUCCESS;
	}
	
	public String addMenu() throws Exception{//添加菜品
		String typeName=menu.getTypeName();
		int typeId=menuTypeService.getByName(typeName).getId();		
		menu.setTypeId(typeId);		
		boolean boo=false;
		if(file==null||file.equals("")){
			request.put("addMsg", "添加失败！请上传图片");
		}else{
			for (int i = 0; i < file.size(); i++) {
				// 循环上传每个文件
				uploadFile(i);
			}
			boo = menuService.addMenu(menu);
			if(boo){
					request.put("addMsg", "添加成功");	//添加完菜名后添加菜的配料
					session.put("menu", menu);
					List<Ingredient> lists = ingerdientService.getAll();	//暂时不要分页
					request.put("allIngredient", lists);
					return "addMenu";
			} else {
				request.put("addMsg", "添加失败！该菜已被添加过");
			}
		}
		List<MenuType> list=menuTypeService.getAllMenuType();
		request.put("Typelist",list);
		return "addAgain";		
	}
	
	//查看菜品详情
	public String toUpdateMenu(){
		int id = menu.getId();
		Menu menu = menuService.get(id);
		List<MenuType> list=menuTypeService.getAllMenuType();
		request.put("Typelist",list);
		for(int i=0;i<list.size();i++){
			if(list.get(i).equals(menu.getTypeName())){
				request.put("select", i);
			}
		}
		request.put("updateMenu", menu);
		return "toUpdateMenu";
	}

	// 修改菜品
	public String updateMenu() throws FileNotFoundException, IOException {
		boolean boo=true;
		String msg = "修改失败";
		Menu menuDb = menuService.get(menu.getId());
		if(menuDb!=null&&!menuDb.getName().equals(menu.getName())){
			Menu menuCheckName = menuService.inquiryByName(menu.getName());
			boo=false;
			msg = "修改失败，该菜已存在";
			if(menuCheckName==null){
				msg = "修改失败";
				boo = true;
			}
		}
		MenuType menuType = menuTypeService.CheckById(menu.getTypeId());
		menu.setTypeName(menuType.getName());
		Menu menuData = menuService.get(menu.getId());
		menu.setCreateDate(menuData.getCreateDate());
		if(file==null||file.equals("")){
			menu.setImgUrl(menu.getImgUrl());
		}else{
			for (int i = 0; i < file.size(); i++) {
				// 循环上传每个文件
				uploadFile(i);
			}
		}
		if(boo){
			msg = "修改成功";
			boo = menuService.updateUser(menu);			
		}
		request.put("updateMsg", msg);
		PageCut<Menu> pCut = menuService.getPageCut(page, 7);
		request.put("allMenu", pCut);
		return "updateMenu";
	}

	// 删除菜品
	public String deleteMenu() {
		String imgPath = ServletActionContext.getRequest().getRealPath("uploadImg")+"/"+menu.getImgUrl();
		boolean boo = menuService.deleteMenu(menu);
		boolean booMater = menuMaterialService.deleteAboutMenu(menu.getId());
		File imgFile = new File(imgPath);
		if(imgFile.exists()){
			imgFile.delete(); 		//删除该记录时删除对应的图片
		}
		if (boo&&booMater) {
			request.put("deleteMenuMsg", "删除成功");
		} else {
			request.put("deleteMenuMsg", "删除失败");
		}
		PageCut<Menu> pCut = menuService.getPageCut(page, 7);
		request.put("allMenu", pCut);
		return "deleteUser";
		
	}
	
	// 执行上传功能
	private void uploadFile(int i) throws FileNotFoundException, IOException {
		try {
			InputStream in = new FileInputStream(file.get(i));
			String dir = ServletActionContext.getRequest().getRealPath("uploadImg");
			File fileLocation = new File(dir);
			// 此处也可以在应用根目录手动建立目标上传目录
			if (!fileLocation.exists()) {
				boolean isCreated = fileLocation.mkdir();
				if (!isCreated) {
					// 目标上传目录创建失败,可做其他处理,例如抛出自定义异常等,一般应该不会出现这种情况。
					return;
				}
			}
			String fileName = this.getFileFileName().get(i);
			menu.setImgUrl(fileName);
			File uploadFile = new File(dir, fileName);
			OutputStream out = new FileOutputStream(uploadFile);
			byte[] buffer = new byte[1024 * 1024];
			int length;
			while ((length = in.read(buffer)) > 0) {
				out.write(buffer, 0, length);
			}
			in.close();
			out.close();
		} catch (Exception ex) {
			System.out.println("上传失败!");
			ex.printStackTrace();
		}
	}
	public String Inquiry(){
		PageCut<Menu> pCut=new PageCut<Menu>();
		if(inquiry==null){
			inquiry=(String) session.get("inquiry");
		}
		pCut = menuService.getSomePageCut(page, 7,inquiry);
		if(pCut.getData().size()==0){
			String mark="没有菜品";
			request.put("deleteMenuMsg", mark);
		}
		request.put("adss", "Inquiry");		
		session.put("inquiry", inquiry);
		request.put("allMenu", pCut);
		return SUCCESS;
	}
	public Menu getMenu() {
		return menu;
	}

	public void setMenu(Menu menu) {
		this.menu = menu;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public List<File> getFile() {
		return file;
	}

	public void setFile(List<File> file) {
		this.file = file;
	}

	public List<String> getFileFileName() {
		return fileFileName;
	}

	public void setFileFileName(List<String> fileFileName) {
		this.fileFileName = fileFileName;
	}

	public List<String> getFileContentType() {
		return fileContentType;
	}

	public void setFileContentType(List<String> fileContentType) {
		this.fileContentType = fileContentType;
	}

	public String getInquiry() {
		return inquiry;
	}

	public void setInquiry(String inquiry) {
		this.inquiry = inquiry;
	}
	public String getAsk() {
		return ask;
	}

	public void setAsk(String ask) {
		this.ask = ask;
	}

}
