package com.example.enter_school_management.Common.lang;

import org.springframework.stereotype.Component;

@Component
public class Const {
    public static final int HDNotUpdated = 0;
    public static final int HDUpdated = 1;

    public static final int HDUnsafe = 0;
    public static final int HDSafe = 1;

    public static final int roleStudent = 0;
    public static final int roleInstructor = 1;
    public static final int roleDeptAdmin = 2;
    public static final int roleSuperAdmin = 3;

    public static final double sickTemperature = 37.5;

    public static final int outSchool = 0;
    public static final int inSchool = 1;

    public static final int appSubmit = 1;
    public static final int appReject = 0;
    public static final int appITApprove = 2;
    public static final int appDAApprove = 3;

    public static final int PermDenied = 0;
    public static final int PermApproved = 1;

    public static final int CampusClosed = 0;
    public static final int CampusOpen = 1;

    public static final int firstOutschool = 0;
    public static final int firstInschool = 1;
    public static final int lastOutschool = 2;
    public static final int lastInschool = 3;
    public static final Long allDayOutsideSchool = 86400000L;
    public static final Long allDayInsideSchool = 0L;

    public static final int oneYear = 365;
}
