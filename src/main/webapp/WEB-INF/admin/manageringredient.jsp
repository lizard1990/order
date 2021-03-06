<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
 <link rel="stylesheet" type="text/css" href="${rootPath}css/manager/manageringredient.css">
     <link href="${rootPath}css/bootstrap.css" rel="stylesheet" />
</head>
<script language="javascript">
window.onload=function(){
	 var array = new Array();  
	 <c:forEach items="${allIngredient.data}" var="t">  
	 array.push("${t.id}"); //js中可以使用此标签，将EL表达式中的值push到数组中  
	 </c:forEach>
	 var a = array.length;
 if(a==0){
	 document.getElementById('div1').style.display='none';
	 document.getElementById('div2').style.display='none';
 }
}
	</script>
<body  style="background: url(${rootPath}images/iframebg.jpg);">
<div style="margin-left: 40%;margin-top:2%;font-size:20px;font-weight: bold;">${updateIngredientMsg}${deleteIngredientMsg}</div>
<div style="float:right;margin-right:10%;width:30%;height:30px; margin-bottom:2%;">
	<form action="${rootPath}manage/Ingredient_Inquiry" method="post"style="margin-left:10%;">
		<input  class="text" type="text" name="inquiry" placeholder="名称/单价/库存/类型" required="required">
		<input style="border-radius: 5px;width:15%;background-color: #82C0E9;" type="submit" value="查询">
	</form>
</div>
<c:if test="${fn:length(allIngredient.data)>0}">
<div  id="div1">
			<table cellspacing="0">
				<thead>
					<tr class="tr1">
						<td>名称</td>
						<td>单价</td>
						<td>库存</td>
						<td>添加时间</td>
						<td>类型</td>
						<td>简介</td>
						<td>操作</td>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${allIngredient.data}" var="ingredient">
						<tr>
							<td>${ingredient.name}</td>
							<td>${ingredient.price}</td>
							<td>${ingredient.num}</td>
							<td style="width: 200px;">${ingredient.createDate}</td>
							<td>${ingredient.type}</td>
							<td style="width: 380px;"><textarea readonly="readonly" style="float: left;resize:none;"
									rows="2" cols="61">${ingredient.introduce}</textarea></td>
							<td><a
								href="${rootPath}manage/Ingredient_toUpdateIngredient?ingredient.id=${ingredient.id}"><span
									class="glyphicon glyphicon-pencil"></span></a>&nbsp;&nbsp;&nbsp; <a
								href="${rootPath}manage/Ingredient_deleteIngredient?ingredient.id=${ingredient.id}"><span
									class="glyphicon glyphicon-trash"></span></a></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
    <div class="page" id="div2">
    <ul class="pagination">
      <li><a href="${rootPath}manage/Ingredient_${adss}?page=${allIngredient.prePage}">上一页</a></li>
					<c:forEach var="i" begin="${allIngredient.currentPage-2>0?allIngredient.currentPage-2:1 }"
 					end="${allIngredient.currentPage+2>allIngredient.pageNum?allIngredient.pageNum:allIngredient.currentPage+2  }">
 					<c:choose>
 						<c:when test="${i>0 && i == allIngredient.currentPage}"> 
 							<li class="active"><a href="${rootPath }manage/Ingredient_${adss}?page=${i }">${i}</a></li> 
						</c:when> 
 						<c:when test="${i>0 && i != allIngredient.currentPage}"> 
 							<li><a href="${rootPath }manage/Ingredient_${adss}?page=${i }">${i}</a></li> 
						</c:when> 
 					</c:choose> 
 				</c:forEach>		
      <li><a href="${rootPath }manage/Ingredient_${adss}?page=${allIngredient.nextPage}">下一页</a></li>
    </ul>
</div>
</c:if>
</body>
</html>