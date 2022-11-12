package com.nj.jlpttrainer;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.view.View;


public class TestActivity extends MainActivity
    implements View.OnClickListener
{
    Question q;
    TextView question_title;
    TextView question_content;
    TextView choice_title;
    Button choice1;
    Button choice2;
    Button choice3;
    Button choice4;
    Button button_answer;
    Button button_next;
    Button button_setting;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setViewItems();
        generate_question();
        set_button_click_listener(q);
    }

    private void setViewItems() {
        question_title = (TextView)findViewById(R.id.question_title);
        question_content = (TextView)findViewById(R.id.question_content);
        choice_title = (TextView)findViewById(R.id.choice_title);
    }

    private void generate_question() {
        q = new Question();
        q.generate_question();
        set_question_to_view(q);
    }

    private void set_question_to_view(Question q) {
        question_content.setText(q.question);

        choice1 = (Button)findViewById(R.id.answer1);
        choice1.setText(q.answers[1]);

        choice2 = (Button)findViewById(R.id.answer2);
        choice2.setText(q.answers[2]);

        choice3 = (Button)findViewById(R.id.answer3);
        choice3.setText(q.answers[3]);

        choice4 = (Button)findViewById(R.id.answer4);
        choice4.setText(q.answers[4]);

    }

    private void set_button_click_listener(Question q) {
        button_answer = (Button)findViewById(R.id.button_answer);
        button_answer.setOnClickListener(this);
        button_next = (Button)findViewById(R.id.button_next);
        button_next.setOnClickListener(this);
        button_setting = (Button)findViewById(R.id.button_setting);
        button_setting.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_answer:
                choice_title.setText("Answer");
                break;
            case R.id.button_next:
                choice_title.setText("Next");
                break;
            case R.id.button_setting:
                choice_title.setText("Setting");
                break;
        }
    }
}

