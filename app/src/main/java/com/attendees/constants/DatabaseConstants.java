package com.attendees.constants;

public class DatabaseConstants {
    // Database Info
    public static final String DATABASE_NAME = "Attendees.db";
    public static final int DATABASE_VERSION = 1;

    // Table Names
    public static final String TABLE_PROFESSOR = "professors";
    public static final String TABLE_COURSES = "courses";
    public static final String TABLE_STUDENTS = "students";
    public static final String TABLE_LECTURES = "lectures";
    public static final String TABLE_ATTENDANCE = "attendance";

    // Common Column Names
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_CREATED_AT = "created_at";

    // Professor Columns
    public static final String COLUMN_PROF_NAME = "prof_name";
    public static final String COLUMN_PROF_EMAIL = "prof_email";
    public static final String COLUMN_PROF_PASSWORD = "prof_password";

    // Course Columns
    public static final String COLUMN_COURSE_ID = "course_id";
    public static final String COLUMN_COURSE_NAME = "course_name";

    // Student Columns
    public static final String COLUMN_STUDENT_ID = "student_id";
    public static final String COLUMN_STUDENT_NAME = "student_name";
    public static final String COLUMN_ENROLLMENT_NUMBER = "enrollment_number";

    // Lecture Columns
    public static final String COLUMN_LECTURE_ID = "lecture_id";
    public static final String COLUMN_LECTURE_TITLE = "lecture_title";
    public static final String COLUMN_LECTURE_DATE = "lecture_date";
    public static final String COLUMN_LECTURE_TIME = "lecture_time";
    public static final String COLUMN_LOCATION = "location";

    // Attendance Columns
    public static final String COLUMN_ATTENDANCE_ID = "attendance_id";
    public static final String COLUMN_STATUS = "status";
}
