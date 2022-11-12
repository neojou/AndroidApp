package com.nj.jlpttrainer;

public class Question {
    String question;
    String[] answers;
    int right_choice;
    int choice;

    public Question() {
        answers = new String[5];
        choice = 0; // User didn't select yet
    }

    /*
    public void generate_question() {
        question = "都心（　　　　）、このあたりはまだ緑が多く残っていて鳥の声も聞こえる。";
        answers[1] = "とあって";
        answers[2] = "とはいえ";
        answers[3] = "ときたら";
        answers[4] = "とあれば";
        right_choice = 2;
    }

    public void generate_question() {
        question = "子供では（　　　　　）、自分のことぐらい自分でしなさい";
        answers[1] = "あるまいし";
        answers[2] = "あれ";
        answers[3] = "あろうが";
        answers[4] = "なくて";
        right_choice = 1;
    }


    public void generate_question() {
        question = "家庭は子供が大部分の時間を過ごす場所である。（　　　　　）、私は家庭でのしつけが一番重要だとかんがえるのです。";
        answers[1] = "それなりに";
        answers[2] = "それゆえに";
        answers[3] = "それこそが";
        answers[4] = "それにひきかえ";
        right_choice = 2;
    }
*/

    public void generate_question() {
        question = "彼は貧しさ（　　　　）十分な教育が受けられなかった。";
        answers[1] = "ゆえに";
        answers[2] = "なりに";
        answers[3] = "ながから";
        answers[4] = "なしに";
        right_choice = 1;
    }
};


