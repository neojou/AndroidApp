package com.nj.jlpttrainer;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class TestActivity extends MainActivity {
    Question q;
    TextView question_content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        generate_question();
        check_answer();
    }

    private void generate_question() {
        q = new Question();
        q.generate_question();
        set_question_to_view(q);
    }
    private void set_question_to_view(Question q) {
        question_content = (TextView)findViewById(R.id.question_content);
        question_content.setText(q.question);

        Button choice1 = (Button)findViewById(R.id.answer1);
        choice1.setText(q.answers[1]);

        Button choice2 = (Button)findViewById(R.id.answer2);
        choice2.setText(q.answers[2]);

        Button choice3 = (Button)findViewById(R.id.answer3);
        choice3.setText(q.answers[3]);

        Button choice4 = (Button)findViewById(R.id.answer4);
        choice4.setText(q.answers[4]);

    }

    private void check_answer() {
    }

}
