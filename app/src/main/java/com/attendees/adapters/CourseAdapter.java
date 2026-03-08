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
        // Temporarily hardcoded for Part 2 as requested
        holder.studentCount.setText(holder.itemView.getContext().getString(R.string.total_students, 0));
        holder.attendancePercentage.setText(holder.itemView.getContext().getString(R.string.average_attendance, 0));

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
