package org.shop.pojo;

public class Discussion {
     private int discussion_id;
     private String topic;
     private String name;
     private String teacher_id;
     private String student_id;
     private String teacher_words;
     private String student_words;
	public Discussion(int discussionId, String topic, String name,
			String teacherId, String studentId, String teacherWords,
			String studentWords) {
		super();
		discussion_id = discussionId;
		this.topic = topic;
		this.name = name;
		teacher_id = teacherId;
		student_id = studentId;
		teacher_words = teacherWords;
		student_words = studentWords;
	}

	public int getDiscussion_id() {
		return discussion_id;
	}

	public void setDiscussion_id(int discussion_id) {
		this.discussion_id = discussion_id;
	}

	public String getTopic() {
		return topic;
	}
	public void setTopic(String topic) {
		this.topic = topic;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	public String getTeacher_words() {
		return teacher_words;
	}
	public void setTeacher_words(String teacherWords) {
		teacher_words = teacherWords;
	}
	public String getStudent_words() {
		return student_words;
	}
	public void setStudent_words(String studentWords) {
		student_words = studentWords;
	}

	@Override
	public String toString() {
		return "Discussion [discussion_id=" + discussion_id + ", topic=" + topic + ", name=" + name + ", teacher_id="
				+ teacher_id + ", student_id=" + student_id + ", teacher_words=" + teacher_words + ", student_words="
				+ student_words + "]";
	}
     
     
}
