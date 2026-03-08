package com.attendees.utils;

public class ValidationUtils {

    public static boolean validateStudentName(String name) {
        return name != null && !name.trim().isEmpty() && name.length() >= 2;
    }

    public static boolean validateEnrollmentNumber(String enrollmentNumber) {
        return enrollmentNumber != null && !enrollmentNumber.trim().isEmpty();
    }

    public static boolean validateCourseName(String courseName) {
        return courseName != null && !courseName.trim().isEmpty() && courseName.length() >= 3;
    }
}
