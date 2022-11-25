package com.nj.jlpttrainer;

import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class QuestionToAnswer {
    private static final String TAG="JLPT_trainer:QuestionToAnswer";

    QuestionHashMap qm;
    LinkedHashSet<Integer> ids;
    ArrayList<Question> questions;


    public QuestionToAnswer(QuestionHashMap m) {
        qm = m;
        ids = new LinkedHashSet<Integer>();
    }

    public void insertAll(List<Integer> data) {
        //Log.d(TAG, "insertALL");
        questions = new ArrayList<Question>();
        for (Integer id : data) {
            Question q = qm.findById(id);
            if (q == null) continue;
            if (!ids.contains(id)) {
                ids.add(id);
                questions.add(q);
            }
        }

        Question.sortByCorrectRate(questions);
        //Question.log_dump(questions);
    }

    public void remove(int id) {
        if (ids.contains(id)) {
            ids.remove(id);
            Question q = qm.findById(id);
            if (q != null)
                questions.remove(q);
        }
    }

    public Question getQuestion() {
        Iterator<Question> qitr;

        qitr = questions.iterator();
        if (qitr.hasNext())
            return qitr.next();
        return null;
    }
}
