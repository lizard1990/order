<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css"
	href="${rootPath}css/manager/updateingredient.css">
</head>
<body  style="background: url(${rootPath}images/iframebg.jpg);">
		<div class="updateingredient">
			<form action="${rootPath}manage/Ingredient_updateIngredient" method="post">
				<input type="hidden" name="ingredient.id" value="${updateIngredient.id}" />
				<lable id="text">名称：</lable>
				<input type="text" name="ingredient.name"
					value="${updateIngredient.name}" required="required" maxlength="10"><br>
				<lable id="text">单价（元/kg）：</lable>
				<input type="text" name="ingredient.price"
					value="${updateIngredient.price}" required="required" onkeyup="this.value=/^\d+\.?\d{0,2}$/.test(this.value) ? this.value : ''"><br>
				<lable id="text">库存（kg）：</lable>
				<input type="text" name="ingredient.num"
					value="${updateIngredient.num}" required="required" onkeyup="this.value=/^\d+\.?\d{0,2}$/.test(this.value) ? this.value : ''"><br>
				<lable id="text">类型：</lable>
				<select name="ingredient.type" >
					<option value="配料"
						<c:if test='${order.status eq "配料"}'>selected="selected"</c:if>>配料</option>
					<option value="原料"
						<c:if test='${order.status eq "原料"}'>selected="selected"</c:if>>原料</option>
				</select><br>
				<lable id="text"  style="margin-top:-50px;"><span>简介：</span></lable>
				<textarea rows="4" cols="30" name="ingredient.introduce" maxlength="254" style="resize:none" >${updateIngredient.introduce}</textarea>
				<br>
				<lable id="text">添加时间：</lable>
				<input type="text" name="ingredient.createDate"
					value="${updateIngredient.createDate}" veadonly="veadonly"
					readonly="readonly" class="sang_Calender"><br>
				<script type="text/javascript" src="../js/datetime.js"></script>
				<div class="button">
					<input style="width: 100px;font-size: 15px;" type="submit" value="修改">
				</div>
			</form>
		</div>
</body>
</html>