package com.nj.jlpttrainer;

public class Question {
    String question;
    String[] choices;
    int right_choice;
    int answer;

    public Question() {
        choices = new String[4];
        answer = 0; // User didn't select yet
    }

    /*
    public void generate_question() {
        question = "都心（　　　　）、このあたりはまだ緑が多く残っていて鳥の声も聞こえる。";
        choices[0] = "とあって";
        choices[1] = "とはいえ";
        choices[2] = "ときたら";
        choices[3] = "とあれば";
        right_choice = 2;
        answer=0;
    }

    public void generate_question() {
        question = "子供では（　　　　　）、自分のことぐらい自分でしなさい";
        choices[0] = "あるまいし";
        choices[1] = "あれ";
        choices[2] = "あろうが";
        choices[3] = "なくて";
        right_choice = 1;
        answer=0;
    }


    public void generate_question() {
        question = "家庭は子供が大部分の時間を過ごす場所である。（　　　　　）、私は家庭でのしつけが一番重要だとかんがえるのです。";
        choices[0] = "それなりに";
        choices[1] = "それゆえに";
        choices[2] = "それこそが";
        choices[3] = "それにひきかえ";
        right_choice = 2;
        answer=0;
    }


    public void generate_question() {
        question = "彼は貧しさ（　　　　）十分な教育が受けられなかった。";
        choices[0] = "ゆえに";
        choices[1] = "なりに";
        choices[2] = "ながから";
        choices[3] = "なしに";
        right_choice = 1;
        answer=0;
    }



    public void generate_question() {
        question = "さんざん考えた末、私（　　　　）作った企画がこれです。";
        choices[0] = "だけに";
        choices[1] = "ゆえに";
        choices[2] = "ように";
        choices[3] = "なりに";
        right_choice = 4;
        answer=0;
    }
*/
    public void generate_question() {
        question = "国民の期待というプレッシャーの大きさは（　　　　　　　　）が、彼女はそれを見事に克服して、金メダルを獲得した。";
        choices[0] = "想像にかたくない";
        choices[1] = "想像できな";
        choices[2] = "想像できることはできる";
        choices[3] = "想像することはない";
        right_choice = 1;
        answer=0;
    }

};


