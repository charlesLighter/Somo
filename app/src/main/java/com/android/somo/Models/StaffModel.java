package com.android.somo.Models;

public class StaffModel {
    private String staffName;
    private String staffEmail;
    private String staffUID;

    public StaffModel() {
    }

    public StaffModel(String staffName, String staffEmail) {
        this.staffName = staffName;
        this.staffEmail = staffEmail;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public String getStaffEmail() {
        return staffEmail;
    }

    public void setStaffEmail(String staffEmail) {
        this.staffEmail = staffEmail;
    }

    public String getStaffUID() {
        return staffUID;
    }

    public void setStaffUID(String staffUID) {
        this.staffUID = staffUID;
    }
}
