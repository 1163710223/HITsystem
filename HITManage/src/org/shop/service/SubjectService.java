package org.shop.service;

import java.util.List;

import org.shop.pojo.Subject;

public interface SubjectService {
    public List<Subject> queryAllSubjects();
    public List<Subject> querySubjectBySubjectId(String id);
    public List<Subject> querySubjectByTeacherId(String id);
    public List<Subject> querySubjectByStudentId(String id);
    public List<Subject> querySubjectByStudnetIdAndSubjectName(String studentId,String subjectName);
    public void addSubject(String subjectId,String subjectName,String teacher_id,String student_id,double score );
    public void deleteSubjectById(String id);
    public void deleteSubBySubidAndStuidString(String subjectId,String studentId);
    public void modifyStudentScore(String subjectId,String studentId, double score);
}
