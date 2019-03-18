package org.shop.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.shop.pojo.Subject;

public interface SubjectDao {
       public List<Subject> queryAllSubjects();
       public List<Subject> querySubjectBySubjectId(String id);
       public List<Subject> querySubjectByTeacherId(String id);
       public List<Subject> querySubjectByStudentId(String id);
       /**
        * 
        * @param studentId
        * @param subjectName
        * @return Subject
        */
       public List<Subject> querySubjectByStudnetIdAndSubjectName(@Param("studentId")String studentId,@Param("subjectName")String subjectName);
       public Subject querySubjectBySubjectName(String subjectName);
       public void addSubject(@Param("subjectId")String subjectId,@Param("subjectName")String subjectName,@Param("teacherId")String teacher_id,@Param("studentId")String student_id,@Param("score")double score);
       public void deleteSubjectById(@Param("id")String id);
       public void deleteSubBySubidAndStuid(@Param("subjectId")String subjectId,@Param("studentId")String studentId);
       public void modifyStudentScore(@Param("subjectId")String subjectId,@Param("studentId")String studentId,@Param("score") double score);
       
}
