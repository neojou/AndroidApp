package com.nj.jlpttrainer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.RadioGroup;
import android.widget.RadioButton;

import java.lang.Integer;
import java.util.ArrayList;
import java.util.List;

import com.nj.jlpttrainer.databinding.ActivityMainBinding;


public class TestActivity extends AppCompatActivity
    implements View.OnClickListener
{
    private static final String TAG="JLPT_trainer:TestActivity";
    private ActivityMainBinding binding;
    private QuestionDataViewModel q_dvm;

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
    boolean isFinished;
    int q_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        //setViewItems();
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        setViewItemsBinding();

        q_dvm = new QuestionDataViewModel(getApplication());
        binding.setQDvm(q_dvm);

        isStarted = false;
        isFinished = false;
        q_id = 0;
        button_next.setText(getString(R.string.button_start));
        set_button_click_listener();

        /* setVisibility : View.VISIBLE, View.INVISIBLE, View.GONE */
        //question_title.setVisibility(View.GONE);
        //choice_title.setVisibility(View.GONE);
        //choice_rd.setVisibility(View.GONE);

        /* once q_dvm finished commands, isLoading is set to false,
         *  and can start to click the buttons.
         */
        q_dvm.issueGetAllQuestions(new QuestionDataViewModel.onDataReadyCallback() {
            @Override
            public void onDataReady(List<Question> lq) {
                Log.v(TAG, "onDataReady");
                q_dvm.questions = lq;
                q_dvm.total_questions = q_dvm.questions.size();
                q_dvm.isLoading.set(false);
            }
        });

        set_screen_without_question();
    }

    /*
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

     */

    private void setViewItemsBinding() {
        question_title = binding.questionTitle;
        question_content = binding.questionContent;
        choice_title = binding.choiceTitle;
        choice_rd = binding.choices;
        choice_rb[0] = binding.choice1;
        choice_rb[1] = binding.choice2;
        choice_rb[2] = binding.choice3;
        choice_rb[3] = binding.choice4;
        button_answer = binding.buttonAnswer;
        button_next = binding.buttonNext;
        button_setting = binding.buttonSetting;
    }

    private void set_question_to_view(Question q) {
        question_title.setText(getString(R.string.each_question_title, q.id));
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
        set_screen_with_question();
    }

    private void set_button_click_listener() {
        button_next.setOnClickListener(this);
        button_answer.setOnClickListener(this);
        button_setting.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.button_answer)
            check_answer();
        else if (id == R.id.button_next)
            next_question();
        else if (id == R.id.button_setting)
            choice_title.setText("Setting");
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

    private void set_screen_without_question() {
        question_content.setVisibility(View.INVISIBLE);
        choice_title.setVisibility(View.INVISIBLE);
        choice_rd.setVisibility(View.INVISIBLE);
        button_answer.setEnabled(false);
        button_next.setEnabled(true);
    }

    private void set_screen_with_question() {
        question_content.setVisibility(View.VISIBLE);
        choice_title.setVisibility(View.VISIBLE);
        choice_rd.setVisibility(View.VISIBLE);
        button_answer.setEnabled(true);
        button_next.setEnabled(false);
    }

    private void check_answer() {
        if (isStarted == false)
            return;
        if (isFinished == true)
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
        button_answer.setEnabled(false);
        button_next.setEnabled(true);
    }

    /*
    private void init_db() {
        Question question = new Question();
        question.set_to_q1();
        q_rep.insert(question);
        question = new Question();
        question.set_to_q2();
        q_rep.insert(question);
        question = new Question();
        question.set_to_q3();
        q_rep.insert(question);
        question = new Question();
        question.set_to_q4();
        q_rep.insert(question);
        question = new Question();
        question.set_to_q5();
        q_rep.insert(question);
        question = new Question();
        question.set_to_q6();
        q_rep.insert(question);
    }

     */

    private void next_question() {
        //Log.v(TAG, "next_question");
        int total_questions = q_dvm.total_questions;

        if (isFinished)
            return;

        if (!isStarted) { // first click
            button_next.setText(getString(R.string.button_next));
            isStarted = true;
            isFinished = false;
            q_id = 0;

            Log.v(TAG, "next_questions() : total_questions = " + Integer.toString(total_questions));
            Log.d(TAG, "total questions = " + Integer.toString(total_questions));
            question_title.setText(getString(R.string.total_question_title, total_questions));
            //q_rep.rearrange(questions);

            //if (db_size <= 0)
            //    init_db();
        }

        if (q_id >= total_questions) {
            isFinished = true;
            button_next.setText(getString(R.string.button_result));
            question_title.setText(getString(R.string.question_title));
            set_screen_without_question();
        }
        if (isFinished == false) {
            q = generate_question();
        }
    }

    private Question generate_question() {
        List<Question> questions = q_dvm.questions;

        if (questions == null || questions.isEmpty() ) {
            Log.e(TAG, "CANNOT access Questions DB");
            q.generate_question();
        } else {
            //Log.v(TAG, "show all questions");
            //Question.log_dump(questions);
        }

        Question q1 = (Question)questions.get(q_id);
        if (q1 == null) {
            Log.e(TAG, "generate_question list error");
            q.generate_question();
        } else {
            q = q1;
            q_id++;
        }
        //Log.d(TAG, "next q1: " + Integer.toString(q1.id) + " : " + q1.question);

        set_question_to_view(q);
        return q;
    }
}

