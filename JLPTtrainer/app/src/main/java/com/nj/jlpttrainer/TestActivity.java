package com.nj.jlpttrainer;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.constraintlayout.widget.ConstraintLayout;
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
    FragmentTransaction ft;
    FragmentQuestion frag_question;
    FragmentImage frag_image;
    FragmentSetting frag_setting;

    ConstraintLayout window_layout;
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

            fragment_manager = getSupportFragmentManager();
            switch_to_main_image();

            /* adjust the width of the window */
            //DisplayMetrics dm = new DisplayMetrics();
            //getWindowManager().getDefaultDisplay().getMetrics(dm);
            //int vWidth = dm.widthPixels;
            //int vHeight = dm.heightPixels;

            //window_layout.getLayoutParams().width = (int)(vWidth * 0.95);

            isDBloaded = false;
            isStarted = false;
            isFinished = false;
            isAnswered = false;
        }
    }

    private void bindingViewItems() {
        ActivityMainBinding binding;

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        q_dvm = new QuestionDataViewModel(getApplication());
        binding.setQDvm(q_dvm);

        window_layout = binding.windowLayout;
        button_load_db = binding.buttonLoadDb;
        button_start = binding.buttonStart;
        button_answer = binding.buttonAnswer;
        button_next = binding.buttonNext;
        button_finished = binding.buttonFinished;
        button_setting = binding.buttonSetting;
        button_return = binding.buttonReturn;
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
        //Log.d(TAG, "to_load_db");
        q_dvm.load_db();
    }

    private void to_question_frag() {
        //Log.d(TAG, "to_question_frag");

        switch_to_question();

        isStarted = true;
        isFinished = false;
        isAnswered = false;
    }

    private void to_question_finished() {
        //Log.d(TAG, "to_question_finished");

        frag_question.finish_answer();

        isFinished = true;
        set_buttons_to_return_only();
    }

    private void to_check_answer() {
        //Log.d(TAG, "check_answer");

        frag_question.check_answer();

        isAnswered = true;
        set_buttons_to_question();
    }


    private void to_next_question() {
        //Log.v(TAG, "next_question");

        frag_question.next_question();

        isAnswered = false;
        set_buttons_to_question();
    }

    private void to_setting() {
        //Log.v(TAG, "setting");
        switch_to_setting();
    }

    private void return_to_main() {
        //Log.v(TAG, "return to main");
        isAnswered = false;
        switch_to_main_image();
    }

    private void switch_to_main_image() {
        ft_switch_to_main();
        set_buttons_to_main();
    }

    private void switch_to_setting() {
        ft_switch_to_setting();
        set_buttons_to_setting();
    }

    private void switch_to_question() {
        ft_switch_to_question();
        set_buttons_to_question();
    }

    private void ft_clean_all_first() {
        if (frag_setting != null) {
            if (frag_setting.isAdded())
                ft.remove(frag_setting);
            frag_setting = null;
        }
        if (frag_question != null) {
            if (frag_question.isAdded())
                ft.remove(frag_question);
            frag_question = null;
        }
        if (frag_image != null) {
            if (frag_image.isAdded())
                ft.remove(frag_image);
            frag_image = null;
        }
    }

    private void ft_switch_to_question() {
        if (fragment_manager == null) {
            Log.e(TAG, "fragment_manager is null");
            return;
        }
        ft = fragment_manager.beginTransaction();

        ft_clean_all_first();
        frag_question = new FragmentQuestion(q_dvm);
        ft.add(R.id.fragment_main, frag_question);
        ft.commit();

        frag_question.start_to_answer();
    }

    private void ft_switch_to_main() {
        if (fragment_manager == null) {
            Log.e(TAG, "fragment_manager is null");
            return;
        }
        ft = fragment_manager.beginTransaction();

        ft_clean_all_first();
        frag_image = new FragmentImage();
        ft.add(R.id.fragment_main, frag_image);
        ft.commit();
    }

    private void ft_switch_to_setting() {
        if (fragment_manager == null) {
            Log.e(TAG, "fragment_manager is null");
            return;
        }
        ft = fragment_manager.beginTransaction();

        ft_clean_all_first();
        frag_setting = new FragmentSetting(q_dvm);
        ft.add(R.id.fragment_main, frag_setting);
        ft.commit();
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

    private void set_buttons_to_setting() {
        set_buttons_to_return_only();
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
