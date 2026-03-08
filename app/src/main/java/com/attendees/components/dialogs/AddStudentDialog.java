package com.attendees.components.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.attendees.R;
import com.attendees.repository.StudentRepository;
import com.attendees.utils.ValidationUtils;

public class AddStudentDialog extends Dialog {

    private final int courseId;
    private final StudentRepository studentRepository;
    private final OnStudentAddedListener listener;

    public interface OnStudentAddedListener {
        void onStudentAdded();
    }

    public AddStudentDialog(@NonNull Context context, int courseId, OnStudentAddedListener listener) {
        super(context);
        this.courseId = courseId;
        this.studentRepository = new StudentRepository(context);
        this.listener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_add_student);

        if (getWindow() != null) {
            getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            getWindow().setLayout(android.view.ViewGroup.LayoutParams.MATCH_PARENT, android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
        }

        EditText nameInput = findViewById(R.id.student_name_input);
        EditText enrollmentInput = findViewById(R.id.enrollment_input);
        Button createButton = findViewById(R.id.create_student_button);
        ImageView closeButton = findViewById(R.id.close_dialog);

        closeButton.setOnClickListener(v -> dismiss());

        createButton.setOnClickListener(v -> {
            String name = nameInput.getText().toString().trim();
            String enrollment = enrollmentInput.getText().toString().trim();

            if (!ValidationUtils.validateStudentName(name)) {
                nameInput.setError(getContext().getString(R.string.field_required));
                return;
            }

            if (!ValidationUtils.validateEnrollmentNumber(enrollment)) {
                enrollmentInput.setError(getContext().getString(R.string.field_required));
                return;
            }

            studentRepository.insertStudent(courseId, name, enrollment);
            listener.onStudentAdded();
            dismiss();
            Toast.makeText(getContext(), "Student added successfully", Toast.LENGTH_SHORT).show();
        });
    }
}
