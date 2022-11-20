package com.nj.jlpttrainer;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public class FragmentSetting extends Fragment
        implements View.OnClickListener
{
    private static final String TAG="JLPT_trainer:FragmentSetting";

    View view;
    Button button_import;

    public FragmentSetting()
    {
        super(R.layout.fragment_setting);
        view = null;
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState
    ) {
        view = inflater.inflate(R.layout.fragment_setting, container, false);
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
    }

    private void setViewItemsBinding() {
        if (view != null) {
            button_import = view.findViewById(R.id.setting_button_import);
        }
    }

    private void set_button_click_listener() {
        if (button_import != null)
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
    }
}
