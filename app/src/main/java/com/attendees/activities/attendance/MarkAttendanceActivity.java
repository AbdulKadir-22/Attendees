package com.attendees.activities.attendance;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.attendees.R;
import com.attendees.adapters.AttendanceAdapter;
import com.attendees.models.Student;
import com.attendees.repository.AttendanceRepository;
import com.attendees.repository.StudentRepository;

import java.util.List;
import java.util.Map;

public class MarkAttendanceActivity extends AppCompatActivity {

    private int lectureId;
    private int courseId;
    private String lectureTitle;
    private AttendanceRepository attendanceRepository;
    private StudentRepository studentRepository;
    private AttendanceAdapter adapter;
    private List<Student> studentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mark_attendance);

        lectureId = getIntent().getIntExtra("LECTURE_ID", -1);
        courseId = getIntent().getIntExtra("COURSE_ID", -1);
        lectureTitle = getIntent().getStringExtra("LECTURE_TITLE");

        attendanceRepository = new AttendanceRepository(this);
        studentRepository = new StudentRepository(this);

        TextView titleView = findViewById(R.id.mark_lecture_title);
        TextView countPill = findViewById(R.id.mark_student_count_pill);
        LinearLayout backBtn = findViewById(R.id.mark_back_button);
        Button markAllBtn = findViewById(R.id.btn_mark_all_present);
        Button saveBtn = findViewById(R.id.btn_save_attendance);
        RecyclerView recyclerView = findViewById(R.id.mark_attendance_recycler);

        titleView.setText(lectureTitle);

        studentList = studentRepository.getStudentsByCourse(courseId);
        countPill.setText(studentList.size() + " Students");

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new AttendanceAdapter(studentList);
        recyclerView.setAdapter(adapter);

        loadExistingAttendance();

        backBtn.setOnClickListener(v -> finish());

        markAllBtn.setOnClickListener(v -> adapter.markAllPresent());

        saveBtn.setOnClickListener(v -> saveAttendance());
    }

    private void saveAttendance() {
        Map<Integer, Boolean> attendanceMap = adapter.getAttendanceMap();
        
        // Loop through all students to ensure we record a status (present/absent)
        for (Student student : studentList) {
            Boolean isPresent = attendanceMap.get(student.getStudentId());
            String status = (isPresent != null && isPresent) ? "present" : "absent";
            attendanceRepository.markAttendance(lectureId, student.getStudentId(), status);
        }

        Toast.makeText(this, R.string.attendance_saved, Toast.LENGTH_SHORT).show();
        finish();
    }

    private void loadExistingAttendance() {
        List<com.attendees.models.Attendance> existing = attendanceRepository.getAttendanceByLecture(lectureId);
        if (!existing.isEmpty()) {
            adapter.setInitialAttendance(existing);
        }
    }
}
