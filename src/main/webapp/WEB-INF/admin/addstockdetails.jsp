<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body   style="background: url(${rootPath}/images/m-91.jpg);">
	<form action="${rootPath}manage/StockDetailsAction_AddDetails" method="post">
     <h4>${addMsg}</h4>
     <lable>配料名称:</lable>
      <select name="details.ingName"> 
 			<c:forEach items="${Ientlist}" var="item">  
 	           	<option value="${item.name}">${item.name}</option> 
         	</c:forEach> 
 	</select><br>
     <lable>重量(kg):</lable><input type="text" name="details.num"><br>
     <lable>价格(kg):</lable><input type="text"  name="details.price"><br>
      <div class="button">
        <input type="submit" value="提交">          
      </div>
    </form>   
</body>
</html>