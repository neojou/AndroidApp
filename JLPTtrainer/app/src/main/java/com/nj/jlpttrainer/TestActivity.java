package com.nj.jlpttrainer;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


import com.nj.jlpttrainer.databinding.ActivityMainBinding;


public class TestActivity extends AppCompatActivity
        implements View.OnClickListener
{
    private static final String TAG="JLPT_trainer:TestActivity";

    private QuestionDataViewModel q_dvm;

    FragmentManager fragment_manager;
    FragmentQuestion frag_question;
    FragmentImage frag_image;
    FragmentSetting frag_setting;

    Button button_load_db;
    Button button_start;
    Button button_answer;
    Button button_next;
    Button button_finished;
    Button button_setting;
    Button button_return;

    boolean isDBloaded;
    boolean isStarted;
    boolean isFinished;
    boolean isAnswered;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState == null) {
            bindingViewItems();
            setButtons();
            setFragments();

            isDBloaded = false;
            isStarted = false;
            isFinished = false;
            isAnswered = false;
            frag_question.q_id = 0;
        }
    }

    private void bindingViewItems() {
        ActivityMainBinding binding;

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        q_dvm = new QuestionDataViewModel(getApplication());
        binding.setQDvm(q_dvm);
        button_load_db = binding.buttonLoadDb;
        button_start = binding.buttonStart;
        button_answer = binding.buttonAnswer;
        button_next = binding.buttonNext;
        button_finished = binding.buttonFinished;
        button_setting = binding.buttonSetting;
        button_return = binding.buttonReturn;
    }

    private void setFragments() {
        frag_question = new FragmentQuestion(q_dvm);
        frag_image = new FragmentImage();
        frag_setting = new FragmentSetting(q_dvm);

        fragment_manager = getSupportFragmentManager();
        FragmentTransaction ft = fragment_manager.beginTransaction();
        ft.add(R.id.fragment_main, frag_image, "Image");
        ft.commit();

        set_buttons_to_main();
    }

    private void setButtons() {
        set_button_click_listener();
    }

    private void set_button_click_listener() {
        button_load_db.setOnClickListener(this);
        button_start.setOnClickListener(this);
        button_next.setOnClickListener(this);
        button_answer.setOnClickListener(this);
        button_finished.setOnClickListener(this);
        button_setting.setOnClickListener(this);
        button_return.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.button_start)
            to_question_frag();
        else if (id == R.id.button_answer)
            to_check_answer();
        else if (id == R.id.button_next)
            to_next_question();
        else if (id == R.id.button_finished)
            to_question_finished();
        else if (id == R.id.button_load_db)
            to_load_db();
        else if (id == R.id.button_setting)
            to_setting();
        else if (id == R.id.button_return)
            return_to_main();
    }

    private void to_load_db() {
        Log.v(TAG, "to_load_db");
        q_dvm.load_db();
    }

    private void to_question_frag() {
        Log.v(TAG, "to_question_frag");
        isStarted = true;
        isFinished = false;
        isAnswered = false;
        set_buttons_to_question();
    }

    private void to_question_finished() {
        Log.v(TAG, "to_question_frag");
        isFinished = true;
        set_buttons_to_return_only();
    }

    private void to_check_answer() {
        Log.v(TAG, "check_answer");
        isAnswered = true;
        set_buttons_to_question();
        /*
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

         */
    }


    private void to_next_question() {
        Log.v(TAG, "next_question");
        isAnswered = false;
        set_buttons_to_question();
        /*
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

         */
    }

    private void generate_question() {
        Log.v(TAG, "generate_question");

        /*
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

         */
    }

    private void to_setting() {
        Log.v(TAG, "setting");

        FragmentTransaction ft = fragment_manager.beginTransaction();
        if (frag_question != null )
            ft.hide(frag_question);

        if (frag_image != null)
            ft.hide(frag_image);

        if (frag_setting != null) {
            if (frag_setting.isAdded()) {
                ft.show(frag_setting);
            } else {
                ft.add(R.id.fragment_main, frag_setting);
            }
        }
        ft.addToBackStack("HOME");
        ft.commit();

        set_buttons_to_return_only();
    }

    private void return_to_main() {
        Log.v(TAG, "return to main");

        fragment_manager.popBackStack("HOME", FragmentManager.POP_BACK_STACK_INCLUSIVE);
        set_buttons_to_main();
    }

    private void set_buttons_to_main() {
        button_load_db.setVisibility(View.VISIBLE);
        button_start.setVisibility(View.VISIBLE);
        button_finished.setVisibility(View.GONE);
        button_answer.setVisibility(View.GONE);
        button_next.setVisibility(View.GONE);
        button_setting.setVisibility(View.VISIBLE);
        button_return.setVisibility(View.GONE);
    }

    private void set_buttons_to_question() {
        button_load_db.setVisibility(View.GONE);
        button_start.setVisibility(View.GONE);
        button_answer.setVisibility(View.VISIBLE);
        button_next.setVisibility(View.VISIBLE);
        button_finished.setVisibility(View.VISIBLE);
        if (!isAnswered) {
            button_answer.setEnabled(true);
            button_next.setEnabled(false);
            button_finished.setEnabled(false);
        } else {
            button_answer.setEnabled(false);
            button_next.setEnabled(true);
            button_finished.setEnabled(true);
        }
        button_setting.setVisibility(View.GONE);
        button_return.setVisibility(View.GONE);
    }

    private void set_buttons_to_return_only() {
        button_load_db.setVisibility(View.GONE);
        button_start.setVisibility(View.GONE);
        button_answer.setVisibility(View.GONE);
        button_next.setVisibility(View.GONE);
        button_finished.setVisibility(View.GONE);
        button_setting.setVisibility(View.GONE);
        button_return.setVisibility(View.VISIBLE);
    }

}
