package com.nj.jlpttrainer;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


import com.nj.jlpttrainer.databinding.ActivityMainBinding;

import java.util.List;


public class TestActivity extends AppCompatActivity
        implements View.OnClickListener
{
    private static final String TAG="JLPT_trainer:TestActivity";

    private ActivityMainBinding binding;
    private QuestionDataViewModel q_dvm;

    FragmentManager fragment_manager;
    FragmentQuestion frag_question;
    FragmentImage frag_image;
    FragmentSetting frag_setting;

    Button button_answer;
    Button button_next;
    Button button_setting;
    Button button_return;

    boolean isStarted;
    boolean isFinished;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState == null) {

            setViewItemsBinding();
            setButtons();
            setFragments();

            isStarted = false;
            isFinished = false;
            frag_question.q_id = 0;
        }
    }

    private void setViewItemsBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        q_dvm = new QuestionDataViewModel(getApplication());
        binding.setQDvm(q_dvm);
        button_answer = binding.buttonAnswer;
        button_next = binding.buttonNext;
        button_setting = binding.buttonSetting;
        button_return = binding.buttonReturn;
    }

    private void setFragments() {
        frag_question = new FragmentQuestion(q_dvm);
        frag_image = new FragmentImage();
        frag_setting = new FragmentSetting();

        fragment_manager = getSupportFragmentManager();
        if (fragment_manager != null) {
            FragmentTransaction ft = fragment_manager.beginTransaction();
            ft.add(R.id.fragment_main, frag_image, "Image");
            ft.commit();
        } else {
            Log.e(TAG, "fragment_manager is null");
        }

        set_screen_to_main();
    }

    private void setButtons() {
        button_next.setText(getString(R.string.button_start_str));
        set_button_click_listener();
    }

    private void set_button_click_listener() {
        button_next.setOnClickListener(this);
        button_answer.setOnClickListener(this);
        button_setting.setOnClickListener(this);
        button_return.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.button_answer)
            check_answer();
        else if (id == R.id.button_next)
            next_question();
        else if (id == R.id.button_setting)
            setting();
        else if (id == R.id.button_return)
            return_to_main();
    }

    private void check_answer() {
        Log.v(TAG, "check_answer");
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


    private void next_question() {
        Log.v(TAG, "next_question");
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

    private void setting() {
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

        set_screen_to_setting();
    }

    private void return_to_main() {
        Log.v(TAG, "return to main");

        fragment_manager.popBackStack("HOME", FragmentManager.POP_BACK_STACK_INCLUSIVE);
        set_screen_to_main();
    }

    private void set_screen_to_setting() {
        button_answer.setVisibility(View.INVISIBLE);
        button_next.setVisibility(View.INVISIBLE);
        button_setting.setVisibility(View.INVISIBLE);
        button_return.setVisibility(View.VISIBLE);
    }

    private void set_screen_to_main() {
        button_answer.setVisibility(View.VISIBLE);
        button_next.setVisibility(View.VISIBLE);
        button_setting.setVisibility(View.VISIBLE);
        button_return.setVisibility(View.GONE);
    }

}
