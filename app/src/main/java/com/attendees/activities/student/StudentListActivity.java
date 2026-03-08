package com.attendees.activities.student;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.attendees.R;
import com.attendees.adapters.StudentAdapter;
import com.attendees.components.dialogs.AddStudentDialog;
import com.attendees.models.Student;
import com.attendees.repository.StudentRepository;

import java.util.ArrayList;
import java.util.List;

public class StudentListActivity extends AppCompatActivity implements AddStudentDialog.OnStudentAddedListener {

    private int courseId;
    private String courseName;
    private StudentRepository studentRepository;
    private List<Student> studentList;
    private StudentAdapter adapter;
    private TextView courseTitle, studentCount;
    private EditText searchInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_list);

        courseId = getIntent().getIntExtra("COURSE_ID", -1);
        courseName = getIntent().getStringExtra("COURSE_NAME");

        studentRepository = new StudentRepository(this);
        studentList = new ArrayList<>();

        courseTitle = findViewById(R.id.list_course_title);
        studentCount = findViewById(R.id.list_student_count_pill);
        searchInput = findViewById(R.id.list_search_input);
        Button addBtn = findViewById(R.id.list_add_student_button);
        RecyclerView rv = findViewById(R.id.list_students_recycler);

        courseTitle.setText("List - " + courseName);

        rv.setLayoutManager(new LinearLayoutManager(this));
        adapter = new StudentAdapter(studentList);
        rv.setAdapter(adapter);

        addBtn.setOnClickListener(v -> {
            AddStudentDialog dialog = new AddStudentDialog(this, courseId, this);
            dialog.show();
        });

        searchInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.filter(s.toString());
            }
            @Override
            public void afterTextChanged(Editable s) {}
        });

        loadStudents();
    }

    private void loadStudents() {
        studentList = studentRepository.getStudentsByCourse(courseId);
        adapter.updateList(studentList);
        studentCount.setText(studentList.size() + " Students");
    }

    @Override
    public void onStudentAdded() {
        loadStudents();
    }
}
