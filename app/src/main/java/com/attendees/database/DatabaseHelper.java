package com.attendees.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.attendees.constants.DatabaseConstants;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static DatabaseHelper instance;

    public static synchronized DatabaseHelper getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseHelper(context.getApplicationContext());
        }
        return instance;
    }

    private DatabaseHelper(Context context) {
        super(context, DatabaseConstants.DATABASE_NAME, null, DatabaseConstants.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create Professors Table
        String CREATE_PROFESSORS_TABLE = "CREATE TABLE " + DatabaseContract.ProfessorTable.TABLE_NAME + "("
                + DatabaseContract.ProfessorTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + DatabaseContract.ProfessorTable.COLUMN_NAME + " TEXT,"
                + DatabaseContract.ProfessorTable.COLUMN_EMAIL + " TEXT,"
                + DatabaseContract.ProfessorTable.COLUMN_PASSWORD + " TEXT"
                + ")";
        db.execSQL(CREATE_PROFESSORS_TABLE);

        // Create Courses Table
        String CREATE_COURSES_TABLE = "CREATE TABLE " + DatabaseContract.CoursesTable.TABLE_NAME + "("
                + DatabaseContract.CoursesTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + DatabaseContract.CoursesTable.COLUMN_COURSE_NAME + " TEXT,"
                + DatabaseContract.CoursesTable.COLUMN_CREATED_AT + " TEXT"
                + ")";
        db.execSQL(CREATE_COURSES_TABLE);

        // Create Students Table
        String CREATE_STUDENTS_TABLE = "CREATE TABLE " + DatabaseContract.StudentsTable.TABLE_NAME + "("
                + DatabaseContract.StudentsTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + DatabaseContract.StudentsTable.COLUMN_COURSE_ID + " INTEGER,"
                + DatabaseContract.StudentsTable.COLUMN_STUDENT_NAME + " TEXT,"
                + DatabaseContract.StudentsTable.COLUMN_ENROLLMENT_NUMBER + " TEXT,"
                + DatabaseContract.StudentsTable.COLUMN_CREATED_AT + " TEXT,"
                + "FOREIGN KEY(" + DatabaseContract.StudentsTable.COLUMN_COURSE_ID + ") REFERENCES "
                + DatabaseContract.CoursesTable.TABLE_NAME + "(" + DatabaseContract.CoursesTable._ID + ")"
                + ")";
        db.execSQL(CREATE_STUDENTS_TABLE);

        // Create Lectures Table
        String CREATE_LECTURES_TABLE = "CREATE TABLE " + DatabaseContract.LecturesTable.TABLE_NAME + "("
                + DatabaseContract.LecturesTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + DatabaseContract.LecturesTable.COLUMN_COURSE_ID + " INTEGER,"
                + DatabaseContract.LecturesTable.COLUMN_LECTURE_TITLE + " TEXT,"
                + DatabaseContract.LecturesTable.COLUMN_LECTURE_DATE + " TEXT,"
                + DatabaseContract.LecturesTable.COLUMN_LECTURE_TIME + " TEXT,"
                + DatabaseContract.LecturesTable.COLUMN_LOCATION + " TEXT,"
                + DatabaseContract.LecturesTable.COLUMN_CREATED_AT + " TEXT,"
                + "FOREIGN KEY(" + DatabaseContract.LecturesTable.COLUMN_COURSE_ID + ") REFERENCES "
                + DatabaseContract.CoursesTable.TABLE_NAME + "(" + DatabaseContract.CoursesTable._ID + ")"
                + ")";
        db.execSQL(CREATE_LECTURES_TABLE);

        // Create Attendance Table
        String CREATE_ATTENDANCE_TABLE = "CREATE TABLE " + DatabaseContract.AttendanceTable.TABLE_NAME + "("
                + DatabaseContract.AttendanceTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + DatabaseContract.AttendanceTable.COLUMN_LECTURE_ID + " INTEGER,"
                + DatabaseContract.AttendanceTable.COLUMN_STUDENT_ID + " INTEGER,"
                + DatabaseContract.AttendanceTable.COLUMN_STATUS + " TEXT,"
                + DatabaseContract.AttendanceTable.COLUMN_CREATED_AT + " TEXT,"
                + "FOREIGN KEY(" + DatabaseContract.AttendanceTable.COLUMN_LECTURE_ID + ") REFERENCES "
                + DatabaseContract.LecturesTable.TABLE_NAME + "(" + DatabaseContract.LecturesTable._ID + "),"
                + "FOREIGN KEY(" + DatabaseContract.AttendanceTable.COLUMN_STUDENT_ID + ") REFERENCES "
                + DatabaseContract.StudentsTable.TABLE_NAME + "(" + DatabaseContract.StudentsTable._ID + ")"
                + ")";
        db.execSQL(CREATE_ATTENDANCE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseContract.AttendanceTable.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseContract.LecturesTable.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseContract.StudentsTable.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseContract.CoursesTable.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseContract.ProfessorTable.TABLE_NAME);
        onCreate(db);
    }
}
