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
    int db_size;
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
        q_id = 1;
        button_next.setText(getString(R.string.button_start));
        set_button_click_listener();

        /** setVisibility : View.VISIBLE, View.INVISIBLE, View.GONE */
        //question_title.setVisibility(View.GONE);
        //choice_title.setVisibility(View.GONE);
        //choice_rd.setVisibility(View.GONE);

        /** Need some time to trigger the DB once */
        //int count = q_dvm.getQuestionsTotalNumber();
        //question_title.setText(getString(R.string.question_title) + " : " + Integer.toString(count));

        /** once q_dvm finished commands, isLoading is set to false,
         *  and can start to click the buttons.
         */

        q_dvm.issueGetAllQuestions(new QuestionDataViewModel.onDataReadyCallback() {
            @Override
            public void onDataReady(List<Question> lq) {
                Log.v(TAG, "onDataReady");
                q_dvm.questions = lq;
                q_dvm.isLoading.set(false);
            }
        });
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
        question_title.setText(getString(R.string.question_title) + " : " + Integer.toString(q.id));
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
        button_next.setOnClickListener(this);
        button_answer.setOnClickListener(this);
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
        Log.v(TAG, "next_question");

        if (isFinished == true)
            return;

        if (isStarted == false) { // first click
            button_next.setText(getString(R.string.button_next));
            isStarted = true;
            isFinished = false;
            q_id = 1;

            int total_questions = q_dvm.total_questions;
            Log.v(TAG, "next_questions() : total_questions = " + Integer.toString(total_questions));
            Log.d(TAG, "total questions = " + Integer.toString(total_questions));
            question_title.setText(getString(R.string.question_title) + " : " + Integer.toString(total_questions));
            //q_rep.rearrange(questions);

            //if (db_size <= 0)
            //    init_db();
        }

        if (q_id > db_size) {
            isFinished = true;
            button_next.setText(getString(R.string.button_result));
        }
        if (isFinished == false) {
            q = generate_question();
        }
    }

    private Question generate_question() {
        Log.v(TAG, "generate_question");

        List<Question> questions = q_dvm.questions;

        if (questions == null || questions.isEmpty() ) {
            Log.e(TAG, "CANNOT access Questions DB");
            q.generate_question();
        } else {
            Log.v(TAG, "show all questions");
            Question.log_dump(questions);
        }

        Question q1 = (Question)questions.get(0);
        if (q1 == null) {
            Log.e(TAG, "generate_question list error");
            q.generate_question();
        } else {
            q.copy(q1);
            q_id++;
        }
        Log.d(TAG, "next q1: " + Integer.toString(q1.id) + " : " + q1.question);

        set_question_to_view(q);
        Log.v(TAG, "generate_question finished");
        return q;
    }
}

