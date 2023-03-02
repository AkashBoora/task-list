package com.codurance.training.tasks;

import java.util.Date;

public final class Task {
    private final String id;
    private final String description;
    private boolean done;
    private Date deadline;
    private final Date createdDate;

    public Task(String id, String description, boolean done) {
        this.id = id;
        this.description = description;
        this.done = done;
        this.deadline = null;
        this.createdDate = new Date();
    }

    public String getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public Date getDeadline() {
        return deadline;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id='" + id + '\'' +
                ", description='" + description + '\'' +
                ", done=" + done +
                ", deadline=" + deadline +
                ", createdDate=" + createdDate +
                '}';
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public Date getCreatedDate() {
        return createdDate;
    }
}