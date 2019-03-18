package org.shop.service.impl;

import java.util.List;

import org.shop.dao.SubjectDao;
import org.shop.pojo.Subject;
import org.shop.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SubjectServiceImpl implements SubjectService {

	@Autowired
	private SubjectDao sd;
	@Override
	public List<Subject> queryAllSubjects() {
		return sd.queryAllSubjects();
	}

	@Override
	public List<Subject> querySubjectBySubjectId(String id) {
		return sd.querySubjectBySubjectId(id);
	}

	

	@Override
	public void addSubject(String subjectId, String subjectName, String teacher_id, String student_id, double score) {
		sd.addSubject(subjectId, subjectName, teacher_id, student_id, score);

	}

	@Override
	public List<Subject> querySubjectByTeacherId(String id) {
		return sd.querySubjectByTeacherId(id);
	}

	@Override
	public List<Subject> querySubjectByStudnetIdAndSubjectName(String studentId, String subjectName) {
		return sd.querySubjectByStudnetIdAndSubjectName(studentId, subjectName);
	}

	@Override
	public List<Subject> querySubjectByStudentId(String id) {
		return sd.querySubjectByStudentId(id);
	}

	@Override
	public void deleteSubjectById(String id) {
		sd.deleteSubjectById(id);
		
	}

	@Override
	public void deleteSubBySubidAndStuidString(String subjectId, String studentId) {
		sd.deleteSubBySubidAndStuid(subjectId, studentId);
		
	}

	@Override
	public void modifyStudentScore(String subjectId, String studentId, double score) {
		sd.modifyStudentScore(subjectId, studentId, score);
		
	}

}
