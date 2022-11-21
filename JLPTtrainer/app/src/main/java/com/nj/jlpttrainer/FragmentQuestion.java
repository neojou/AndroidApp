package com.nj.jlpttrainer;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.nj.jlpttrainer.databinding.FragmentQuestionBinding;



import java.util.List;
import java.lang.Integer;


public class FragmentQuestion extends Fragment
{
    private static final String TAG="JLPT_trainer:FragmentQuestion";
    private QuestionDataViewModel q_dvm;

    FragmentQuestionBinding binding;

    Question q = new Question();
    TextView question_title;
    TextView question_content;
    TextView choice_title;
    RadioGroup choice_rd;
    RadioButton[] choice_rb = new RadioButton[4];
    String[] choices = new String[4];
    int q_id;

    public FragmentQuestion(QuestionDataViewModel m)
    {
        super(R.layout.fragment_question);
        q_dvm = m;
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState
        ) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_question, container, false);
        setViewItemsBinding();
        binding.setQDvm(q_dvm);
        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

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



    private void set_radio_button_color(RadioButton rb, int color) {
        rb.setTextColor(ContextCompat.getColorStateList(getContext(), color));
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
    }

    private void set_screen_with_question() {
        question_content.setVisibility(View.VISIBLE);
        choice_title.setVisibility(View.VISIBLE);
        choice_rd.setVisibility(View.VISIBLE);
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
}

