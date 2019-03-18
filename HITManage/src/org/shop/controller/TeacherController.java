package org.shop.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;
import tool.Md5;

import org.shop.pojo.Teacher;
import org.shop.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class TeacherController {

	@Autowired
	private TeacherService ts;
	
	@RequestMapping(value="/queryAllTeachers" ,method=RequestMethod.POST)
	public String queryAllTeachers(Map<String,List<Teacher>> map)
	{
		List<Teacher> teachers = ts.queryAllTeachers();
        map.put("teachers", teachers);
		return "teacher";
		
	}
	@RequestMapping(value="/queryTeacherById",method=RequestMethod.POST)
	public String queryById(@RequestParam String id,Map<String,List<Teacher>> map)
	{
		
		List<Teacher> teachers = ts.queryById(id);
		if(id==null||id.equals("")){
			teachers=ts.queryAllTeachers();
		}	
		map.put("teachers", teachers);
		return "teacher";
		
	}
	@RequestMapping(value="/addTeacher" ,method=RequestMethod.POST)
	public String addTeacher(@RequestParam String id,@RequestParam String password,
			@RequestParam String name,@RequestParam String phonenum,HttpServletRequest request,HttpServletResponse response)
	{
		response.setContentType("text/html;charset=utf-8");		
        try {
			request.setCharacterEncoding("utf-8");
		} catch (UnsupportedEncodingException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
        response.setCharacterEncoding("utf-8");
		
		String newName = null;
		
		try {
			newName = new String(name.getBytes("iso-8859-1"),"utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		String newPassword = Md5.md5(password);
		ts.addTeacher(id, newPassword, newName, phonenum);
		return "teacher";
	}
	@RequestMapping(value="/deleteTeacherById",method=RequestMethod.POST)
	public String deleteById(@RequestParam String id )
	{
		ts.deleteById(id);
		return "teacher";
	}
	@RequestMapping(value="/modifyTeacher",method=RequestMethod.POST)
	public String modifyTeacher(@RequestParam String id,@RequestParam String password,
			@RequestParam String name,@RequestParam String phonenum)
	{
		ts.modifyTeacher(id, password, name, phonenum);
		return "teacher";
	}
	
	
	//Android
    @RequestMapping(value="reqteamsg")
	public void reqteamsg(HttpServletRequest request,HttpServletResponse response)
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
		List<Teacher> tea = ts.queryById(id);
		JSONObject object = JSONObject.fromObject(tea.get(0));
		try{
			PrintWriter write = response.getWriter();
			//write.write(object.toString());
			write.print(object);
		}catch(IOException e){
			e.printStackTrace();
		}
		
}
}
