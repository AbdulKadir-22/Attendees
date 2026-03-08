package com.attendees.models;

import androidx.annotation.NonNull;

public class Attendance {
    private int attendanceId;
    private int lectureId;
    private int studentId;
    private String status; // "present" or "absent"
    private String createdAt;

    public Attendance() {
    }

    public Attendance(int attendanceId, int lectureId, int studentId, String status, String createdAt) {
        this.attendanceId = attendanceId;
        this.lectureId = lectureId;
        this.studentId = studentId;
        this.status = status;
        this.createdAt = createdAt;
    }

    public int getAttendanceId() {
        return attendanceId;
    }

    public void setAttendanceId(int attendanceId) {
        this.attendanceId = attendanceId;
    }

    public int getLectureId() {
        return lectureId;
    }

    public void setLectureId(int lectureId) {
        this.lectureId = lectureId;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
        return "Attendance{" +
                "attendanceId=" + attendanceId +
                ", lectureId=" + lectureId +
                ", studentId=" + studentId +
                ", status='" + status + '\'' +
                ", createdAt='" + createdAt + '\'' +
                '}';
    }
}
