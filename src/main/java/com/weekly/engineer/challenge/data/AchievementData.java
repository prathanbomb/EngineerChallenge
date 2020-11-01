package com.weekly.engineer.challenge.data;

public class AchievementData {
    private String name;
    private int condition;
    private int goal;
    private String detail;
    private boolean type;
    private boolean have;
    private int imgId;

    public AchievementData(String name,int condition, int goal, String detail, int have,int imgId) {
        this.name = name;
        this.condition = condition;
        this.goal = goal;
        this.detail = detail;
        this.have = (have == 1) ? true : false;
        this.imgId = imgId;

    }

    public AchievementData(String name,int condition, int goal, String detail, boolean have,int imgId) {
        this.name = name;
        this.condition = condition;
        this.goal = goal;
        this.detail = detail;
        this.have = have;
        this.imgId = imgId;

    }
    public AchievementData(String name,int condition, int goal, String detail, int type, int have,int imgId) {
        this.name = name;
        this.condition = condition;
        this.goal = goal;
        this.detail = detail;
        this.type = (type == 1) ? true : false;
        this.have = (have == 1) ? true : false;
        this.imgId = imgId;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCondition() {
        return condition;
    }

    public void setCondition(int condition) {
        this.condition = condition;
    }

    public int getGoal() {
        return goal;
    }

    public void setGoal(int goal) {
        this.goal = goal;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public boolean isType() {
        return type;
    }

    public void setType(int type) {
        this.type = (type == 1) ? true : false;
    }

    public boolean isHave() {
        return have;
    }

    public void setHave(int have) {
        this.have = (have == 1) ? true : false;
    }

    public int getImgID() {
        return imgId;
    }
    public void setImgId(int goal) {
        this.goal = goal;
    }



}
