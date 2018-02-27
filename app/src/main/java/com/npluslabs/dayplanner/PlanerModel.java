package com.npluslabs.dayplanner;

/**
 * Created by Sumit on 22-Feb-18.
 */

public class PlanerModel {
    String  taskName;
    String timeToComplete;
    String impact;
    String urgency;
    String deadLine;
    String probabilityofSuccess;
    String workSequence;
    String effortsToComplete;
    String normaliseTimeToComplete;

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
