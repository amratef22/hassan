package com.abdo.eduapp.models;

public class ModelQuizGrades {


    String quizName;
    String grade;

    public ModelQuizGrades() {
    }

    public ModelQuizGrades(String quizName) {
        this.quizName = quizName;
        this.grade = "Not Answer";
    }

    public ModelQuizGrades(String quizName, String grade) {
        this.quizName = quizName;
        this.grade = grade;
    }

    public String getQuizName() {
        return quizName;
    }

    public void setQuizName(String quizName) {
        this.quizName = quizName;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }


}
