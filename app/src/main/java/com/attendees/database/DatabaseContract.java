package com.attendees.database;

import android.provider.BaseColumns;
import com.attendees.constants.DatabaseConstants;

public final class DatabaseContract {

    private DatabaseContract() {}

    public static class ProfessorTable implements BaseColumns {
        public static final String TABLE_NAME = DatabaseConstants.TABLE_PROFESSOR;
        public static final String COLUMN_NAME = DatabaseConstants.COLUMN_PROF_NAME;
        public static final String COLUMN_EMAIL = DatabaseConstants.COLUMN_PROF_EMAIL;
        public static final String COLUMN_PASSWORD = DatabaseConstants.COLUMN_PROF_PASSWORD;
    }

    public static class CoursesTable implements BaseColumns {
        public static final String TABLE_NAME = DatabaseConstants.TABLE_COURSES;
        public static final String COLUMN_COURSE_NAME = DatabaseConstants.COLUMN_COURSE_NAME;
        public static final String COLUMN_CREATED_AT = DatabaseConstants.COLUMN_CREATED_AT;
    }

    public static class StudentsTable implements BaseColumns {
        public static final String TABLE_NAME = DatabaseConstants.TABLE_STUDENTS;
        public static final String COLUMN_COURSE_ID = DatabaseConstants.COLUMN_COURSE_ID;
        public static final String COLUMN_STUDENT_NAME = DatabaseConstants.COLUMN_STUDENT_NAME;
        public static final String COLUMN_ENROLLMENT_NUMBER = DatabaseConstants.COLUMN_ENROLLMENT_NUMBER;
        public static final String COLUMN_CREATED_AT = DatabaseConstants.COLUMN_CREATED_AT;
    }

    public static class LecturesTable implements BaseColumns {
        public static final String TABLE_NAME = DatabaseConstants.TABLE_LECTURES;
        public static final String COLUMN_COURSE_ID = DatabaseConstants.COLUMN_COURSE_ID;
        public static final String COLUMN_LECTURE_TITLE = DatabaseConstants.COLUMN_LECTURE_TITLE;
        public static final String COLUMN_LECTURE_DATE = DatabaseConstants.COLUMN_LECTURE_DATE;
        public static final String COLUMN_LECTURE_TIME = DatabaseConstants.COLUMN_LECTURE_TIME;
        public static final String COLUMN_LOCATION = DatabaseConstants.COLUMN_LOCATION;
        public static final String COLUMN_CREATED_AT = DatabaseConstants.COLUMN_CREATED_AT;
    }

    public static class AttendanceTable implements BaseColumns {
        public static final String TABLE_NAME = DatabaseConstants.TABLE_ATTENDANCE;
        public static final String COLUMN_LECTURE_ID = DatabaseConstants.COLUMN_LECTURE_ID;
        public static final String COLUMN_STUDENT_ID = DatabaseConstants.COLUMN_STUDENT_ID;
        public static final String COLUMN_STATUS = DatabaseConstants.COLUMN_STATUS;
        public static final String COLUMN_CREATED_AT = DatabaseConstants.COLUMN_CREATED_AT;
    }
}
