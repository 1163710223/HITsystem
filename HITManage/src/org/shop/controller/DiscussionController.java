package org.shop.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.shop.pojo.Discussion;
import org.shop.service.DiscussionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
public class DiscussionController {

	@Autowired
	private DiscussionService ds;
	
	@RequestMapping(value="/queryAllDiscussions" ,method=RequestMethod.POST)
	public String queryAllDiscussions(Map<String,List<Discussion>> map)
	{
		
    	List<Discussion> discussions=	ds.queryAllDiscussions();
	    map.put("discussions", discussions);
		return "discussion";
	}
	
	@RequestMapping(value="/queryDiscussionByDiscussionId" ,method=RequestMethod.POST)
	public String queryDiscussionByDiscussionId(@RequestParam String id,Map<String,List<Discussion>> map)
	{
		
    	List<Discussion> discussions= new ArrayList<Discussion>();
    	Discussion discussion = ds.queryDiscussionByDiscussionById(id);
    	discussions.add(discussion);
	    map.put("discussions", discussions);
		return "discussion";
	}
	@RequestMapping(value="addDiscussion",method =RequestMethod.POST)
	public String addDiscussion(@RequestParam String topic,
			@RequestParam String name,@RequestParam String teacherId,@RequestParam String studentId
			,@RequestParam String teacherWords,@RequestParam String studentWords,
			Map<String,List<Discussion>> map)
	{	
		ds.addDiscussion(topic, name, teacherId, studentId, teacherWords, studentWords);
		List<Discussion> discussions = ds.queryAllDiscussions();
	    map.put("discussions", discussions);
		return "discussion";
	}
	
	
	//Android
	//请求所有的讨论名称
	@RequestMapping(value = "reqalldis")
	public void reqalldis(HttpServletRequest request,HttpServletResponse response)
	{
		response.setContentType("text/html;charset=utf-8");		
        try {
			request.setCharacterEncoding("utf-8");
		} catch (UnsupportedEncodingException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
        response.setCharacterEncoding("utf-8");
        
        
		List<Discussion> discussions = ds.queryAllDiscussions();
		Set<String> set = new HashSet<>();
		List<String> names  = new ArrayList<>();
		for(Discussion d:discussions)
		{
			if(!set.contains(d.getName()))
			{
				names.add(d.getName());
			}
			set.add(d.getName());
			
		}
		JSONArray object = new JSONArray();
		object.add(names);
		try {
			PrintWriter writer = response.getWriter();
			writer.print(object);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	@RequestMapping(value = "reqdis")
	public void reqdis(HttpServletRequest request,HttpServletResponse response)
	{
		response.setContentType("text/html;charset=utf-8");		
        try {
			request.setCharacterEncoding("utf-8");
		} catch (UnsupportedEncodingException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
        response.setCharacterEncoding("utf-8");
        
		try {
			request.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String nameStr = request.getParameter("disname");
		
		String name="";
		try {
			name = new String(nameStr.getBytes("iso-8859-1"),"utf-8");
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		System.out.println(name);
		
		List<Discussion> discussions = ds.queryDiscussionByDiscussionName(name);
	
		
		List<Discussion> newDiscussions = new ArrayList<>();
		
		
		for(Discussion d:discussions)
		{
			if((d.getStudent_id()!=null&&d.getStudent_words()!=null)||(d.getTeacher_id()!=null&&d.getTeacher_words()!=null))
			{
				newDiscussions.add(d);
				//System.out.println(d.toString());
			}
			//System.out.println("没有筛选"+d.toString());
		
		}
	
		JSONArray object = new JSONArray();
		object.add(newDiscussions);
		System.out.println(newDiscussions.toString());
		try {
			PrintWriter writer = response.getWriter();
			writer.print(object);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@RequestMapping(value = "newDiscussion")
	public void newDiscussion(HttpServletRequest request,HttpServletResponse response)
	{
		response.setContentType("text/html;charset=utf-8");		
        try {
			request.setCharacterEncoding("utf-8");
		} catch (UnsupportedEncodingException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
        response.setCharacterEncoding("utf-8");
		String topic = request.getParameter("topic");
		String name = request.getParameter("name");
		try {
			String newTopic = new String(topic.getBytes("ISO-8859-1"),"utf-8");
			String newName = new String(name.getBytes("ISO-8859-1"),"utf-8");
			ds.addDiscussion(newTopic,newName, null, null, null, null);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		JSONObject object = new JSONObject();
		object.put("status", true);
		try {
			PrintWriter writer = response.getWriter();
			writer.print(object);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@RequestMapping(value = "addDiscussion")
	public void addDiscussion(HttpServletRequest request,HttpServletResponse response)
	{
		response.setContentType("text/html;charset=utf-8");		
        try {
			request.setCharacterEncoding("utf-8");
		} catch (UnsupportedEncodingException e2) {
		//	System.out.println("here i am 1111111111111");
			e2.printStackTrace();
		}
        response.setCharacterEncoding("utf-8");
        
        
	   //	String topic = request.getParameter("topic");
		String nameStr = request.getParameter("disname");
		String contentStr = request.getParameter("content");
		String id = request.getParameter("id");
		
	
	//	System.out.println(id);
		
		
		String name="";
		String content="";
		try {
			name = new String(nameStr.getBytes("iso-8859-1"),"utf-8");
			 content = new String(contentStr.getBytes("iso-8859-1"),"utf-8");
		} catch (UnsupportedEncodingException e1) {
			System.out.println("here 222222222222");
			e1.printStackTrace();
		}
		
		
		
	//	System.out.println(name);
	//	System.out.println(content);
	//	System.out.println(id);
		
		long num = Long.parseLong(id);
		long stuMin = 1000000000L;
	    long stuMax = 9999999999L;
		//老师的登录
		if(num>=10000000&&num<=99999999)
		{
			ds.addDiscussion(name,name, id, null, content, null);
			JSONObject object = new JSONObject();
			object.put("status", true);
			object.put("msg", "The teacher speaks successfully!");
			try {
				PrintWriter writer = response.getWriter();
				writer.print(object);
			} catch (IOException e) {
			//	System.out.println("33333333");
				e.printStackTrace();
			}
		    return ;
		}
		//学生的登录
		else if(num>=stuMin&&num<stuMax)
		{
			ds.addDiscussion(name,name, null, id, null, content);
			JSONObject object = new JSONObject();
			object.put("status", true);
			object.put("msg", "The student speaks successfully!");
			try {
				PrintWriter writer = response.getWriter();
				writer.print(object);
			} catch (IOException e) {
				System.out.println("4444444");
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
}
