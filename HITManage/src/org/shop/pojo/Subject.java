package org.shop.pojo;

public class Subject {
    private String subject_id;
    private String subject_name;
    private String teacher_id;
    private String student_id;
    private double  score;
    

	public Subject(String subjectId, String subjectName, String teacherId,
			String studentId, double score) {
		super();
		subject_id = subjectId;
		subject_name = subjectName;
		teacher_id = teacherId;
		student_id = studentId;
		this.score = score;
	}
	public String getSubject_id() {
		return subject_id;
	}
	public void setSubject_id(String subjectId) {
		subject_id = subjectId;
	}
	public String getSubject_name() {
		return subject_name;
	}
	public void setSubject_name(String subjectName) {
		subject_name = subjectName;
	}
	public String getTeacher_id() {
		return teacher_id;
	}
	public void setTeacher_id(String teacherId) {
		teacher_id = teacherId;
	}
	public String getStudent_id() {
		return student_id;
	}
	public void setStudent_id(String studentId) {
		student_id = studentId;
	}
	public double getScore() {
		return score;
	}
	public void setScore(double score) {
		this.score = score;
	}


}
