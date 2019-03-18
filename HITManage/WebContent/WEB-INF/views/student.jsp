<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head> 
    <title>学生信息</title>
  </head>
<body>  
	
<form action="queryById" method="post">
    <label for="firstname" >用户ID</label>
      <input type="text"  name ="id" placeholder="请输入学生ID">
    <div>
      <input type="submit" value="查找">
    </div>
</form>
<form action="addStudent" method="post">
      <div>
         <label for="firstname" >学生ID</label>
         <input type="text"  name ="id" placeholder="请输入学生ID">
      </div>
      <div>
          <label for="firstname" >学生密码</label>
          <input type="text"  name ="password" placeholder="请输入学生密码">
      </div>
      <div>
           <label for="firstname" >学生姓名</label>
           <input type="text"  name ="name" placeholder="请输入学生姓名">
      </div>
      <div>
          <label for="firstname" >学生入学年份</label>
           <input type="text"  name ="year" placeholder="请输入学生入学年份">
      </div>
      <div>
            <label for="firstname" >学生手机号码</label>
            <input type="text"  name ="phonenum" placeholder="请输入学生电话号码">
      </div>  
    <div>
      <input type="submit" value="添加学生">
    </div>
</form>
<form action="deleteStudent" method="post">
    <label for="firstname" >用户ID</label>
      <input type="text"  name ="id" placeholder="请输入学生ID">
    <div>
      <input type="submit" value="删除">
    </div>
</form>

<form action="modifyStudent" method="post">
      <div>
         <label for="firstname" >学生ID</label>
         <input type="text"  name ="id" placeholder="请输入学生ID">
      </div>
      <div>
          <label for="firstname" >学生密码</label>
          <input type="text"  name ="password" placeholder="请输入学生密码">
      </div>
      <div>
           <label for="firstname" >学生姓名</label>
           <input type="text"  name ="name" placeholder="请输入学生姓名">
      </div>
      <div>
          <label for="firstname" >学生入学年份</label>
           <input type="text"  name ="year" placeholder="请输入学生入学年份">
      </div>
      <div>
            <label for="firstname" >学生手机号码</label>
            <input type="text"  name ="phonenum" placeholder="请输入学生电话号码">
      </div>  
    <div>
      <input type="submit" value="修改学生信息">
    </div>
</form>
<table style="width:100%;border:1px white solid">
    <tr bgcolor="#4F81BD"style="color: #fff;">
        <th style="text-align: center">学号</th>
        <th style="text-align: center">姓名</th>
        <th style="text-align: center">入学年份</th>
        <th style="text-align: center">手机号</th>
    </tr>
    <c:forEach items="${students}" var="student" varStatus="status">
        <tr bgcolor="${status.index%2 == 0?'#D0D8E8':'#E9EDF4'}">         
            <td align="center">${student.id}</td>
             <td align="center">${student.name}</td>
             <td align="center">${student.year}</td>
             <td align="center">${student.phonenum}</td>
        </tr>
    </c:forEach>
</table>


 
   
</body>
</html>
