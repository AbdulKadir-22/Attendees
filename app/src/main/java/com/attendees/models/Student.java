package com.attendees.models;

import androidx.annotation.NonNull;

public class Student {
    private int studentId;
    private int courseId;
    private String studentName;
    private String enrollmentNumber;
    private String createdAt;

    public Student() {
    }

    public Student(int studentId, int courseId, String studentName, String enrollmentNumber, String createdAt) {
        this.studentId = studentId;
        this.courseId = courseId;
        this.studentName = studentName;
        this.enrollmentNumber = enrollmentNumber;
        this.createdAt = createdAt;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getEnrollmentNumber() {
        return enrollmentNumber;
    }

    public void setEnrollmentNumber(String enrollmentNumber) {
        this.enrollmentNumber = enrollmentNumber;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    @NonNull
    @Override
    public String toString() {
        return "Student{" +
                "studentId=" + studentId +
                ", courseId=" + courseId +
                ", studentName='" + studentName + '\'' +
                ", enrollmentNumber='" + enrollmentNumber + '\'' +
                ", createdAt='" + createdAt + '\'' +
                '}';
    }
}
