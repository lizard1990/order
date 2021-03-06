<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html >
<html>
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width,initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no"/>
	<title>餐饮管理系统</title>
	<link rel="stylesheet" type="text/css" href="${rootPath}css/customer/bootstrap.min.css">
	<link rel="stylesheet" href="${rootPath}css/customer/public.css">
	<link rel="stylesheet" href="${rootPath}css/customer/show.css">
<script type="text/javascript">
	function addShopCart(id) {
		var xmlhttp;
		if (window.XMLHttpRequest) {
			// IE7+, Firefox, Chrome, Opera, Safari 浏览器执行代码
			xmlhttp = new XMLHttpRequest();
		} else {
			// IE6, IE5 浏览器执行代码
			xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
		}
		xmlhttp.onreadystatechange = function() {
			if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {		
				var result = xmlhttp.responseText;								
				if(parseInt(result)==1){
					show_notice('添加成功',1);
				}else if(parseInt(result)==0){
					show_notice('配料不足',1);
				}
				else{
					show_notice('添加失败',1);
				}
							
			}
		}
		xmlhttp.open("POST", "customer_joinCart", true);
		xmlhttp.setRequestHeader("Content-type",
				"application/x-www-form-urlencoded");
		xmlhttp.send("id="+id);
	}
	
	
	function show_notice(str,second,callback){  
	    var box_id = 'notice_box';  
	    var tooltipBox = document.getElementById(box_id);  
	    if(tooltipBox){  
	        document.body.removeChild(tooltipBox);  
	    }  
	    if(!second) second = 2;  
	    tooltipBox = document.createElement('div');  
	    tooltipBox.innerHTML = str;  
	    tooltipBox.id = box_id;  
	    tooltipBox.style.background='rgba(94,94,94,.8)';  
	    tooltipBox.style.color='#fff';  
	    tooltipBox.style.display='inline-block';  
	    tooltipBox.style.padding = '0.4em 1.5em';  
	    tooltipBox.style.borderRadius = '1em';  
	    tooltipBox.style.fontSize = '0.9em';  
	    document.body.appendChild(tooltipBox);  
	    var vWidth = document.documentElement.clientWidth;  
	    var vHeight = document.documentElement.clientHeight;  
	    tooltipBox.style.position = 'fixed';  
	    tooltipBox.style.zIndex = '9999';  
	    tooltipBox.style.left = ((vWidth/2)-(tooltipBox.offsetWidth/2))+'px';  
	    tooltipBox.style.top = ((vHeight/2)-(tooltipBox.offsetHeight/2))+'px';  
	    setTimeout(function () {  
	                    document.body.removeChild(tooltipBox);  
	                    if(callback)    callback();  
	                }, second*1000);  
	}  
</script>
</head>
<body>
      <!-- 头部 店铺与联系方式-->
   <div class="header">
     <div class="banner">
     	<img src="${rootPath}uploadImg/${r.imgUrl}">
     </div>
     <div class="information">
         <div>
       		地址: ${r.address}
         </div>
     	<div>
     	    电话: ${r.phone}
     	</div>
     </div>
   </div>
   <!-- 菜品的分类 -->
   
   <div class="classification">
    <c:forEach items="${menuType}" var="mT">
    <a href="${rootPath}customer/customer_getMenuByTypeId?id=${mT.id}" title=""><div class="one left">
	   	 	<img src="${rootPath}uploadImg/${mT.imgUrl}">
	   	 	<p>${mT.name}</p>
	   	 </div></a>
   </c:forEach>
   	</div>
   <!-- 菜品的展示 -->
   <c:if test="${!empty menus}">
   <c:forEach items="${menus}" var="m" >
   <div class="show">
       <!-- 以下层级每一个块   都会是一道菜 -->
	 
	    <div class="food">
		      <a href="${rootPath}customer/customer_getMenuMaterial?id=${m.id}">
	            <div class="left photo">
	            	<img src="${rootPath}uploadImg/${m.imgUrl}" alt="" width="100px" height="90px">
	            </div>
	      	  </a>
            <div class="left indu">
            	<div class="name">
            		<b> <a href="${rootPath}customer/customer_getMenuMaterial?id=${m.id}">${m.name}</a></b>
            	</div>
            	<div class="indu2">
            		${m.introduce}
            	</div>
            	<div class="di">
            	<p class="left p1">￥${m.price}</p>
              <p class="left p2">销量: ${m.num}</p>           
            	<input type="button" name="" value="+" class="right" onclick="addShopCart(${m.id})" />
            	</div>
            </div>
	    </div>
	    
       </div>
     </c:forEach>
    </c:if>
    <c:if test="${empty menus}">
    <center><h1 class="margin">该类型暂无菜品</h1></center>
    </c:if>
     <!-- 底部留白 -->
       <div class="liubai">
       	
       </div>
     <!-- 底部固定的菜单 -->
   <footer>
     <a href="${rootPath}customer/customer_toIndex?id=${userId}">
      <div class="left">
      	<div><img src="${rootPath}images/sy.png" style="width:100%; height:100%; "></div>
      	
      	<p>首页</p>
      </div>
      </a>
      <a href="${rootPath}customer/cart_getCart">
      <div class="left">
      	<div><img src="${rootPath}images/gwc.png" style="width:100%; height:100%; "></div>
      	<p>购物车</p>
      </div>
      </a>
      <a href="${rootPath}customer/cart_orderDetails">
      <div class="left">
      	<div><img src="${rootPath}images/dd.png" style="width:70%; height:70%;"></div>
      	<p>订单</p>
      </div>
      </a>
   </footer>
</body>
</html>