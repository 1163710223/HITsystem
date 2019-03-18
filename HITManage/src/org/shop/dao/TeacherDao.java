package org.shop.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.shop.pojo.Teacher;

public interface TeacherDao {
        public List<Teacher> queryAllTeachers();
        public List<Teacher> queryById(@Param("id")String id);
        public void addTeacher(@Param("id")String id,@Param("password")String password,
        		@Param("name")String name,@Param("phonenum")String phonenum);
        public void deleteById(String id);
        public void modifyTeacher(@Param("id")String id,@Param("password")String password,
        		@Param("name")String name,@Param("phonenum")String phonenum);
}
