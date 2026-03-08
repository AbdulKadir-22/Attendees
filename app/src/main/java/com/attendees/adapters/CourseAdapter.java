package com.attendees.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.attendees.R;
import com.attendees.models.Course;

import java.util.List;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CourseViewHolder> {

    private final List<Course> courseList;
    private final OnCourseClickListener listener;

    public interface OnCourseClickListener {
        void onCourseClick(Course course);
    }

    public CourseAdapter(List<Course> courseList, OnCourseClickListener listener) {
        this.courseList = courseList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_course_card, parent, false);
        return new CourseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseViewHolder holder, int position) {
        Course course = courseList.get(position);
        holder.courseName.setText(course.getCourseName());
        com.attendees.repository.StudentRepository studentRepo = new com.attendees.repository.StudentRepository(holder.itemView.getContext());
        com.attendees.repository.AttendanceRepository attendanceRepo = new com.attendees.repository.AttendanceRepository(holder.itemView.getContext());
        
        int studentCount = studentRepo.getStudentsByCourse(course.getCourseId()).size();
        int totalLectures = attendanceRepo.getTotalLecturesForCourse(course.getCourseId());
        
        int totalPresent = 0;
        List<com.attendees.models.Student> students = studentRepo.getStudentsByCourse(course.getCourseId());
        for (com.attendees.models.Student s : students) {
            totalPresent += attendanceRepo.getPresentCountForStudent(s.getStudentId(), course.getCourseId());
        }
        
        int totalPossible = studentCount * totalLectures;
        int avgPercentage = (totalPossible > 0) ? (int)(((double)totalPresent / totalPossible) * 100) : 0;

        holder.studentCount.setText(holder.itemView.getContext().getString(R.string.total_students, studentCount));
        holder.attendancePercentage.setText(holder.itemView.getContext().getString(R.string.average_attendance, avgPercentage));

        holder.itemView.setOnClickListener(v -> listener.onCourseClick(course));
        holder.viewDetailsButton.setOnClickListener(v -> listener.onCourseClick(course));
    }

    @Override
    public int getItemCount() {
        return courseList.size();
    }

    public static class CourseViewHolder extends RecyclerView.ViewHolder {
        TextView courseName, studentCount, attendancePercentage;
        View viewDetailsButton;

        public CourseViewHolder(@NonNull View itemView) {
            super(itemView);
            courseName = itemView.findViewById(R.id.course_name);
            studentCount = itemView.findViewById(R.id.student_count);
            attendancePercentage = itemView.findViewById(R.id.attendance_percentage);
            viewDetailsButton = itemView.findViewById(R.id.view_details_button);
        }
    }
}
