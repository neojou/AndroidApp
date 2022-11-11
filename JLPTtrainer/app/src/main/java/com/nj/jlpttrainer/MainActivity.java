package com.nj.jlpttrainer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.widget.*;

public class MainActivity extends AppCompatActivity {
    Question q;
    TextView question_content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
}
