package com.example.web1mhz.parkzoo;

/**
 * Created by HP on 2017-05-25.
 */

public class Course {

    int courseID; // 강의 고유 번호
    String courseUniversity; // 학부 혹은 대학원
    int courseYear; // 해당 년도
    String courseTerm; // 해당 학기
    String courseArea; // 강의 영역
    String courseMajer; // 해당 학과
    String courseGrade; // 해당 학년
    String courseTitle; // 강의 제목
    int courseCredit; // 강의 학점
    int courseDivide; // 강의 분반
    String coursePersonnel; // 강의 제한 인원
    String courseProfessor; // 강의 교수
    String courseTime; // 강의 시간대
    String courseRoom; // 강의실

    public Course(int courseID, String courseUniversity, int courseYear, String courseTerm, String courseArea, String courseMajer, String courseGrade, String courseTitle, int courseCredit, int courseDivide, String coursePersonnel, String courseProfessor, String courseTime, String courseRoom) {
        this.courseID = courseID;
        this.courseUniversity = courseUniversity;
        this.courseYear = courseYear;
        this.courseTerm = courseTerm;
        this.courseArea = courseArea;
        this.courseMajer = courseMajer;
        this.courseGrade = courseGrade;
        this.courseTitle = courseTitle;
        this.courseCredit = courseCredit;
        this.courseDivide = courseDivide;
        this.coursePersonnel = coursePersonnel;
        this.courseProfessor = courseProfessor;
        this.courseTime = courseTime;
        this.courseRoom = courseRoom;
    }

    public int getCourseID() {
        return courseID;
    }

    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }

    public String getCourseUniversity() {
        return courseUniversity;
    }

    public void setCourseUniversity(String courseUniversity) {
        this.courseUniversity = courseUniversity;
    }

    public int getCourseYear() {
        return courseYear;
    }

    public void setCourseYear(int courseYear) {
        this.courseYear = courseYear;
    }

    public String getCourseTerm() {
        return courseTerm;
    }

    public void setCourseTerm(String courseTerm) {
        this.courseTerm = courseTerm;
    }

    public String getCourseArea() {
        return courseArea;
    }

    public void setCourseArea(String courseArea) {
        this.courseArea = courseArea;
    }

    public String getCourseMajer() {
        return courseMajer;
    }

    public void setCourseMajer(String courseMajer) {
        this.courseMajer = courseMajer;
    }

    public String getCourseGrade() {
        return courseGrade;
    }

    public void setCourseGrade(String courseGrade) {
        this.courseGrade = courseGrade;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    public int getCourseCredit() {
        return courseCredit;
    }

    public void setCourseCredit(int courseCredit) {
        this.courseCredit = courseCredit;
    }

    public int getCourseDivide() {
        return courseDivide;
    }

    public void setCourseDivide(int courseDivide) {
        this.courseDivide = courseDivide;
    }

    public String getCoursePersonnel() {
        return coursePersonnel;
    }

    public void setCoursePersonnel(String coursePersonnel) {
        this.coursePersonnel = coursePersonnel;
    }

    public String getCourseProfessor() {
        return courseProfessor;
    }

    public void setCourseProfessor(String courseProfessor) {
        this.courseProfessor = courseProfessor;
    }

    public String getCourseTime() {
        return courseTime;
    }

    public void setCourseTime(String courseTime) {
        this.courseTime = courseTime;
    }

    public String getCourseRoom() {
        return courseRoom;
    }

    public void setCourseRoom(String courseRoom) {
        this.courseRoom = courseRoom;
    }
}
