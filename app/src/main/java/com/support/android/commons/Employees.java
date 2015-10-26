
package com.support.android.commons;

import android.app.Application;

import com.support.android.user.R;

import java.util.Random;

public class Employees extends Application {

    String empId ;
    String userRoleForView;
    String fullName;
    String userRole;
    String empIdForPersonalDetail;

    private static final Random RANDOM = new Random();

    public static int getRandomCheeseDrawable() {
        switch (RANDOM.nextInt(5)) {
            default:
            case 0:
                return R.drawable.cheese_1;
            case 1:
                return R.drawable.cheese_2;
            case 2:
                return R.drawable.cheese_3;
            case 3:
                return R.drawable.cheese_4;
            case 4:
                return R.drawable.cheese_5;
        }
    }
   // public static  final String serverUrl= "http://192.168.14.194:8080/AppServices/rest/";
    public static  final String serverUrl= "http://54.149.184.216:8080/WebServices/rest/";

    public static final String[] sEmpStrings = {
            "Anoop ", "Aparna", "Vinay", "Sunita", "Prabhat",
            "Amit", "Priyanka", "Mukti ", "Alok", "Partha",
            "Babu", "Nitin", "Mustafa", "Vaibhav", "Risab",
            "Nikita", "Meghana", "Ganjikunta", "Asraf", "Ansu",
            "Ashustosh", "Visnu", "Suma", "Doot", "Rajiv",
            "Nikesh"
    };

    public static final String[] cardHeadings = {
            "Employee Id", "Name", "Designation", "Contact No" , "Emergency Contact No",
            "Joining date", "Prasent address", "Permanent address", "Personal email", "Professional email",

            "Emergency Contact Person Name" ,"Emergency Person Relation" , "Project", "Reporting Manager", "SkillSet",
            "Blood Group", "Hobbies","Pan", "BirthDate","Role"
    };

    public String getUserRoleForView() {
        return userRoleForView;
    }

    public void setUserRoleForView(String userRoleForView) {
        this.userRoleForView = userRoleForView;
    }

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    public String getEmpIdForPersonalDetail() {
        return empIdForPersonalDetail;
    }

    public void setEmpIdForPersonalDetail(String empIdForPersonalDetail) {
        this.empIdForPersonalDetail = empIdForPersonalDetail;
    }
}
