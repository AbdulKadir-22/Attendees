package com.attendees.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.attendees.database.DatabaseContract;
import com.attendees.database.DatabaseHelper;
import com.attendees.models.Student;
import com.attendees.utils.DateUtils;

import java.util.ArrayList;
import java.util.List;

public class StudentRepository {
    private final DatabaseHelper dbHelper;

    public StudentRepository(Context context) {
        this.dbHelper = DatabaseHelper.getInstance(context);
    }

    public long insertStudent(int courseId, String name, String enrollmentNumber) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseContract.StudentsTable.COLUMN_COURSE_ID, courseId);
        values.put(DatabaseContract.StudentsTable.COLUMN_STUDENT_NAME, name);
        values.put(DatabaseContract.StudentsTable.COLUMN_ENROLLMENT_NUMBER, enrollmentNumber);
        values.put(DatabaseContract.StudentsTable.COLUMN_CREATED_AT, DateUtils.getCurrentDateTime());
        return db.insert(DatabaseContract.StudentsTable.TABLE_NAME, null, values);
    }

    public List<Student> getStudentsByCourse(int courseId) {
        List<Student> students = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(DatabaseContract.StudentsTable.TABLE_NAME, null,
                DatabaseContract.StudentsTable.COLUMN_COURSE_ID + "=?",
                new String[]{String.valueOf(courseId)}, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                Student student = new Student();
                student.setStudentId(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.StudentsTable._ID)));
                student.setCourseId(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.StudentsTable.COLUMN_COURSE_ID)));
                student.setStudentName(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.StudentsTable.COLUMN_STUDENT_NAME)));
                student.setEnrollmentNumber(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.StudentsTable.COLUMN_ENROLLMENT_NUMBER)));
                student.setCreatedAt(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.StudentsTable.COLUMN_CREATED_AT)));
                students.add(student);
            } while (cursor.moveToNext());
            cursor.close();
        }
        return students;
    }

    public int deleteStudent(int id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        return db.delete(DatabaseContract.StudentsTable.TABLE_NAME,
                DatabaseContract.StudentsTable._ID + "=?", new String[]{String.valueOf(id)});
    }
}
