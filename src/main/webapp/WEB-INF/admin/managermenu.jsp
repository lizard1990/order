<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
	<link rel="stylesheet" type="text/css" href="${rootPath}css/manager/managermenu.css">
    <link rel="stylesheet" type="text/css" href="${rootPath}css/bootstrap.css">
    <link href="${rootPath}css/basic.css" rel="stylesheet" />
<script language="javascript">
window.onload=function(){
	 var array = new Array();  
	 <c:forEach items="${allMenu.data}" var="t">  
	 array.push("${t.id}"); //js中可以使用此标签，将EL表达式中的值push到数组中  
	 </c:forEach>
	 var a = array.length;
 if(a==0){
	 document.getElementById('div1').style.display='none';
	 document.getElementById('div2').style.display='none';
 }
}
</script>
</head>
<body  style="background: url(${rootPath}images/iframebg.jpg);">
<center>
	<div class="error" style="font-weight: bold;">${updateMsg}${deleteMenuMsg }</div>
</cenetr>
 <div class="photowall" style="margin-left:35%;"> 
	<form action="${rootPath}manage/MenuManager_Inquiry" method="post">
		<input class="input" type="text" name="inquiry" placeholder="菜名/类型/价格" required="required">
		<button style="border-radius: 5px;width:6%;background-color: #82C0E9;" type="submit">查询</button>
	</form>
</div>
<c:if test="${fn:length(allMenu.data)>0 }">
    <div class="picture_wall" id="div1">
    		${inquiryMsg}
    		<table>
    			<tr>
    				<th style="width:200px;">菜名</th>
    				<th style="width:115px;">图片</th>
    				<th >类型</th>
    				<th style="width:85px;">价格/元</th>
    				<th>状态</th>
    				<th>简介</th>
    				<th style="width:90px;">操作</th>
    			</tr>
    		<c:forEach items="${allMenu.data}" var="data">
    			<tr>
    				<td>${data.name}</td>
    				<td><img src="${rootPath}uploadImg/${data.imgUrl}" alt="未添加图片"></td>
    				<td>${data.typeName}</td>
    				<td>${data.price}</td>
    				<td><c:if test="${data.exist eq '0'}">暂缺</c:if><c:if test="${data.exist eq '1'}">可做</c:if></td>
    				<td>
    					<textarea rows="2" cols="20" style="resize:none;" maxlength="254" readonly="readonly">${data.introduce}</textarea>
    				</td>
    				<td>
    					<a href="${rootPath}manage/MenuManager_toUpdateMenu?menu.id=${data.id}"><span class="glyphicon glyphicon-pencil"></span></a>&nbsp;&nbsp;&nbsp;
    					<a href="${rootPath}manage/MenuManager_deleteMenu?menu.id=${data.id}"><span class="glyphicon glyphicon-trash"></span></a>
    				</td>
    			</tr>
    		</c:forEach>
    		</table>
    </div>  
   <div class="page" id="div2">
    <ul class="pagination">
      <li><a href="${rootPath }manage/MenuManager_${adss}?page=${allMenu.prePage}">上一页</a></li>
     			<c:forEach var="i" begin="${allMenu.currentPage-2>0?allMenu.currentPage-2:1 }"
 					end="${allMenu.currentPage+2>allMenu.pageNum?allMenu.pageNum:allMenu.currentPage+2  }">
 					<c:choose> 
						<c:when test="${i>0 && i == allMenu.currentPage}">
 							<li class="active"><a href="${rootPath }manage/MenuManager_${adss}?page=${i }">${i}</a></li> 
 						</c:when>
 						<c:when test="${i>0 && i != allMenu.currentPage}">
 							<li><a href="${rootPath }manage/MenuManager_${adss}?page=${i }">${i}</a></li> 
 						</c:when> 
					</c:choose> 
 				</c:forEach> 
      <li><a href="manage/MenuManager_${adss}?page=${allMenu.nextPage}">下一页</a></li>
    </ul>
   </div>
  </c:if>
</body>
</html>