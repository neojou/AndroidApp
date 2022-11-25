package com.nj.jlpttrainer;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class WrongAnswerBook {
    private static final String TAG="JLPT_trainer:WrongAnswerBook";

    final static int TimeToCountDown = 3;

    HashMap<Integer, Question> wrongs = new HashMap<Integer, Question>();
    int timers = TimeToCountDown;

    public void addWrongAnswer(Question q) {
        //Log.d(TAG, "addWrongAnswer: id=" + q.id);
        wrongs.put(new Integer(q.id), q);
    }

    public void removeWrongAnswerById(int id) {
        //Log.d(TAG, "removeWrongAnswer: id=" + id);
        wrongs.remove(id);
    }

    public Question getWrongAnswer() {
        if (!countdown())
            return null;

        Set keySet = wrongs.keySet();
        if (keySet.isEmpty()) {
            Log.e(TAG, "strange HashMap is not empty but key set is empty");
            return null;
        }
        Iterator<Integer> itr = keySet.iterator();
        if (!itr.hasNext()) {
            Log.e(TAG, "strange keySet is not empty but iterator has no Next");
            return null;
        }
        Integer id = itr.next();
        Question q = wrongs.remove(id);
        //Log.d(TAG, "getWrongAnswer: id=" + q.id);
        return q;
    }

    private boolean countdown() {
        if (wrongs.isEmpty()) {
            return false;
        }
        timers--;
        //Log.d(TAG, "timers=" + timers);
        if (timers <= 0) {
            timers = TimeToCountDown;
            return true;
        }
        return false;
    }
}
