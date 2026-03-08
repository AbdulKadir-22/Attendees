package com.attendees.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.attendees.R;
import com.attendees.models.Student;
import com.google.android.material.progressindicator.LinearProgressIndicator;

import java.util.ArrayList;
import java.util.List;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.StudentViewHolder> {

    private List<Student> studentList;
    private List<Student> studentListFull;

    public StudentAdapter(List<Student> studentList) {
        this.studentList = studentList;
        this.studentListFull = new ArrayList<>(studentList);
    }

    @NonNull
    @Override
    public StudentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_student_card, parent, false);
        return new StudentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentViewHolder holder, int position) {
        Student student = studentList.get(position);
        holder.studentName.setText(student.getStudentName());
        holder.studentEnrollment.setText(holder.itemView.getContext().getString(R.string.enrollment_prefix) + student.getEnrollmentNumber());
        
        // Calculate Attendance
        com.attendees.repository.AttendanceRepository attendanceRepo = new com.attendees.repository.AttendanceRepository(holder.itemView.getContext());
        int total = attendanceRepo.getTotalLecturesForCourse(student.getCourseId());
        int present = attendanceRepo.getPresentCountForStudent(student.getStudentId(), student.getCourseId());
        int percentage = com.attendees.utils.AttendanceCalculator.calculatePercentage(present, total);

        holder.attendancePercentage.setText(percentage + "%");
        holder.attendanceProgress.setProgress(percentage);
    }

    @Override
    public int getItemCount() {
        return studentList.size();
    }

    public void updateList(List<Student> newList) {
        this.studentList = newList;
        this.studentListFull = new ArrayList<>(newList);
        notifyDataSetChanged();
    }

    public void filter(String text) {
        studentList = new ArrayList<>();
        if (text.isEmpty()) {
            studentList.addAll(studentListFull);
        } else {
            text = text.toLowerCase();
            for (Student student : studentListFull) {
                if (student.getStudentName().toLowerCase().contains(text) || 
                    student.getEnrollmentNumber().toLowerCase().contains(text)) {
                    studentList.add(student);
                }
            }
        }
        notifyDataSetChanged();
    }

    public static class StudentViewHolder extends RecyclerView.ViewHolder {
        TextView studentName, studentEnrollment, attendancePercentage;
        LinearProgressIndicator attendanceProgress;

        public StudentViewHolder(@NonNull View itemView) {
            super(itemView);
            studentName = itemView.findViewById(R.id.student_name);
            studentEnrollment = itemView.findViewById(R.id.student_enrollment);
            attendancePercentage = itemView.findViewById(R.id.student_attendance_percentage);
            attendanceProgress = itemView.findViewById(R.id.attendance_progress);
        }
    }
}
