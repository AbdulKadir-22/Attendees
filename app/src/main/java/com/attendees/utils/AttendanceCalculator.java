package com.attendees.utils;

import java.util.Locale;

public class AttendanceCalculator {

    public static double calculateAttendancePercentage(int totalLectures, int presentCount) {
        if (totalLectures == 0) {
            return 0.0;
        }
        return ((double) presentCount / totalLectures) * 100;
    }

    public static String formatPercentage(double percentage) {
        return String.format(Locale.getDefault(), "%.2f%%", percentage);
    }
}
