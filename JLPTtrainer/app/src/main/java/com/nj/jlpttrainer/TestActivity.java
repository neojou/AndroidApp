package com.nj.jlpttrainer;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.RadioGroup;
import android.widget.RadioButton;
import android.view.View;
import android.content.res.ColorStateList;
import androidx.core.content.ContextCompat;

import java.lang.Integer;


public class TestActivity extends MainActivity
    implements View.OnClickListener
{
    Question q;
    TextView question_title;
    TextView question_content;
    TextView choice_title;
    RadioGroup choice_rd;
    RadioButton[] choice;
    Button button_answer;
    Button button_next;
    Button button_setting;

    public TestActivity() {
        q = new Question();
        choice = new RadioButton[4];
    }

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
        choice_rd = (RadioGroup)findViewById(R.id.choices);
        choice[0] = (RadioButton)findViewById(R.id.choice1);
        choice[1] = (RadioButton)findViewById(R.id.choice2);
        choice[2] = (RadioButton)findViewById(R.id.choice3);
        choice[3] = (RadioButton)findViewById(R.id.choice4);
    }

    private void generate_question() {
        q.generate_question();
        set_question_to_view(q);
    }

    private void set_question_to_view(Question q) {
        question_content.setText(q.question);
        choice_title.setText(getString(R.string.choice_title));
        choice_rd.clearCheck();
        choice[0].setText(q.choices[0]);
        choice[0].setTextColor(R.color.gray);
        choice[1].setText(q.choices[1]);
        choice[1].setTextColor(R.color.gray);
        choice[2].setText(q.choices[2]);
        choice[2].setTextColor(R.color.gray);
        choice[3].setText(q.choices[3]);
        choice[3].setTextColor(R.color.gray);
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
                check_answer();
                break;
            case R.id.button_next:
                next_question();
                break;
            case R.id.button_setting:
                choice_title.setText("Setting");
                break;
        }
    }

    private void set_radio_button_color(RadioButton rb, int color) {
        rb.setTextColor(ContextCompat.getColorStateList(this, color));
    }

    private void set_right_choice_button(int which) {
        int id = which - 1;
        if (id < 0 || id > 3) return;
        set_radio_button_color(choice[id], R.color.blue);
        //choice[id].setChecked(true);
    }

    private void set_wrong_choice_button(int which) {
        int id = which - 1;
        if (id < 0 || id > 3) return;
        set_radio_button_color(choice[id], R.color.fuchsia);
        //choice[id].setTextColor(R.color.fuchsia);
    }

    private void check_answer() {
        q.answer = 0;
        switch(choice_rd.getCheckedRadioButtonId()) {
            case R.id.choice1:
                q.answer = 1;
                break;
            case R.id.choice2:
                q.answer = 2;
                break;
            case R.id.choice3:
                q.answer = 3;
                break;
            case R.id.choice4:
                q.answer = 4;
                break;
        }
        choice_title.setText(R.string.choice_title + " : " + R.string.correct);

        set_right_choice_button(q.right_choice);
        if (q.answer != q.right_choice) {
            set_wrong_choice_button(q.answer);
            choice_title.setText(getString(R.string.choice_title) + " : " +
                                getString(R.string.wrong));
        } else {
            choice_title.setText(getString(R.string.choice_title) + " : " +
                                getString(R.string.correct));
        }
    }

    private void next_question() {
        generate_question();
    }
}

