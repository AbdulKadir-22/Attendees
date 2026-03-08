package com.attendees.database;

import android.content.Context;
import com.attendees.repository.CourseRepository;
import com.attendees.repository.StudentRepository;
import com.attendees.repository.LectureRepository;
import com.attendees.repository.AttendanceRepository;

public class DatabaseSeeder {

    public static void seedData(Context context) {
        CourseRepository courseRepository = new CourseRepository(context);
        StudentRepository studentRepository = new StudentRepository(context);
        LectureRepository lectureRepository = new LectureRepository(context);
        AttendanceRepository attendanceRepository = new AttendanceRepository(context);

        // Check if data already exists
        if (!courseRepository.getAllCourses().isEmpty()) {
            return;
        }

        // 1. Insert 2 Courses
        long course1Id = courseRepository.insertCourse("Mobile App Development (MAD 4A)");
        long course2Id = courseRepository.insertCourse("Database Management Systems (DBMS)");

        // 2. Add 10 Students (5 per course)
        String[] students1 = {"Rahul", "Anjali", "Suresh", "Priya", "Amit"};
        String[] students2 = {"Vikram", "Sneha", "Karan", "Pooja", "Raj"};

        for (int i = 0; i < students1.length; i++) {
            studentRepository.insertStudent((int) course1Id, students1[i], "ENR00" + (i + 1));
        }
        for (int i = 0; i < students2.length; i++) {
            studentRepository.insertStudent((int) course2Id, students2[i], "ENR01" + (i + 1));
        }

        // 3. Create 3 Lectures for Course 1
        long lecture1Id = lectureRepository.insertLecture((int) course1Id, "Intro to Android", "2026-03-01", "10:00", "Lab 1");
        long lecture2Id = lectureRepository.insertLecture((int) course1Id, "Activity Lifecycle", "2026-03-03", "11:00", "Room 101");
        long lecture3Id = lectureRepository.insertLecture((int) course1Id, "SQLite Database", "2026-03-05", "10:00", "Lab 2");

        // 4. Mark Attendance for Lecture 1 (Course 1)
        // Let's mark all present for lecture 1
        for (int i = 0; i < students1.length; i++) {
            // Student IDs start from 1 after fresh install
            attendanceRepository.markAttendance((int) lecture1Id, i + 1, "present");
        }
    }
}
