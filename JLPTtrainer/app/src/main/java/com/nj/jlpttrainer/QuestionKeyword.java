package com.nj.jlpttrainer;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.LinkedHashSet;

public class QuestionKeyword {
    private static final String TAG="JLPT_trainer:QuestionKeyword";

    public int id;
    public Set<String> keywords = new LinkedHashSet<String>();

    public void log_dump() {
        Log.d(TAG, "Question id: " + Integer.toString(id));
        Log.d(TAG, "keywords: " + keywords.toString());
    }

}
