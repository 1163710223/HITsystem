<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>讨论区</title>
  </head>
  
  <body>
  
  <form action="queryDiscussionByDiscussionId" method="post">
    <label for="firstname" >讨论ID</label>
      <input type="text"  name ="id" placeholder="请输入讨论ID">
    <div>
      <input type="submit" value="查找">
    </div>
</form>
 <table style="width:100%;border:1px white solid">
    <tr bgcolor="#4F81BD"style="color: #fff;">
        <th style="text-align: center">讨论id</th>
        <th style="text-align: center">讨论主题</th>
        <th style="text-align: center">老师id</th>
        <th style="text-align: center">学生id</th>
        <th style="text-align: center">老师发言</th>
        <th style="text-align: center">老师发言</th>
    </tr>
    <c:forEach items="${discussions}" var="discussion" varStatus="status">
        <tr bgcolor="${status.index%2 == 0?'#D0D8E8':'#E9EDF4'}">         
            <td align="center">${discussion.discussion_id}</td>
             <td align="center">${discussion.topic}</td>
             <td align="center">${discussion.teacher_id}</td>
             <td align="center">${discussion.student_id}</td>
             <td align="center">${discussion.teacher_words}</td>
             <td align="center">${discussion.student_words}</td>
        </tr>
    </c:forEach>
</table>



<form action="addDiscussion" method ="post">
<div>
<label for="firstname">讨论编号</label>
<input type="text" name="discussionId" placeholder="讨论编号">
</div>

<div>
<label for="firstname">主题</label>
<input type="text" name="topic" placeholder="主题">
</div>
<div>
<label for="firstname">内容</label>
<input type="text" name="name" placeholder="内容">
</div>
<div>
<label for="firstname">老师ID</label>
<input type="text" name="teacherId" placeholder="老师Id">
</div>
<div>
<label for="firstname">学生ID</label>
<input type="text" name="studentId" placeholder="学生Id">
</div>

<div>
<label for="firstname">老师发言</label>
<input type="text" name="teacherWords" placeholder="老师发言">
</div>

<div>
<label for="firstname">学生发言</label>
<input type="text" name="studentWords" placeholder="学生发言">
</div>
<div>
<input type = "submit" value="添加讨论">
</div>

</form>
  </body>
</html>
