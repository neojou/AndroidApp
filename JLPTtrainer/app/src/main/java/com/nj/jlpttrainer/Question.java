package com.nj.jlpttrainer;

public class Question {
    String question;
    String[] answers;
    int right_choice;
    int choice;

    public Question() {
        answers = new String[5];
    }

    /*
    public void generate_question() {
        question = "都心（　　　　）、このあたりはまだ緑が多く残っていて鳥の声も聞こえる。";
        answers[1] = "とあって";
        answers[2] = "とはいえ";
        answers[3] = "ときたら";
        answers[4] = "とあれば";
        right_choice = 2;
        choice = 0;
    }
     */

    public void generate_question() {
        question = "子供では（　　　　　）、自分のことぐらい自分でしなさい";
        answers[1] = "あるまいし";
        answers[2] = "あれ";
        answers[3] = "あろうが";
        answers[4] = "なくて";
        right_choice = 1;
        choice = 0;
    }

};

