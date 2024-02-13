package models.dao;

import java.util.ArrayList;

public interface ClassDAO {

	void insertClass(String className, String roomNum, String progress, String teacherName);

	ArrayList<String> fetchAllClasses();

	void deleteClass(String className);

	boolean updateClass(String newClassName, String newRoomNum, String newProgress, String newTeacherName);

	String[] getClassInfo(String className);

}