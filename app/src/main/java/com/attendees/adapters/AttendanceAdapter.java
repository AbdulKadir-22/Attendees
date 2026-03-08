package com.attendees.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.attendees.R;
import com.attendees.models.Student;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AttendanceAdapter extends RecyclerView.Adapter<AttendanceAdapter.AttendanceViewHolder> {

    private final List<Student> studentList;
    // Maps student ID to attendance status (true for present, false for absent, null for unmarked)
    private final Map<Integer, Boolean> attendanceMap;

    public AttendanceAdapter(List<Student> studentList) {
        this.studentList = studentList;
        this.attendanceMap = new HashMap<>();
    }

    public void setInitialAttendance(List<com.attendees.models.Attendance> existingAttendance) {
        for (com.attendees.models.Attendance a : existingAttendance) {
            boolean isPresent = "present".equalsIgnoreCase(a.getStatus());
            attendanceMap.put(a.getStudentId(), isPresent);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AttendanceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_attendance_row, parent, false);
        return new AttendanceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AttendanceViewHolder holder, int position) {
        Student student = studentList.get(position);
        holder.name.setText(student.getStudentName());
        holder.enrollment.setText("ENR: " + student.getEnrollmentNumber());

        Boolean status = attendanceMap.get(student.getStudentId());
        if (status == null) {
            holder.statusIcon.setImageResource(R.drawable.ic_unchecked);
        } else if (status) {
            holder.statusIcon.setImageResource(R.drawable.ic_checked);
        } else {
            holder.statusIcon.setImageResource(R.drawable.ic_absent);
        }

        holder.statusIcon.setOnClickListener(v -> {
            // Cycle: Unmarked -> Present -> Absent -> Unmarked
            Boolean currentStatus = attendanceMap.get(student.getStudentId());
            if (currentStatus == null) {
                attendanceMap.put(student.getStudentId(), true);
            } else if (currentStatus) {
                attendanceMap.put(student.getStudentId(), false);
            } else {
                attendanceMap.remove(student.getStudentId());
            }
            notifyItemChanged(position);
        });
    }

    @Override
    public int getItemCount() {
        return studentList.size();
    }

    public void markAllPresent() {
        for (Student s : studentList) {
            attendanceMap.put(s.getStudentId(), true);
        }
        notifyDataSetChanged();
    }

    public Map<Integer, Boolean> getAttendanceMap() {
        return attendanceMap;
    }

    public static class AttendanceViewHolder extends RecyclerView.ViewHolder {
        TextView name, enrollment;
        ImageView statusIcon;

        public AttendanceViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.attendance_student_name);
            enrollment = itemView.findViewById(R.id.attendance_enrollment_no);
            statusIcon = itemView.findViewById(R.id.attendance_status_icon);
        }
    }
}
