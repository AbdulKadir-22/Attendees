package com.attendees.utils;

import com.attendees.models.Attendance;
import java.util.List;

public class AttendanceCalculator {

    /**
     * Calculates attendance percentage.
     * @param presentCount Number of lectures attended.
     * @param totalLectures Total number of lectures held.
     * @return Percentage (0-100).
     */
    public static int calculatePercentage(int presentCount, int totalLectures) {
        if (totalLectures <= 0) return 0;
        return (int) (((double) presentCount / totalLectures) * 100);
    }

    /**
     * Formats the percentage as a string with % symbol.
     */
    public static String formatPercentage(int percentage) {
        return percentage + "%";
    }
}
