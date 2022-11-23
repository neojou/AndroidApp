package com.nj.jlpttrainer;

import android.util.Log;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;


public class QuestionKeywordRepository {
    private static final String TAG="JLPT_trainer:QuestionKeywordRepositoryl";

    private ArrayList<QuestionKeyword>  qks;
    Random random = new Random();


    public QuestionKeywordRepository(int size) {
        qks = new ArrayList<QuestionKeyword>(size);
    }

    private int findByIdMinMax(int id, int minPos, int maxPos) {
        int middle = (minPos + maxPos) / 2;

        if (middle == maxPos) return -1; // not found
        QuestionKeyword qk = qks.get(middle);
        if (qk != null) {
            if (qk.id == id) return middle;
            else if (qk.id > id)
                return findByIdMinMax(id, middle, maxPos);
            else if (qk.id < id)
                return findByIdMinMax(id, minPos, middle);
        }
        return -1;
    }

    public int findById(int id) {
        return findByIdMinMax(id, 0, qks.size());
    }

    private void addByIdMinMax(int id, String keyword, int minPos, int maxPos) {
        Log.d(TAG, "addByIdMinMax: " + "id=" + Integer.toString(id)
                    + ", minPos=" + Integer.toString(minPos)
                    + " maxPos=" + Integer.toString(maxPos) );

        int middle = (minPos + maxPos) / 2;

        if (middle >= maxPos) { // add new
            QuestionKeyword qk = new QuestionKeyword();
            qk.id = id;
            qk.keywords.add(keyword);
            if (middle >= qks.size())
                qks.add(qk);
            else
                qks.add(middle, qk);
        }

        QuestionKeyword qk = qks.get(middle);
        if (qk != null) {
            if (qk.id == id)
                qk.keywords.add(keyword);
            else if (qk.id > id)
                addByIdMinMax(id, keyword, middle + 1, maxPos);
            else if (qk.id < id)
                addByIdMinMax(id, keyword, minPos, middle - 1);
        }
    }

    public void addById(int id, String keyword) {
        addByIdMinMax(id, keyword, 0, qks.size());
    }

    public void addByKeywordIndex(KeywordIndex ki) {
        QuestionKeyword qk;

        String keyword = ki.keyword;
        for (int i = 0; i < ki.indexArray.length; i++) {
            int id = ki.indexArray[i];
            addById(id, keyword);
        }
    }

    public void log_dump() {
        Log.d(TAG, "QuestionKeywords: ");
        for (QuestionKeyword qk : qks) {
            qk.log_dump();
        }
    }

    public int size() {
        return qks.size();
    }

    public QuestionKeyword getRandomQuestionKeyword() {
        int random_id;
        int size = qks.size();
        random_id = random.nextInt(size);
        return qks.get(random_id);
    }

    public List<Integer> getAllIDs() {
        ArrayList<Integer> aids = new ArrayList<>();
        for (QuestionKeyword qk : qks) {
            aids.add(new Integer(qk.id));
        }
        return aids;
    }

}
