package com.attendees.models;

import androidx.annotation.NonNull;

public class Lecture {
    private int lectureId;
    private int courseId;
    private String lectureTitle;
    private String lectureDate;
    private String lectureTime;
    private String location;
    private String createdAt;

    public Lecture() {
    }

    public Lecture(int lectureId, int courseId, String lectureTitle, String lectureDate, String lectureTime, String location, String createdAt) {
        this.lectureId = lectureId;
        this.courseId = courseId;
        this.lectureTitle = lectureTitle;
        this.lectureDate = lectureDate;
        this.lectureTime = lectureTime;
        this.location = location;
        this.createdAt = createdAt;
    }

    public int getLectureId() {
        return lectureId;
    }

    public void setLectureId(int lectureId) {
        this.lectureId = lectureId;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getLectureTitle() {
        return lectureTitle;
    }

    public void setLectureTitle(String lectureTitle) {
        this.lectureTitle = lectureTitle;
    }

    public String getLectureDate() {
        return lectureDate;
    }

    public void setLectureDate(String lectureDate) {
        this.lectureDate = lectureDate;
    }

    public String getLectureTime() {
        return lectureTime;
    }

    public void setLectureTime(String lectureTime) {
        this.lectureTime = lectureTime;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
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
        return "Lecture{" +
                "lectureId=" + lectureId +
                ", courseId=" + courseId +
                ", lectureTitle='" + lectureTitle + '\'' +
                ", lectureDate='" + lectureDate + '\'' +
                ", lectureTime='" + lectureTime + '\'' +
                ", location='" + location + '\'' +
                ", createdAt='" + createdAt + '\'' +
                '}';
    }
}
