package com.attendees.models;

import androidx.annotation.NonNull;

public class Course {
    private int courseId;
    private String courseName;
    private String createdAt;

    public Course() {
    }

    public Course(int courseId, String courseName, String createdAt) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.createdAt = createdAt;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
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
        return "Course{" +
                "courseId=" + courseId +
                ", courseName='" + courseName + '\'' +
                ", createdAt='" + createdAt + '\'' +
                '}';
    }
}
