package com.attendees.activities.course;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.attendees.R;
import com.attendees.activities.lecture.AddLectureActivity;
import com.attendees.activities.student.StudentListActivity;
import com.attendees.adapters.LectureAdapter;
import com.attendees.components.dialogs.AddStudentDialog;
import com.attendees.models.Lecture;
import com.attendees.repository.LectureRepository;
import com.attendees.repository.StudentRepository;

import java.util.ArrayList;
import java.util.List;

public class CourseDetailActivity extends AppCompatActivity implements AddStudentDialog.OnStudentAddedListener {

    private int courseId;
    private String courseName;
    private StudentRepository studentRepository;
    private LectureRepository lectureRepository;
    private List<Lecture> lectureList;
    private LectureAdapter lectureAdapter;
    private TextView hubStudentCount;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_detail);

        courseId = getIntent().getIntExtra("COURSE_ID", -1);
        courseName = getIntent().getStringExtra("COURSE_NAME");
        if (courseName == null) courseName = "Course " + courseId;

        studentRepository = new StudentRepository(this);
        lectureRepository = new LectureRepository(this);
        lectureList = new ArrayList<>();

        // UI Initialization
        ImageView backButton = findViewById(R.id.back_button);
        TextView titleHeader = findViewById(R.id.course_name_title);
        Button addLectureBtn = findViewById(R.id.hub_add_lecture);
        Button addStudentBtn = findViewById(R.id.hub_add_student);
        View studentListCard = findViewById(R.id.student_list_card);
        hubStudentCount = findViewById(R.id.hub_student_count);
        recyclerView = findViewById(R.id.hub_lectures_recycler);

        titleHeader.setText(courseName);

        // Adapters
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        lectureAdapter = new LectureAdapter(lectureList, lecture -> {
            Intent intent = new Intent(this, com.attendees.activities.attendance.MarkAttendanceActivity.class);
            intent.putExtra("LECTURE_ID", lecture.getLectureId());
            intent.putExtra("COURSE_ID", courseId);
            intent.putExtra("LECTURE_TITLE", lecture.getLectureTitle());
            startActivity(intent);
        });
        recyclerView.setAdapter(lectureAdapter);

        // Listeners
        backButton.setOnClickListener(v -> finish());
        
        addLectureBtn.setOnClickListener(v -> {
            Intent intent = new Intent(this, AddLectureActivity.class);
            intent.putExtra("COURSE_ID", courseId);
            startActivity(intent);
        });

        addStudentBtn.setOnClickListener(v -> {
            AddStudentDialog dialog = new AddStudentDialog(this, courseId, this);
            dialog.show();
        });

        studentListCard.setOnClickListener(v -> {
            Intent intent = new Intent(this, StudentListActivity.class);
            intent.putExtra("COURSE_ID", courseId);
            intent.putExtra("COURSE_NAME", courseName);
            startActivity(intent);
        });

        loadHubData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadHubData();
    }

    private void loadHubData() {
        // Load student count
        int count = studentRepository.getStudentsByCourse(courseId).size();
        hubStudentCount.setText(count + " Enrolled Students");

        // Load lectures
        lectureList.clear();
        lectureList.addAll(lectureRepository.getLecturesByCourse(courseId));
        lectureAdapter.notifyDataSetChanged();
    }

    @Override
    public void onStudentAdded() {
        loadHubData();
    }
}
