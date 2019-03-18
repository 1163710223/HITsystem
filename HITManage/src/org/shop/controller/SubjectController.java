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

import org.shop.pojo.Subject;
import org.shop.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


@Controller
public class SubjectController {
	
  @Autowired
  private SubjectService ss;
  
  @RequestMapping(value="/queryAllSubjects" ,method=RequestMethod.POST)
  public String queryAllSubjects(Map<String,List<Subject>> map)
  {
	 List<Subject> subjects =  ss.queryAllSubjects();
	 map.put("subjects", subjects);
	return "subject";
	  
  }
  @RequestMapping(value="/querySubjectBySubjectId",method= RequestMethod.POST)
  public String querySubjectBySubjectId(@RequestParam String id ,Map<String,List<Subject>> map)
  {
	  List<Subject> subjects = new ArrayList<>();
	  if(id.equals("")||id ==null)
	  {
		  subjects = ss.queryAllSubjects();
	  }else{
		  subjects = ss.querySubjectBySubjectId(id);
	  }	
		 map.put("subjects", subjects);
	  return "subject";
  }
  //Android
  @RequestMapping(value="reqallsubject")
  public void reqallsubject(HttpServletRequest request,HttpServletResponse response)
  {
		response.setContentType("text/html;charset=utf-8");		
        try {
			request.setCharacterEncoding("utf-8");
		} catch (UnsupportedEncodingException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
        response.setCharacterEncoding("utf-8");

        
	  List<Subject> subjects = ss.queryAllSubjects();
	  List<String> strList= new ArrayList<String>();
	  for(Subject s :subjects)
	  {		 
			  strList.add(s.getSubject_name());
	  }
	  JSONArray object = JSONArray.fromObject(strList);
	  
	  try {
		PrintWriter pw = response.getWriter();
		pw.print(object);
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	  
  }
  @RequestMapping(value="reqSubjectByAndroid")
  public void reqsubject(HttpServletRequest request,HttpServletResponse response)
  {
		response.setContentType("text/html;charset=utf-8");		
        try {
			request.setCharacterEncoding("utf-8");
		} catch (UnsupportedEncodingException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
        response.setCharacterEncoding("utf-8");
	 String subjectName = request.getParameter("subjectname");
	 String studentId = request.getParameter("student_id");
	 List<Subject> subject = ss.querySubjectByStudnetIdAndSubjectName(studentId, subjectName);
	 if(subject.size()==0)
	 {
		 JSONObject object = new JSONObject();
		 object.put("status", false);
	     object.put("msg", "Student not Found!");
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
	 else if(subject.size()>=2)
	 {
		 JSONObject object = new JSONObject();
		 object.put("status", false);
		 object.put("msg", "error with database");
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
		 JSONObject object =  new JSONObject();
		 object.put("status", true);
		 object.put("msg", "successful");
		 object.put("data", subject);
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
  @RequestMapping(value="reqsubscore")
  public void reqsbuscore(HttpServletRequest request,HttpServletResponse response)
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
	} catch (UnsupportedEncodingException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}	  
	  String id = request.getParameter("subjectId");
	//  System.out.println(id);
	  List<Subject> subjects = ss.querySubjectBySubjectId(id);
	                                            
	  JSONObject object =new JSONObject();
	  object.put("status", true);
	  object.put("subjects", subjects);
	  try {
		PrintWriter writer = response.getWriter();
		writer.print(object);
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	  
  }
 
  @RequestMapping(value="addSubjectByAndroid" )
  public void addSubjectByAndroid(HttpServletRequest request ,HttpServletResponse response)
  {
		response.setContentType("text/html;charset=utf-8");		
        try {
			request.setCharacterEncoding("utf-8");
		} catch (UnsupportedEncodingException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
        response.setCharacterEncoding("utf-8");

	String subjectId =  request.getParameter("id");
	String subjectName =  request.getParameter("subjectName");
	String teacherId =  request.getParameter("teacherId");
	String studentId =  request.getParameter("studentId");
	String score =  request.getParameter("score");

	

	if(score.equals("")||score==null)
	{
		ss.addSubject(subjectId, subjectName, teacherId, studentId, 0);
	}
	else{
		ss.addSubject(subjectId, subjectName, teacherId, studentId, Double.parseDouble(score));
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
  @RequestMapping(value="addScoreByAndroid")
  public void addScoreByAndroid(HttpServletRequest request ,HttpServletResponse response)
  {
		response.setContentType("text/html;charset=utf-8");		
        try {
			request.setCharacterEncoding("utf-8");
		} catch (UnsupportedEncodingException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
        response.setCharacterEncoding("utf-8");      
        
	  String subjects = request.getParameter("subject");
	 // System.out.println(subjects);
	 // String jsonString = jsonArray.toString();
      JSONArray jsonArray =  JSONArray.fromObject(subjects);
      for(int i=0;i<jsonArray.size();i++){
    	
          JSONObject jsonObject = jsonArray.getJSONObject(i);
       //   System.out.println(jsonObject.getString("subject_id"));
        //  System.out.println(jsonObject.getString("subject_name"));
        //  System.out.println(jsonObject.getString("teacher_id"));
        //  System.out.println(jsonObject.getString("student_id"));
       //   System.out.println(Double.valueOf(jsonObject.get("score").toString()));
          ss.addSubject(jsonObject.getString("subject_id"),jsonObject.getString("subject_name"),
        		  jsonObject.getString("teacher_id"), jsonObject.getString("student_id"),Double.valueOf(jsonObject.get("score").toString()));
         
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
  
	@RequestMapping(value="reqSubByTeaId")
	public void reqSubByTeaId(HttpServletRequest request ,HttpServletResponse response)
	{
		response.setContentType("text/html;charset=utf-8");		
        try {
			request.setCharacterEncoding("utf-8");
		} catch (UnsupportedEncodingException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
        response.setCharacterEncoding("utf-8");
		String teacherId = request.getParameter("teacherid");
		List<Subject> subjects = ss.querySubjectByTeacherId(teacherId);
		//System.out.println(subjects.size());
		Set<String> set = new HashSet<>();
		List<Subject> subList= new ArrayList<>();
		for(Subject s:subjects)
		{
			String id =s.getSubject_id();
			if(!set.contains(id))
			{
				subList.add(s);
				set.add(id);
			}
		}
		JSONObject object = new JSONObject();
		object.put("subjects",subList);
		try {
			PrintWriter writer = response.getWriter();
			writer.print(object);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@RequestMapping(value ="reqSubByStuId")
	public void reqSubByStuId(HttpServletRequest request ,HttpServletResponse response)
	{
		response.setContentType("text/html;charset=utf-8");		
        try {
			request.setCharacterEncoding("utf-8");
		} catch (UnsupportedEncodingException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
        response.setCharacterEncoding("utf-8");
		String studentId = request.getParameter("studentid");
		List<Subject> subjects = ss.querySubjectByStudentId(studentId);
		if(subjects.isEmpty())
		{
			JSONObject object = new JSONObject();
			object.put("status", false);
			object.put("msg", "No such student!");
			try {
				PrintWriter writer = response.getWriter();
				writer.print(object);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return;
		}
	//	System.out.println(subjects.size());
		Set<String> set = new HashSet<>();
		List<Subject> subList= new ArrayList<>();
		for(Subject s:subjects)
		{
			String id =s.getSubject_id();
			if(!set.contains(id))
			{
				subList.add(s);
				set.add(id);
			}
		}
		JSONObject object = new JSONObject();
		object.put("status", true);
		object.put("msg", "successful");
		object.put("subjects",subList);
		try {
			PrintWriter writer = response.getWriter();
			writer.print(object);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@RequestMapping(value="deleteSubjectById")
	public void deleteSubjectById(HttpServletRequest request ,HttpServletResponse response)
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
		List<Subject> subject = ss.querySubjectBySubjectId(id);
		if(subject.isEmpty())
		{
			JSONObject object = new JSONObject();
			object.put("status", false);
			object.put("msg", "the subject does not exist");
			try {
				PrintWriter writer = response.getWriter();
				writer.print(object);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return ;
		}
		else{
			ss.deleteSubjectById(id);
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
	@RequestMapping(value="deleteSubBySubidAndStuid")
	public void deleteSubBySubidAndStuid(HttpServletRequest request ,HttpServletResponse response)
	{
		response.setContentType("text/html;charset=utf-8");		
        try {
			request.setCharacterEncoding("utf-8");
		} catch (UnsupportedEncodingException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
        response.setCharacterEncoding("utf-8");
		
		String subjectId = request.getParameter("subjectId");
		String studentId = request.getParameter("studentId");
		List<Subject> subject1 = ss.querySubjectBySubjectId(subjectId);
		List<Subject> subject2 = ss.querySubjectByStudentId(studentId);
		if(subject1.isEmpty()||subject2.isEmpty())
		{
			JSONObject object = new JSONObject();
			object.put("status", false);
			object.put("msg", "the subject or student does not exist");
			try {
				PrintWriter writer = response.getWriter();
				writer.print(object);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return ;
		}
		else{
			ss.deleteSubBySubidAndStuidString(subjectId, studentId);
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
@RequestMapping(value="modifyStudentScore")
public void modifyStudentScore(HttpServletRequest request,HttpServletResponse response)
{
	response.setContentType("text/html;charset=utf-8");		
    try {
		request.setCharacterEncoding("utf-8");
	} catch (UnsupportedEncodingException e2) {
		// TODO Auto-generated catch block
		e2.printStackTrace();
	}
    response.setCharacterEncoding("utf-8");
    
    String subjectId = request.getParameter("subjectId");
   String studentId =  request.getParameter("studentId");
    double score = Double.parseDouble(request.getParameter("score"));
    JSONObject object = new JSONObject();
    
     List<Subject> list1 = ss.querySubjectByStudentId(studentId);
     //没有这个学号的学生
     if(list1.isEmpty())
     {
    	 object.put("status", false);
    	 object.put("msg", "No such student id !");
    	 try {
			PrintWriter writer = response.getWriter();
			writer.print(object);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	 return ;
     }
     List<Subject> list2 = ss.querySubjectBySubjectId(subjectId);
     //没有这个id的课程
     if(list2.isEmpty())
     {
    	 object.put("status", false);
    	 object.put("msg", "No such subject id !");
    	 try {
			PrintWriter writer = response.getWriter();
			writer.print(object);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	 return ;
     }
    ss.modifyStudentScore(subjectId, studentId, score);
    object.put("status", true);
	 object.put("msg", "Modify successfully!");
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
