<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<center>
	${updateMsg}${deleteMenuMsg }
	共有数据${allMenu.count}条
		<table>
			<c:forEach items="${allMenu.data}" var="data"> 
				<tr>
					<td><img alt="加载中" src=""></td>
				</tr>
				<tr>
					<td><a href="${rootPath}manage/MenuManager_toUpdateMenu?menu.id=${data.id}">${data.name}&nbsp;&nbsp;&nbsp;${data.typeName}</a></td>
				</tr>
			</c:forEach>
		</table>
		
		<a href="${rootPath }manage/MenuManager?page=${allMenu.prePage}">上一页</a>
		
		<c:forEach var="i" begin="${allMenu.currentPage-3>0?allMenu.currentPage-3:1 }"
					end="${allMenu.currentPage+3>allMenu.pageNum?allMenu.pageNum:allMenu.currentPage+3  }">
					<c:choose>
						<c:when test="${i>0 && i == allMenu.currentPage }">
							<li class="active"><a href="${rootPath }manage/MenuManager?page=${i }">${i}</a></li>
						</c:when>
						<c:when test="${i>0 && i != allMenu.currentPage }">
							<li><a href="${rootPath }manage/MenuManager?page=${i }">${i}</a></li>
						</c:when>
					</c:choose>
				</c:forEach>
			<a href="${rootPath }manage/MenuManager?page=${allMenu.nextPage}">下一页</a>
	</center>
</body>
</html>