package clarion.attendance.core;

import java.util.Date;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;

public class AttendanceLessons {

	private int id;

	private int sNo;
	private SimpleStringProperty date;
	private SimpleStringProperty teacherName;
	private SimpleStringProperty teacherClass;

	private SimpleStringProperty teacherSubject;
	private SimpleStringProperty lessonTime;
	private SimpleStringProperty term;

	public AttendanceLessons(int id, int sNo, String date, String teacherName, String teacherClass, String teacherSubject,
			String lessonTime, String term) {
		super();
		this.id = id;
		this.sNo = sNo;
		this.date = new SimpleStringProperty(date);
		this.teacherName = new SimpleStringProperty(teacherName);
		this.teacherClass = new SimpleStringProperty(teacherClass);
		this.teacherSubject = new SimpleStringProperty(teacherSubject);
		this.lessonTime = new SimpleStringProperty(lessonTime);
		this.term = new SimpleStringProperty(term);
	}

	public AttendanceLessons(int id, String date, String teacherName, String teacherClass, String teacherSubject,
			String lessonTime, String term) {
		super();
		this.id = id;
		this.date = new SimpleStringProperty(date);
		this.teacherName = new SimpleStringProperty(teacherName);
		this.teacherClass = new SimpleStringProperty(teacherClass);
		this.teacherSubject = new SimpleStringProperty(teacherSubject);
		this.lessonTime = new SimpleStringProperty(lessonTime);
		this.term = new SimpleStringProperty(term);
	}

	public AttendanceLessons(String date, String teacherName, String teacherClass, String teacherSubject,
			String lessonTime, String term) {
		super();
		this.date = new SimpleStringProperty(date);
		this.teacherName = new SimpleStringProperty(teacherName);
		this.teacherClass = new SimpleStringProperty(teacherClass);
		this.teacherSubject = new SimpleStringProperty(teacherSubject);
		this.lessonTime = new SimpleStringProperty(lessonTime);
		this.term = new SimpleStringProperty(term);
	}

	// public int getSNo(){
	//
	// ObservableList<AttendanceLessons> lessons = null;
	//
	// for (AttendanceLessons sn : lessons) {
	//
	// }
	// }

	public int getId() {
		return id;
	}

	public int getsNo() {
		return sNo;
	}

	public void setsNo(int sNo) {
		this.sNo = sNo;
	}

	public String getDate() {
		return date.get();
	}

	public String getTeacherName() {
		return teacherName.get();
	}

	public String getTeacherClass() {
		return teacherClass.get();
	}

	public String getTeacherSubject() {
		return teacherSubject.get();
	}

	public String getLessonTime() {
		return lessonTime.get();
	}

	public String getTerm() {
		return term.get();
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setDate(String date) {
		this.date.set(date);
	}

	public void setTeacherName(String teacherName) {
		this.teacherName.set(teacherName);
	}

	public void setTeacherClass(String teacherClass) {
		this.teacherClass.set(teacherClass);
	}

	public void setTeacherSubject(String teacherSubject) {
		this.teacherSubject.set(teacherSubject);
	}

	public void setLessonTime(String lessonTime) {
		this.lessonTime.set(lessonTime);
	}

	public void setTerm(String term) {
		this.term.set(term);
	}

}
