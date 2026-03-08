package com.attendees.activities.lecture;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.attendees.R;
import com.attendees.repository.LectureRepository;

import java.util.Calendar;
import java.util.Locale;

public class AddLectureActivity extends AppCompatActivity {

    private int courseId;
    private LectureRepository lectureRepository;
    private EditText titleInput, locationInput;
    private TextView dateText, timeText;
    private String selectedDate = "", selectedTime = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_lecture);

        courseId = getIntent().getIntExtra("COURSE_ID", -1);
        lectureRepository = new LectureRepository(this);

        ImageView backBtn = findViewById(R.id.lecture_back_button);
        titleInput = findViewById(R.id.lecture_name_input);
        locationInput = findViewById(R.id.lecture_location_input);
        dateText = findViewById(R.id.selected_date_text);
        timeText = findViewById(R.id.selected_time_text);
        View datePicker = findViewById(R.id.date_picker_trigger);
        View timePicker = findViewById(R.id.time_picker_trigger);
        Button createBtn = findViewById(R.id.create_lecture_button);

        backBtn.setOnClickListener(v -> finish());

        datePicker.setOnClickListener(v -> showDatePicker());
        timePicker.setOnClickListener(v -> showTimePicker());

        createBtn.setOnClickListener(v -> {
            String title = titleInput.getText().toString().trim();
            String location = locationInput.getText().toString().trim();

            if (title.isEmpty() || location.isEmpty() || selectedDate.isEmpty() || selectedTime.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            lectureRepository.insertLecture(courseId, title, selectedDate, selectedTime, location);
            Toast.makeText(this, "Lecture created", Toast.LENGTH_SHORT).show();
            finish();
        });
    }

    private void showDatePicker() {
        Calendar c = Calendar.getInstance();
        new DatePickerDialog(this, (view, year, month, dayOfMonth) -> {
            selectedDate = String.format(Locale.getDefault(), "%d-%02d-%02d", year, month + 1, dayOfMonth);
            dateText.setText(selectedDate);
            dateText.setTextColor(getResources().getColor(R.color.text_primary));
        }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void showTimePicker() {
        Calendar c = Calendar.getInstance();
        new TimePickerDialog(this, (view, hourOfDay, minute) -> {
            selectedTime = String.format(Locale.getDefault(), "%02d:%02d", hourOfDay, minute);
            timeText.setText(selectedTime);
            timeText.setTextColor(getResources().getColor(R.color.text_primary));
        }, c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE), true).show();
    }
}
