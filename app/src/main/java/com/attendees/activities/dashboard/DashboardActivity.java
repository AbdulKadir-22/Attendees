package com.attendees.activities.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.attendees.R;
import com.attendees.activities.course.CourseDetailActivity;
import com.attendees.adapters.CourseAdapter;
import com.attendees.components.dialogs.AddCourseDialog;
import com.attendees.models.Course;
import com.attendees.repository.CourseRepository;

import java.util.ArrayList;
import java.util.List;

public class DashboardActivity extends AppCompatActivity implements AddCourseDialog.OnCourseAddedListener {

    private RecyclerView recyclerView;
    private CourseAdapter adapter;
    private List<Course> courseList;
    private CourseRepository courseRepository;
    private Button addCourseButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        courseRepository = new CourseRepository(this);
        courseList = new ArrayList<>();

        recyclerView = findViewById(R.id.courses_recycler_view);
        addCourseButton = findViewById(R.id.add_course_button);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new CourseAdapter(courseList, course -> {
            Intent intent = new Intent(DashboardActivity.this, CourseDetailActivity.class);
            intent.putExtra("COURSE_ID", course.getCourseId());
            startActivity(intent);
        });
        recyclerView.setAdapter(adapter);

        addCourseButton.setOnClickListener(v -> {
            AddCourseDialog dialog = new AddCourseDialog(DashboardActivity.this, this);
            dialog.show();
        });

        loadCourses();
    }

    private void loadCourses() {
        courseList.clear();
        courseList.addAll(courseRepository.getAllCourses());
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onCourseAdded() {
        loadCourses();
        Toast.makeText(this, "Course Added Successfully", Toast.LENGTH_SHORT).show();
    }
}
