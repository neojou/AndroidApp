package com.nj.jlpttrainer;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

import com.nj.jlpttrainer.databinding.FragmentSettingBinding;


public class FragmentSetting extends Fragment
        implements View.OnClickListener
{
    private static final String TAG="JLPT_trainer:FragmentSetting";
    private QuestionDataViewModel q_dvm;

    FragmentSettingBinding binding;
    QuestionDataViewModel.onDataReadyCallback data_rdy_callback;

    View view;
    Button button_import;
    TextView setting_status;

    public FragmentSetting(QuestionDataViewModel m)
    {
        super(R.layout.fragment_setting);
        q_dvm = m;
        view = null;
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_setting, container, false);
        binding.setQDvm(q_dvm);
        view = binding.getRoot();
        if (view == null)
            Log.e(TAG, "OnCreateView() : view is null");
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        view = null;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setViewItemsBinding();
        set_button_click_listener();
        data_rdy_callback = new QuestionDataViewModel.onDataReadyCallback() {
            @Override
            public void onDataReady(List<Question> lq) {
                Log.v(TAG, "onDataReady with List");
                setting_status.setText(getString(R.string.setting_status_completed));
            }
        };
    }

    private void setViewItemsBinding() {
        //button_import = view.findViewById(R.id.setting_button_import);
        button_import = binding.settingButtonImport;
        setting_status = binding.settingStatus;
    }

    private void set_button_click_listener() {
        button_import.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.setting_button_import)
            questions_import();
    }

    private void questions_import() {
        Log.v(TAG, "question_import()");

        setting_status.setText(getString(R.string.setting_status_in_progress));
        q_dvm.importFromTxtFile(data_rdy_callback);
        Log.v(TAG, "question_import() issued");
    }
}
