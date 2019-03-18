<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ page import="org.shop.pojo.*"%> 

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
//请求内容转码
request.setCharacterEncoding("UTF-8");
//回传告诉浏览器展示编码
response.setContentType("text/html;charset=UTF-8");
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head> 
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>老师信息</title>
  </head>
  
  <body>
<form action="queryTeacherById" method="post">
	  <label for="firstname" >教师ID</label>
      <input type="text"  name ="id" placeholder="请输入教师ID">
      <input type="submit" value="查询">
</form>
<form action="addTeacher" method="post">
      <div>
         <label for="firstname" >老师ID</label>
         <input type="text"  name ="id" placeholder="请输入老师ID">
      </div>
      <div>
          <label for="firstname" >老师密码</label>
          <input type="text"  name ="password" placeholder="请输入老师密码">
      </div>
      <div>
           <label for="firstname" >老师姓名</label>
           <input type="text"  name ="name" placeholder="请输入老师姓名">
      </div>
      <div>
            <label for="firstname" >老师手机号码</label>
            <input type="text"  name ="phonenum" placeholder="请输入老师电话号码">
      </div>  
    <div>
      <input type="submit" value="添加老师">
    </div>
</form>
<form action="deleteTeacherById" method="post">
    <label for="firstname" >老师ID</label>
      <input type="text"  name ="id" placeholder="请输入老师ID">
    <div>
      <input type="submit" value="删除">
    </div>
</form>

<form action="modifyTeacher" method="post">
      <div>
         <label for="firstname" >老师ID</label>
         <input type="text"  name ="id" placeholder="请输入老师ID">
      </div>
      <div>
          <label for="firstname" >老师密码</label>
          <input type="text"  name ="password" placeholder="请输入老师密码">
      </div>
      <div>
           <label for="firstname" >老师姓名</label>
           <input type="text"  name ="name" placeholder="请输入老师姓名">
      </div>
      <div>
            <label for="firstname" >老师手机号码</label>
            <input type="text"  name ="phonenum" placeholder="请输入老师电话号码">
      </div>  
    <div>
      <input type="submit" value="修改老师信息">
    </div>
</form>

      <table  style="width:100%;border:1px white solid">
     	 <tr bgcolor="#4F81BD"style="color: #fff;">
       	     <th style="text-align: center">职工号</th>
       	     <th style="text-align: center">姓名</th>
        	 <th style="text-align: center">手机号</th>
   		 </tr>
  		  <c:forEach items="${teachers}" var="teacher" varStatus="status">
            <tr bgcolor="${status.index%2 == 0?'#D0D8E8':'#E9EDF4'}">         
          		  <td align="center">${teacher.id}</td>
          	 	  <td align="center">${teacher.name}</td>
           		  <td align="center">${teacher.phonenum}</td>
       		 </tr>
   		 </c:forEach>
      </table>  
        
         

  </body>
</html>
