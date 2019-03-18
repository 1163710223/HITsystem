<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head> 
    <title>课程</title>
  </head>
  
  <body>
 <form action="querySubjectBySubjectId" method="post">
    <label for="firstname" >课程ID</label>
      <input type="text"  name ="id" placeholder="请输入课程ID">
    <div>
      <input type="submit" value="查找">
    </div>
</form>


<table style="width:100%;border:1px white solid">
    <tr bgcolor="#4F81BD"style="color: #fff;">
        <th style="text-align: center">课程编号</th>
        <th style="text-align: center">课程名称</th>
        <th style="text-align: center">教师id</th>
        <th style="text-align: center">学生id</th>
         <th style="text-align: center">分数</th>
    </tr>
    <c:forEach items="${subjects}" var="subject" varStatus="status">
        <tr bgcolor="${status.index%2 == 0?'#D0D8E8':'#E9EDF4'}">         
            <td align="center">${subject.subject_id}</td>
             <td align="center">${subject.subject_name}</td>
             <td align="center">${subject.teacher_id}</td>
             <td align="center">${subject.student_id}</td>
             <td align="center">${subject.score}</td>
        </tr>
    </c:forEach>
</table>






  </body>
</html>
