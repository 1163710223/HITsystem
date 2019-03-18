package org.shop.service.impl;

import java.util.List;

import org.shop.dao.TeacherDao;
import org.shop.pojo.Teacher;
import org.shop.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TeacherServiceImpl implements TeacherService{

	@Autowired
	private TeacherDao teacherDao;

	@Override
	public List<Teacher> queryAllTeachers() {
		return teacherDao.queryAllTeachers();
	}

	@Override
	public List<Teacher> queryById(String id) {
		return teacherDao.queryById(id);
	}

	@Override
	public void addTeacher(String id, String password, String name, String phonenum) {
		teacherDao.addTeacher(id, password, name, phonenum);
		
	}

	@Override
	public void deleteById(String id) {
		teacherDao.deleteById(id);		
	}

	@Override
	public void modifyTeacher(String id, String password, String name, String phonenum) {
		teacherDao.modifyTeacher(id, password, name, phonenum);		
	}



}
