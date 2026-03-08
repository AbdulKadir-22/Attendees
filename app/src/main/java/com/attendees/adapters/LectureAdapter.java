package com.attendees.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.attendees.R;
import com.attendees.models.Lecture;
import com.google.android.material.progressindicator.LinearProgressIndicator;

import java.util.List;

public class LectureAdapter extends RecyclerView.Adapter<LectureAdapter.LectureViewHolder> {

    private final List<Lecture> lectureList;
    private final OnLectureClickListener listener;

    public interface OnLectureClickListener {
        void onLectureClick(Lecture lecture);
    }

    public LectureAdapter(List<Lecture> lectureList, OnLectureClickListener listener) {
        this.lectureList = lectureList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public LectureViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lecture_card, parent, false);
        return new LectureViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LectureViewHolder holder, int position) {
        Lecture lecture = lectureList.get(position);
        holder.title.setText(lecture.getLectureTitle());
        holder.venue.setText("Venue: " + lecture.getLocation());
        holder.time.setText("Time: " + lecture.getLectureTime());
        
        // Show total present count
        com.attendees.repository.AttendanceRepository attendanceRepo = new com.attendees.repository.AttendanceRepository(holder.itemView.getContext());
        List<com.attendees.models.Attendance> attendances = attendanceRepo.getAttendanceByLecture(lecture.getLectureId());
        int presentCount = 0;
        for (com.attendees.models.Attendance a : attendances) {
            if ("present".equalsIgnoreCase(a.getStatus())) presentCount++;
        }
        
        holder.attendancePercentage.setText(presentCount + " Present");
        // For lecture cards, we can show present/total if total students is known, 
        // but design just shows "Total Present" label usually or count.
        // Let's use a simple progress if we can get total students.
        // For simplicity, we'll just show the count as per design "Total Present: X"

        holder.itemView.setOnClickListener(v -> listener.onLectureClick(lecture));
    }

    @Override
    public int getItemCount() {
        return lectureList.size();
    }

    public static class LectureViewHolder extends RecyclerView.ViewHolder {
        TextView title, venue, time, attendancePercentage;
        LinearProgressIndicator attendanceProgress;

        public LectureViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.lecture_card_title);
            venue = itemView.findViewById(R.id.lecture_card_venue);
            time = itemView.findViewById(R.id.lecture_card_time);
            attendancePercentage = itemView.findViewById(R.id.lecture_attendance_percentage);
            attendanceProgress = itemView.findViewById(R.id.lecture_attendance_progress);
        }
    }
}
