<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
  <head> 
    <title>初始页面</title>
  </head>
<body>


<div class="header">
		<h1>
			<img src="assets/images/hithead.png" />
		</h1>
		
		<form action="queryAllStudents" method="post">
                <input type="submit" value="学生">
        </form>
		<form action="queryAllTeachers" method="post">
                  <input type="submit" value="老师">
        </form>
        <form action="queryAllSubjects" method="post">
                <input type="submit" value="课程">
        </form>
        <form action="queryAllDiscussions" method="post">
                <input type="submit" value="讨论区">
        </form>
	
</div>

</body>
</html>
