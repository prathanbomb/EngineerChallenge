package com.weekly.engineer.challenge.data;

public class HistoryData {
    private int no;
    private String subject;
    private int score;

    public HistoryData(int no, String subject, int score) {
        this.no = no;
        this.subject = subject;
        this.score = score;
    }

    public HistoryData(String subject, int score) {
        this.subject = subject;
        this.score = score;
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
