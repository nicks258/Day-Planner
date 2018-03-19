package com.npluslabs.dayplanner.Models;

/**
 * Created by Sumit on 22-Feb-18.
 */

public class PlanerModel {
    String  taskName;
    String timeToComplete;
    String impact;
    String urgency;
    String teamName;
    String deadLine;
    String probabilityofSuccess;
    String workSequence;
    String effortsToComplete;
    String normaliseTimeToComplete;

    public String getDependentTask() {
        return dependentTask;
    }

    public void setDependentTask(String dependentTask) {
        this.dependentTask = dependentTask;
    }

    String dependentTask;
    public String getTaskDescription() {
        return taskDescription;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    String taskDescription;
    public String getdFactor() {
        return dFactor;
    }

    public void setdFactor(String dFactor) {
        this.dFactor = dFactor;
    }

    String dFactor;
    public String getImportance() {
        return importance;
    }

    public void setImportance(String importance) {
        this.importance = importance;
    }

    String importance;
    public String getDaysLeft() {
        return daysLeft;
    }

    public void setDaysLeft(String daysLeft) {
        this.daysLeft = daysLeft;
    }

    String daysLeft;
    public double getFinalScore() {
        return finalScore;
    }

    public void setFinalScore(double finalScore) {
        this.finalScore = finalScore;
    }

    private double finalScore;
    private String normaliseDeadline;


    public String getNormaliseDeadline() {
        return normaliseDeadline;
    }

    public void setNormaliseDeadline(String normaliseDeadline) {
        this.normaliseDeadline = normaliseDeadline;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getNormaliseTimeToComplete() {
        return normaliseTimeToComplete;
    }

    public void setNormaliseTimeToComplete(String normaliseTimeToComplete) {
        this.normaliseTimeToComplete = normaliseTimeToComplete;
    }


    public String getEffortsToComplete() {
        return effortsToComplete;
    }

    public void setEffortsToComplete(String effortsToComplete) {
        this.effortsToComplete = effortsToComplete;
    }


    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTimeToComplete() {
        return timeToComplete;
    }

    public void setTimeToComplete(String timeToComplete) {
        this.timeToComplete = timeToComplete;
    }

    public String getImpact() {
        return impact;
    }

    public void setImpact(String impact) {
        this.impact = impact;
    }

    public String getUrgency() {
        return urgency;
    }

    public void setUrgency(String urgency) {
        this.urgency = urgency;
    }

    public String getDeadLine() {
        return deadLine;
    }

    public void setDeadLine(String deadLine) {
        this.deadLine = deadLine;
    }

    public String getProbabilityofSuccess() {
        return probabilityofSuccess;
    }

    public void setProbabilityofSuccess(String probabilityofSuccess) {
        this.probabilityofSuccess = probabilityofSuccess;
    }

    public String getWorkSequence() {
        return workSequence;
    }

    public void setWorkSequence(String workSequence) {
        this.workSequence = workSequence;
    }
}
