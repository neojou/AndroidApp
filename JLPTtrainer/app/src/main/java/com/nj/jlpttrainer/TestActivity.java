package com.nj.jlpttrainer;

import android.app.Application;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.RadioGroup;
import android.widget.RadioButton;

import androidx.core.content.ContextCompat;
import androidx.lifecycle.AndroidViewModel;

import java.lang.Integer;
import java.util.List;


public class TestActivity extends MainActivity
    implements View.OnClickListener
{
    private static final String TAG="JLPT_trainer:TestActivity";
    private QuestionRepository q_rep;
    Question q = new Question();
    TextView question_title;
    TextView question_content;
    TextView choice_title;
    RadioGroup choice_rd;
    RadioButton[] choice_rb = new RadioButton[4];
    Button button_answer;
    Button button_next;
    Button button_setting;
    String[] choices = new String[4];
    boolean isStarted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setViewItems();
        initial_question_rep();

        isStarted = false;
        button_next.setText(getString(R.string.button_start));

        /** Need some time to trigger the DB once */
        int count = (int)q_rep.count();
        question_title.setText(getString(R.string.question_title) + " : " + Integer.toString(count));

        //q = generate_question(q);
        set_button_click_listener();
    }

    private void setViewItems() {
        question_title = (TextView)findViewById(R.id.question_title);
        question_content = (TextView)findViewById(R.id.question_content);
        choice_title = (TextView)findViewById(R.id.choice_title);
        choice_rd = (RadioGroup)findViewById(R.id.choices);
        choice_rb[0] = (RadioButton)findViewById(R.id.choice1);
        choice_rb[1] = (RadioButton)findViewById(R.id.choice2);
        choice_rb[2] = (RadioButton)findViewById(R.id.choice3);
        choice_rb[3] = (RadioButton)findViewById(R.id.choice4);
        button_answer = (Button)findViewById(R.id.button_answer);
        button_next = (Button)findViewById(R.id.button_next);
        button_setting = (Button)findViewById(R.id.button_setting);
    }

    private Question generate_question(Question q) {
        int count = (int)q_rep.count();
        Log.v(TAG, "db count() = " + Integer.toString(count));

        List<Question> questions = q_rep.getAllQuestions();
        Question.log_dump(questions);

        q.generate_question();
        set_question_to_view(q);
        return q;
    }

    private void initial_question_rep() {
        q_rep = new QuestionRepository(getApplication());;
        /*
        Question question = new Question();
        question.set_to_q1();
        q_rep.insert(q);
         */
    }

    private void set_question_to_view(Question q) {
        int count = (int)q_rep.count();
        question_title.setText(getString(R.string.question_title) + " : " + Integer.toString(count));
        question_content.setText(q.question);
        choice_title.setText(getString(R.string.choice_title));
        choice_rd.clearCheck();
        choices[0] = q.choice1;
        choices[1] = q.choice2;
        choices[2] = q.choice3;
        choices[3] = q.choice4;
        choice_rb[0].setText(choices[0]);
        choice_rb[1].setText(choices[1]);
        choice_rb[2].setText(choices[2]);
        choice_rb[3].setText(choices[3]);
        set_radio_button_color(choice_rb[0], R.color.gray);
        set_radio_button_color(choice_rb[1], R.color.gray);
        set_radio_button_color(choice_rb[2], R.color.gray);
        set_radio_button_color(choice_rb[3], R.color.gray);
    }

    private void set_button_click_listener() {
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
        set_radio_button_color(choice_rb[id], R.color.blue);
        //choice[id].setChecked(true);
    }

    private void set_wrong_choice_button(int which) {
        int id = which - 1;
        if (id < 0 || id > 3) return;
        set_radio_button_color(choice_rb[id], R.color.fuchsia);
    }

    private void check_answer() {
        if (isStarted == false)
            return;

        int answer = 0;
        switch(choice_rd.getCheckedRadioButtonId()) {
            case R.id.choice1:
                answer = 1;
                break;
            case R.id.choice2:
                answer = 2;
                break;
            case R.id.choice3:
                answer = 3;
                break;
            case R.id.choice4:
                answer = 4;
                break;
        }
        choice_title.setText(getString(R.string.choice_title) + " : " + getString(R.string.correct));

        set_right_choice_button(q.right_choice);
        if (answer != q.right_choice) {
            set_wrong_choice_button(answer);
            choice_title.setText(getString(R.string.choice_title) + " : " +
                                getString(R.string.wrong));
        } else {
            choice_title.setText(getString(R.string.choice_title) + " : " +
                                getString(R.string.correct));
        }
    }

    private void next_question() {
        if (isStarted == false) { // first click
            button_next.setText(getString(R.string.button_next));
            isStarted = true;
        }
        generate_question(q);
    }
}

