package com.android.somo.Models;

public class StemReportsModel {

    private String staffName;
    private String reportId;
    private String role;
    private String sessionDate;
    private long timestamp;
    private String school;
    private String topic;
    private String sessionOverview;
    private String studentEngagement;
    private String demonstratedSkills;
    private String projectProgress;
    private String challengesEncountered;
    private String supportProvided;
    private String nextSteps;
    private String feedback;

    public StemReportsModel(String staffName, String role, String sessionDate,String school, String topic, String sessionOverview, String studentEngagement, String demonstratedSkills, String projectProgress, String challengesEncountered, String supportProvided, String nextSteps, String feedback) {
        this.staffName = staffName;
        this.role = role;
        this.sessionDate = sessionDate;
        this.school = school;
        this.topic = topic;
        this.sessionOverview = sessionOverview;
        this.studentEngagement = studentEngagement;
        this.demonstratedSkills = demonstratedSkills;
        this.projectProgress = projectProgress;
        this.challengesEncountered = challengesEncountered;
        this.supportProvided = supportProvided;
        this.nextSteps = nextSteps;
        this.feedback = feedback;
    }



    public StemReportsModel() {
    } //empty constructor

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public String getReportId() {
        return reportId;
    }

    public void setReportId(String reportId) {
        this.reportId = reportId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getSessionDate() {
        return sessionDate;
    }

    public void setSessionDate(String sessionDate) {
        this.sessionDate = sessionDate;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getSessionOverview() {
        return sessionOverview;
    }

    public void setSessionOverview(String sessionOverview) {
        this.sessionOverview = sessionOverview;
    }

    public String getStudentEngagement() {
        return studentEngagement;
    }

    public void setStudentEngagement(String studentEngagement) {
        this.studentEngagement = studentEngagement;
    }

    public String getDemonstratedSkills() {
        return demonstratedSkills;
    }

    public void setDemonstratedSkills(String demonstratedSkills) {
        this.demonstratedSkills = demonstratedSkills;
    }

    public String getProjectProgress() {
        return projectProgress;
    }

    public void setProjectProgress(String projectProgress) {
        this.projectProgress = projectProgress;
    }

    public String getChallengesEncountered() {
        return challengesEncountered;
    }

    public void setChallengesEncountered(String challengesEncountered) {
        this.challengesEncountered = challengesEncountered;
    }

    public String getSupportProvided() {
        return supportProvided;
    }

    public void setSupportProvided(String supportProvided) {
        this.supportProvided = supportProvided;
    }

    public String getNextSteps() {
        return nextSteps;
    }

    public void setNextSteps(String nextSteps) {
        this.nextSteps = nextSteps;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }
}
