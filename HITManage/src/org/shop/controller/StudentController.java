package org.shop.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.shop.pojo.Student;
import org.shop.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class StudentController {

	@Autowired
	private StudentService ss;

	//����id��ѯѧ��
	@RequestMapping(value = "/queryById",method = RequestMethod.POST)
	public String queryById(@RequestParam String id ,Map<String,List<Student>> map) {
		String quyeryid = id ;
		Student student = ss.queryById(quyeryid);		
		List<Student> students = new ArrayList<Student>();
		students.add(student);
		map.put("students", students);
		return "student";
	}
	//��ѯ����ѧ������Ϣ
    @RequestMapping(value = "/queryAllStudents",method = RequestMethod.POST)
    public String queryAllStudents(Model model,Map<String,List<Student>> map ) {
        List<Student> students = ss.queryAllStudents() ;
        map.put("students", students);
        return "student";
    }
    //���ѧ��
    @RequestMapping(value="/addStudent",method=RequestMethod.POST)
    public String addStuent(@RequestParam String id,@RequestParam String password,
    		@RequestParam String name,@RequestParam String year,
    		@RequestParam String phonenum,Map<String,List<Student>> map)
    {
    	//��ѯ�Ƿ��Ѿ��������ѧ��
    	Student student = ss.queryById(id);	
    	if(student==null){   //���������ѧ�ŵ�ѧ��
    		int yearnum = Integer.parseInt(year);
        	ss.addStudent(id, password, name, yearnum, phonenum);
    	}
    	
    	//��ʾ�����е�ѧ��
    	List<Student> students = ss.queryAllStudents() ;
        map.put("students", students);
		return "student";
    	
    }
    //�޸�ѧ����Ϣ
    @RequestMapping(value="/modifyStudent",method=RequestMethod.POST)
    public String modifyStuent(@RequestParam String id,@RequestParam String password,
    		@RequestParam String name,@RequestParam String year,
    		@RequestParam String phonenum,Map<String,List<Student>> map)
    {
    	//��ѯ�Ƿ��Ѿ��������ѧ��
    	Student student = ss.queryById(id);	
    	if(student!=null){   //�������ѧ�ŵ�ѧ��,��������Ϣ�����޸�
    		int yearnum = Integer.parseInt(year);
        	ss.modifyStudent(id, password, name, yearnum, phonenum);
    	}
    	
    	//��ʾ�����е�ѧ��
    	List<Student> students = ss.queryAllStudents() ;
        map.put("students", students);
		return "student";
    	
    }
   
    @RequestMapping(value = "/deleteStudent",method = RequestMethod.POST)
    public String deleteStudent(@RequestParam String id,Map<String,List<Student>> map ) {
   
    	Student student = ss.queryById(id);	
    	if(student!=null){   
    		ss.deleteStudent(id);
    	}  
        List<Student> students = ss.queryAllStudents() ;
        map.put("students", students);
        return "student";
    }
    
    
    //Android
    @RequestMapping(value="reqstumsg")
	public void reqstumsg(HttpServletRequest request,HttpServletResponse response)
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
		Student stu = ss.queryById(id);
	     String nameStr = stu.getName();
	  //  System.out.println(nameStr);
	    JSONObject object =   JSONObject.fromObject(stu);
	    /**
		try {
			String s_gbk = new String(nameStr.getBytes("ISO-8859-1"),"GBK");
			String s_utf8 = new String(nameStr.getBytes("ISO-8859-1"),"UTF-8");
			String s_iso88591 = new String(nameStr.getBytes("ISO-8859-1"),"ISO-8859-1");
			String s_unicode = new String(nameStr.getBytes("ISO-8859-1"),"unicode");
			
			System.out.println("s_gbk"+s_gbk);
			System.out.println("s_utf8"+s_utf8);
			System.out.println("s_iso88591"+s_iso88591);
			System.out.println("s_unicode"+s_unicode);
			object.put("s_gbk", s_gbk);
			object.put("s_utf8",s_utf8);
			object.put("s_iso88591", s_iso88591);
			object.put("s_unicode", s_unicode);
			
			
			//String name = new String(nameStr.getBytes("ISO-8859-1"),"ISO-8859-1");
			//stu.setName(name);
		//	System.out.println(name);
			
			System.out.println("**********************");
			String s_gbk1 = new String(nameStr.getBytes("UTF-8"),"GBK");
			String s_utf81 = new String(nameStr.getBytes("UTF-8"),"UTF-8");
			String s_iso885911 = new String(nameStr.getBytes("UTF-8"),"ISO-8859-1");
			String s_unicode1 = new String(nameStr.getBytes("UTF-8"),"unicode");
			
			System.out.println("s_gbk1"+s_gbk1);
			System.out.println("s_utf81"+s_utf81);
			System.out.println("s_iso885911"+s_iso885911);
			System.out.println("s_unicode1"+s_unicode1);
			object.put("s_gbk1", s_gbk1);
			object.put("s_utf81",s_utf81);
			object.put("s_iso885911", s_iso885911);
			object.put("s_unicode1", s_unicode1);
			System.out.println("**********************");
			
			String s_gbk2 = new String(nameStr.getBytes("GBK"),"GBK");
			String s_utf82 = new String(nameStr.getBytes("GBK"),"UTF-8");
			String s_iso885912 = new String(nameStr.getBytes("GBK"),"ISO-8859-1");
			String s_unicode2 = new String(nameStr.getBytes("GBK"),"unicode");
			
			System.out.println("s_gbk2"+s_gbk2);
			System.out.println("s_utf82"+s_utf82);
			System.out.println("s_iso885912"+s_iso885912);
			System.out.println("s_unicode2"+s_unicode2);
			object.put("s_gbk2", s_gbk2);
			object.put("s_utf82",s_utf82);
			object.put("s_iso885912", s_iso885912);
			object.put("s_unicode2", s_unicode2);
			
		} catch (UnsupportedEncodingException e1) {
			System.out.println("没有osi-8859-1");
			e1.printStackTrace();
		}
		
	*/
		try{		    
			PrintWriter write = response.getWriter();
			write.print(object);
		//	System.out.println(jsonObject.toString());
		}catch(IOException e){
			e.printStackTrace();
		}
		

}
    @RequestMapping(value="addStudentByAndroid")
    public void addStudentByAndroid(HttpServletRequest request ,HttpServletResponse response)
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
    	Student student = ss.queryById(id);
    	if(student != null)
    	{
    		JSONObject object = new JSONObject();
        	object.put("status", false);
        	String msg = "There is already this student!";
			object.put("msg", msg);
		
        	try {
				PrintWriter writer = response.getWriter();
				writer.print(object);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	return ;
        	
    	}
    	String password = request.getParameter("password");
    	String name = request.getParameter("name");
    	String year =request.getParameter("year");
    	String phonenum = request.getParameter("phonenum");
    	ss.addStudent(id, password, name, Integer.parseInt(year), phonenum);
    	JSONObject object = new JSONObject();
    	object.put("status", true);
    	object.put("msg", "successful");
    	try {
			PrintWriter writer = response.getWriter();
			writer.print(object);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return ;
    	
    }
    @RequestMapping(value="modifyStudentByAndroid")
    public void modifyStudentByAndroid(HttpServletRequest request ,HttpServletResponse response)
    {
		response.setContentType("text/html;charset=utf-8");		
        try {
			request.setCharacterEncoding("utf-8");
		} catch (UnsupportedEncodingException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
        response.setCharacterEncoding("utf-8");
      String id =  request.getParameter("id");
      String password = request.getParameter("password");
      String nameStr = request.getParameter("name");
      String yearStr = request.getParameter("year");
      String phonenum = request.getParameter("phonenum");
      
      int year = Integer.parseInt(yearStr);
      String name = nameStr;
	try {
		name = new String(nameStr.getBytes("ISO-8859-1"),"utf-8");
	} catch (UnsupportedEncodingException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
      Student student = ss.queryById(id);
      if(student ==null)
      {
    	  JSONObject object = new JSONObject();
      	object.put("status", false);
      	object.put("msg", "the student does not exist");
      	try {
  			PrintWriter writer = response.getWriter();
  			writer.print(object);
  		} catch (IOException e) {
  			// TODO Auto-generated catch block
  			e.printStackTrace();
  		}
      	return ;
      }else{
    	  ss.modifyStudent(id, password, name, year, phonenum);
    	  JSONObject object = new JSONObject();
        	object.put("status", true);
        	object.put("msg", "successful");
        	try {
    			PrintWriter writer = response.getWriter();
    			writer.print(object);
    		} catch (IOException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
        	return ;
      }
      
    }
}
