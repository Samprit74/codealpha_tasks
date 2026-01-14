package model;

public class Student {
  private int id;
  private String name;
  private String department;
  private int year;
  private int semester;
  
  
  private int subject1;
  private int subject2;
  private int subject3;
  
  private double score;
  
  public Student() {
      // default constructor
  }
  
  public Student(String name, String department, int year, int semester,
          int subject1, int subject2, int subject3, double score) {

this.name = name;
this.department = department;
this.year = year;
this.semester = semester;
this.subject1 = subject1;
this.subject2 = subject2;
this.subject3 = subject3;
this.score = score;
}

public int getId() {
	return id;
}

public void setId(int id) {
	this.id = id;
}

public String getName() {
	return name;
}

public void setName(String name) {
	this.name = name;
}

public String getDepartment() {
	return department;
}

public void setDepartment(String department) {
	this.department = department;
}

public int getYear() {
	return year;
}

public void setYear(int year) {
	this.year = year;
}

public int getSemester() {
	return semester;
}

public void setSemester(int semester) {
	this.semester = semester;
}

public int getSubject1() {
	return subject1;
}

public void setSubject1(int subject1) {
	this.subject1 = subject1;
}

public int getSubject2() {
	return subject2;
}

public void setSubject2(int subject2) {
	this.subject2 = subject2;
}

public int getSubject3() {
	return subject3;
}

public void setSubject3(int subject3) {
	this.subject3 = subject3;
}

public double getScore() {
	return score;
}

public void setScore(double score) {
	this.score = score;
}
  
  
}
