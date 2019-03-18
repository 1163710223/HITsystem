package bean;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/*课程
课程编号 课程名称 任课教师职工号 学生学号 学生成绩
*/
/*drop table if exists `subject`;
        CREATE TABLE IF NOT EXISTS `subject`(
        `subject_id` VARCHAR(100) NOT NULL,
        `subject_name` VARCHAR(100) NOT NULL,
        `teacher_id` VARCHAR(100) NOT NULL,
        `student_id`  VARCHAR(100) NOT NULL,
        `score`  double
        )ENGINE=InnoDB DEFAULT CHARSET=utf8;*/
public class Subject {

    private String subject_id;
    private String subject_name;
    private String teacher_id;
    private String student_id;
    private double score;

    public Subject(){

    }

    public String getSubject_id() {
        return subject_id;
    }

    public void setSubject_id(String subject_id) {
        this.subject_id = subject_id;
    }

    public String getSubject_name() {
        return subject_name;
    }

    public void setSubject_name(String subject_name) {
        this.subject_name = subject_name;
    }

    public String getTeacher_id() {
        return teacher_id;
    }

    public void setTeacher_id(String teacher_id) {
        this.teacher_id = teacher_id;
    }

    public String getStudent_id() {
        return student_id;
    }

    public void setStudent_id(String student_id) {
        this.student_id = student_id;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public JSONObject getJSONObject() {
        Map<String, Object> map = new HashMap<>();
        map.put("subject_id", subject_id);
        map.put("subject_name", subject_name);
        map.put("teacher_id", teacher_id);
        map.put("student_id", student_id);
        map.put("score", score);
        Subject subject = new Subject();
        subject.setSubject_id(subject_id);
        subject.setTeacher_id(teacher_id);
        subject.setStudent_id(student_id);
        subject.setSubject_name(subject_name);
        subject.setScore(score);
        JSONObject obj = new JSONObject(map);

        //JSONObject obj = JSONObject.fromObject(score);
        return obj;
    }

    @Override
    public String toString() {
        return "subjectname="+subject_name+",subjectid="+subject_id+",teacherid"+teacher_id+",studentid"+student_id+",score"+score;
    }

}
