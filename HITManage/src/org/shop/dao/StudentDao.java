package org.shop.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.shop.pojo.Student;

public interface StudentDao {
	/**
	 * ��ѯ���е�ѧ����Ϣ
	 * 
	 * @return
	 */
	public List<Student> queryAllStudents();

	/**
	 * ����ѧ�Ų���ѧ������Ϣ
	 * 
	 * @param id
	 * @return
	 */
	public Student queryById(String id);

	/**
	 * ����ѧ��
	 * @param id
	 * @param password
	 * @param name
	 * @param year
	 * @param phonenum
	 */
	public void addStudent(@Param("id")String id, @Param("password")String password, @Param("name")String name,
			@Param("year")	int year,@Param("phonenum")String phonenum);
	/**
	 * ����ѧ��ɾ��ѧ��
	 * @param id
	 */
	public void deleteStudent(String id);
	/**
	 * �޸�ѧ������Ϣ
	 * @param id
	 * @param password
	 * @param name
	 * @param year
	 * @param phonenum
	 */
	public void modifyStudent(@Param("id")String id, @Param("password")String password, 
			@Param("name")String name,@Param("year") int year,
			@Param("phonenum")String phonenum);
	

}
