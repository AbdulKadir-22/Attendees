package com.attendees.components.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.attendees.R;
import com.attendees.repository.CourseRepository;
import com.attendees.utils.ValidationUtils;

public class AddCourseDialog extends Dialog {

    private final CourseRepository courseRepository;
    private final OnCourseAddedListener listener;

    public interface OnCourseAddedListener {
        void onCourseAdded();
    }

    public AddCourseDialog(@NonNull Context context, OnCourseAddedListener listener) {
        super(context);
        this.courseRepository = new CourseRepository(context);
        this.listener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_add_course);

        if (getWindow() != null) {
            getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            getWindow().setLayout(android.view.ViewGroup.LayoutParams.MATCH_PARENT, android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
        }

        EditText courseTitleInput = findViewById(R.id.course_title_input);
        Button createButton = findViewById(R.id.create_course_button);

        createButton.setOnClickListener(v -> {
            String title = courseTitleInput.getText().toString().trim();

            if (ValidationUtils.validateCourseName(title)) {
                courseRepository.insertCourse(title);
                listener.onCourseAdded();
                dismiss();
            } else {
                courseTitleInput.setError(getContext().getString(R.string.field_required));
                Toast.makeText(getContext(), "Invalid course name", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
