package com.attendees.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.attendees.database.DatabaseContract;
import com.attendees.database.DatabaseHelper;
import com.attendees.models.Lecture;
import com.attendees.utils.DateUtils;

import java.util.ArrayList;
import java.util.List;

public class LectureRepository {
    private final DatabaseHelper dbHelper;

    public LectureRepository(Context context) {
        this.dbHelper = DatabaseHelper.getInstance(context);
    }

    public long insertLecture(int courseId, String title, String date, String time, String location) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseContract.LecturesTable.COLUMN_COURSE_ID, courseId);
        values.put(DatabaseContract.LecturesTable.COLUMN_LECTURE_TITLE, title);
        values.put(DatabaseContract.LecturesTable.COLUMN_LECTURE_DATE, date);
        values.put(DatabaseContract.LecturesTable.COLUMN_LECTURE_TIME, time);
        values.put(DatabaseContract.LecturesTable.COLUMN_LOCATION, location);
        values.put(DatabaseContract.LecturesTable.COLUMN_CREATED_AT, DateUtils.getCurrentDateTime());
        return db.insert(DatabaseContract.LecturesTable.TABLE_NAME, null, values);
    }

    public List<Lecture> getLecturesByCourse(int courseId) {
        List<Lecture> lectures = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(DatabaseContract.LecturesTable.TABLE_NAME, null,
                DatabaseContract.LecturesTable.COLUMN_COURSE_ID + "=?",
                new String[]{String.valueOf(courseId)}, null, null,
                DatabaseContract.LecturesTable.COLUMN_LECTURE_DATE + " DESC");

        if (cursor != null && cursor.moveToFirst()) {
            do {
                Lecture lecture = new Lecture();
                lecture.setLectureId(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.LecturesTable._ID)));
                lecture.setCourseId(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.LecturesTable.COLUMN_COURSE_ID)));
                lecture.setLectureTitle(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.LecturesTable.COLUMN_LECTURE_TITLE)));
                lecture.setLectureDate(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.LecturesTable.COLUMN_LECTURE_DATE)));
                lecture.setLectureTime(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.LecturesTable.COLUMN_LECTURE_TIME)));
                lecture.setLocation(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.LecturesTable.COLUMN_LOCATION)));
                lecture.setCreatedAt(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.LecturesTable.COLUMN_CREATED_AT)));
                lectures.add(lecture);
            } while (cursor.moveToNext());
            cursor.close();
        }
        return lectures;
    }

    public int deleteLecture(int id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        return db.delete(DatabaseContract.LecturesTable.TABLE_NAME,
                DatabaseContract.LecturesTable._ID + "=?", new String[]{String.valueOf(id)});
    }
}
