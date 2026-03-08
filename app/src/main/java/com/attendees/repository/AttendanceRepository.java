package com.attendees.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.attendees.database.DatabaseContract;
import com.attendees.database.DatabaseHelper;
import com.attendees.models.Attendance;
import com.attendees.utils.DateUtils;

import java.util.ArrayList;
import java.util.List;

public class AttendanceRepository {
    private final DatabaseHelper dbHelper;

    public AttendanceRepository(Context context) {
        this.dbHelper = DatabaseHelper.getInstance(context);
    }

    public long markAttendance(int lectureId, int studentId, String status) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseContract.AttendanceTable.COLUMN_LECTURE_ID, lectureId);
        values.put(DatabaseContract.AttendanceTable.COLUMN_STUDENT_ID, studentId);
        values.put(DatabaseContract.AttendanceTable.COLUMN_STATUS, status);
        values.put(DatabaseContract.AttendanceTable.COLUMN_CREATED_AT, DateUtils.getCurrentDateTime());

        // Check if attendance already exists for this student and lecture (to handle updates)
        String selection = DatabaseContract.AttendanceTable.COLUMN_LECTURE_ID + "=? AND " +
                DatabaseContract.AttendanceTable.COLUMN_STUDENT_ID + "=?";
        String[] selectionArgs = {String.valueOf(lectureId), String.valueOf(studentId)};

        int rows = db.update(DatabaseContract.AttendanceTable.TABLE_NAME, values, selection, selectionArgs);
        if (rows == 0) {
            return db.insert(DatabaseContract.AttendanceTable.TABLE_NAME, null, values);
        }
        return rows;
    }

    public List<Attendance> getAttendanceByLecture(int lectureId) {
        List<Attendance> attendances = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(DatabaseContract.AttendanceTable.TABLE_NAME, null,
                DatabaseContract.AttendanceTable.COLUMN_LECTURE_ID + "=?",
                new String[]{String.valueOf(lectureId)}, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                Attendance attendance = new Attendance();
                attendance.setAttendanceId(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.AttendanceTable._ID)));
                attendance.setLectureId(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.AttendanceTable.COLUMN_LECTURE_ID)));
                attendance.setStudentId(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.AttendanceTable.COLUMN_STUDENT_ID)));
                attendance.setStatus(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.AttendanceTable.COLUMN_STATUS)));
                attendance.setCreatedAt(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.AttendanceTable.COLUMN_CREATED_AT)));
                attendances.add(attendance);
            } while (cursor.moveToNext());
            cursor.close();
        }
        return attendances;
    }

    public List<Attendance> getStudentAttendance(int studentId) {
        List<Attendance> attendances = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(DatabaseContract.AttendanceTable.TABLE_NAME, null,
                DatabaseContract.AttendanceTable.COLUMN_STUDENT_ID + "=?",
                new String[]{String.valueOf(studentId)}, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                Attendance attendance = new Attendance();
                attendance.setAttendanceId(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.AttendanceTable._ID)));
                attendance.setLectureId(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.AttendanceTable.COLUMN_LECTURE_ID)));
                attendance.setStudentId(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.AttendanceTable.COLUMN_STUDENT_ID)));
                attendance.setStatus(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.AttendanceTable.COLUMN_STATUS)));
                attendance.setCreatedAt(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.AttendanceTable.COLUMN_CREATED_AT)));
                attendances.add(attendance);
            } while (cursor.moveToNext());
            cursor.close();
        }
        return attendances;
    }

    public int getPresentCountForStudent(int studentId, int courseId) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query = "SELECT COUNT(*) FROM " + DatabaseContract.AttendanceTable.TABLE_NAME + " a " +
                "JOIN " + DatabaseContract.LecturesTable.TABLE_NAME + " l ON a." + DatabaseContract.AttendanceTable.COLUMN_LECTURE_ID + " = l." + DatabaseContract.LecturesTable._ID + " " +
                "WHERE a." + DatabaseContract.AttendanceTable.COLUMN_STUDENT_ID + "=? AND l." + DatabaseContract.LecturesTable.COLUMN_COURSE_ID + "=? AND a." + DatabaseContract.AttendanceTable.COLUMN_STATUS + "='present'";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(studentId), String.valueOf(courseId)});
        int count = 0;
        if (cursor != null && cursor.moveToFirst()) {
            count = cursor.getInt(0);
            cursor.close();
        }
        return count;
    }

    public int getTotalLecturesForCourse(int courseId) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query = "SELECT COUNT(*) FROM " + DatabaseContract.LecturesTable.TABLE_NAME + " WHERE " + DatabaseContract.LecturesTable.COLUMN_COURSE_ID + "=?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(courseId)});
        int count = 0;
        if (cursor != null && cursor.moveToFirst()) {
            count = cursor.getInt(0);
            cursor.close();
        }
        return count;
    }
}
