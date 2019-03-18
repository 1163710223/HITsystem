package org.shop.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.shop.pojo.Student;
import org.shop.pojo.Teacher;
import org.shop.service.StudentService;
import org.shop.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import net.sf.json.JSONObject;
import tool.Md5;

@Controller
public class LoginController {

	@Autowired
	private StudentService ss;
	@Autowired
	private TeacherService ts;
	
	
	//Android
	@RequestMapping(value="login")
	public void doLogin(HttpServletRequest request,HttpServletResponse response) 
   {    
		response.setContentType("text/html;charset=utf-8");		
        try {
			request.setCharacterEncoding("utf-8");
		} catch (UnsupportedEncodingException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
        response.setCharacterEncoding("utf-8");
		String id = request.getParameter("id");
		String password = request.getParameter("password");
		//System.out.println(id+"   "+password);
		long num = Long.parseLong(id);
		long stuMin = 1000000000L;
	    long stuMax = 9999999999L;
		//老师的登录
		if(num>=10000000&&num<=99999999)
		{
			//System.out.println("进入老师");	
			List<Teacher> teachers = new ArrayList<>();
			try{
			teachers =  ts.queryById(id);
			}catch(Exception e)
			{
				e.printStackTrace();
			}
			
			
			boolean flag = false;
			if(teachers.isEmpty())
			{
				flag =false;
			//	System.out.println("没有查到数据");
			}	
			else{
				Teacher teacher = teachers.get(0);
			//	System.out.println(teacher.toString());
				if(teacher.getPassword().equals(password))
				{
					flag = true;
				}
				
			}
			JSONObject object = new JSONObject();
			if(flag)
			{
				object.put("status",true);
				object.put("msg","login success");
				
			}else{
				object.put("status",false);
				object.put("msg","login fail!");
			}
			try{
				PrintWriter write = response.getWriter();
				write.print(object);
			}catch(IOException e){
				e.printStackTrace();
			}
			
		    return ;
		}
		//学生的登录
		else if(num>=stuMin&&num<stuMax)
		{
			JSONObject object = new JSONObject();
			Student student = ss.queryById(id);
			String passwordMd =  Md5.md5(password);
			boolean flag = false;
			if(student==null)
			{
				flag=false;
			}
			else{
				if(student.getPassword().equals(passwordMd))
				{
					flag = true;
				}
				
			}
			if(flag)
			{
				object.put("status",true);
				object.put("msg","login success");
				
			}else{
				object.put("status",false);
				object.put("msg","login fail!");
			}
			try{
				PrintWriter write = response.getWriter();
				write.print(object);
			}catch(IOException e){
				e.printStackTrace();
			}
			return ;
			}
		  else{
				JSONObject object = new JSONObject();
				object.put("status",false);
				object.put("msg","no such id");
	
			try{
				PrintWriter write = response.getWriter();
				write.print(object);
			}catch(IOException e){
				e.printStackTrace();
			}
			return ;
			}
		}
	@RequestMapping(value="modifyPassword")
    public void modifyPassword(HttpServletRequest request,HttpServletResponse response)
    {
		response.setContentType("text/html;charset=utf-8");		
        try {
			request.setCharacterEncoding("utf-8");
		} catch (UnsupportedEncodingException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
        response.setCharacterEncoding("utf-8");
        
        
		   String id = request.getParameter("id");
		   String password =    request.getParameter("password");
		   long num = Long.parseLong(id);
			long stuMin = 1000000000L;
		    long stuMax = 9999999999L;
			//老师的登录
			if(num>=10000000&&num<=99999999)
			{
				System.out.println("进入老师");	
	             List<Teacher> teachers = ts.queryById(id);
	        	 JSONObject object = new JSONObject();
	             if(teachers.isEmpty())
	             {
	            
	            	 object.put("status", false);
	            	 object.put("msg", "No such teacher of this id!");
	             }
	             else if(teachers.size()>=2){
	            	 object.put("status", false);
	            	 object.put("msg", "Too many teachers of the same id!");
	             }
	             else{
	            	 Teacher teacher = teachers.get(0);
	            	 System.out.println("pw"+password);
	            	 ts.modifyTeacher(id, password, teacher.getName(), teacher.getPhonenum());
		            	 object.put("status", true);
		            	 object.put("msg", "The teacher's password is modified successfully!");
	             }
	             
	             PrintWriter writer;
				try {
					writer = response.getWriter();
					  writer.print(object);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	           
			    return ;
			}
			//学生的登录
			else if(num>=stuMin&&num<stuMax)
			{
				System.out.println("进入学生");
				Student student = ss.queryById(id);
                 String passwordMd = Md5.md5(password);
				JSONObject object = new JSONObject();
				if(student==null)
				{
				
	            	 object.put("status", false);
	            	 object.put("msg", "No such student of this id!");
				}
				else {
					ss.modifyStudent(id, passwordMd, student.getName(), student.getYear(), student.getPhonenum());
	            	 object.put("status", true);
	            	 object.put("msg", "The student's password is modified successfully!");
				}
				PrintWriter writer;
				try {
					writer = response.getWriter();
					writer.print(object);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				return ;
			}
			//学号或职工号不符合规定
			else{
				 JSONObject object = new JSONObject();
            	 object.put("status", false);
            	 object.put("msg", "The id is illegal!");
            		PrintWriter writer;
    				try {
    					writer = response.getWriter();
    					writer.print(object);
    				} catch (IOException e) {
    					// TODO Auto-generated catch block
    					e.printStackTrace();
    				}
			}
			
    }
    @RequestMapping(value="signup")
    public void signup(HttpServletRequest request,HttpServletResponse response)
    {
    	response.setContentType("text/html;charset=utf-8");		
        try {
			request.setCharacterEncoding("utf-8");
		} catch (UnsupportedEncodingException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
        response.setCharacterEncoding("utf-8");
        
    	String id = request.getParameter("id");
    	String password =request.getParameter("password");
    	String  year = request.getParameter("year");
    	String phone = request.getParameter("phone");
    	String nameStr = request.getParameter("name");
    	
    	Student student = ss.queryById(id);
    	if(student!=null)
    	{
    		 JSONObject object = new JSONObject();
        	 object.put("status", false);
        	 object.put("msg", "The student of this id has already existed!");
        		PrintWriter writer;
				try {
					writer = response.getWriter();
					writer.print(object);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return ;
    	}
    	else{
    		String name = nameStr;
			try {
				name = new String(nameStr.getBytes("iso-8859-1"),"utf-8");
			} catch (UnsupportedEncodingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
    		ss.addStudent(id, password, name, Integer.parseInt(year), phone);
    		 JSONObject object = new JSONObject();
        	 object.put("status", true);
        	 object.put("msg", "successful!");
        		PrintWriter writer;
				try {
					writer = response.getWriter();
					writer.print(object);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return ;
    	}
    	
    }
}


