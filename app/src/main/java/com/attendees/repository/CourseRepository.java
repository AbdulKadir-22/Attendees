package com.attendees.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.attendees.database.DatabaseContract;
import com.attendees.database.DatabaseHelper;
import com.attendees.models.Course;
import com.attendees.utils.DateUtils;

import java.util.ArrayList;
import java.util.List;

public class CourseRepository {
    private final DatabaseHelper dbHelper;

    public CourseRepository(Context context) {
        this.dbHelper = DatabaseHelper.getInstance(context);
    }

    public long insertCourse(String courseName) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseContract.CoursesTable.COLUMN_COURSE_NAME, courseName);
        values.put(DatabaseContract.CoursesTable.COLUMN_CREATED_AT, DateUtils.getCurrentDateTime());
        return db.insert(DatabaseContract.CoursesTable.TABLE_NAME, null, values);
    }

    public List<Course> getAllCourses() {
        List<Course> courses = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(DatabaseContract.CoursesTable.TABLE_NAME, null, null, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                Course course = new Course();
                course.setCourseId(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.CoursesTable._ID)));
                course.setCourseName(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.CoursesTable.COLUMN_COURSE_NAME)));
                course.setCreatedAt(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.CoursesTable.COLUMN_CREATED_AT)));
                courses.add(course);
            } while (cursor.moveToNext());
            cursor.close();
        }
        return courses;
    }

    public Course getCourseById(int id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(DatabaseContract.CoursesTable.TABLE_NAME, null,
                DatabaseContract.CoursesTable._ID + "=?", new String[]{String.valueOf(id)},
                null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            Course course = new Course();
            course.setCourseId(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.CoursesTable._ID)));
            course.setCourseName(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.CoursesTable.COLUMN_COURSE_NAME)));
            course.setCreatedAt(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.CoursesTable.COLUMN_CREATED_AT)));
            cursor.close();
            return course;
        }
        return null;
    }

    public int deleteCourse(int id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        return db.delete(DatabaseContract.CoursesTable.TABLE_NAME,
                DatabaseContract.CoursesTable._ID + "=?", new String[]{String.valueOf(id)});
    }
}
