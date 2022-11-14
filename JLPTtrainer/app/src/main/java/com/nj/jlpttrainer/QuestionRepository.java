package com.nj.jlpttrainer;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.app.Application;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.LiveData;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class QuestionRepository {
    private static final String TAG="JLPT_trainer:QuestionRepository";

    QuestionDatabase questionDB;
    private final QuestionDAO questionDao;
    public long count;
    private List<Question> results;

    public QuestionRepository(Application application) {
        questionDB = QuestionDatabase.getInstance(application);
        questionDao = questionDB.questionDao();
    }

    public long count() {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(()-> {
            count = questionDao.count();
        });
        executor.shutdown();
        return count;
    }

    public void insert(Question q) {
        /*
        Log.v(TAG, "insert");
        List<Question> res = getQuestionByQuestion(q.question);
        Question.log_dump(res);
        if (res != null && res.size() > 0) { // duplicated
            Question d = res.get(0);
            Log.v(TAG, "The question we want to insert has been inserted");
            Log.v(TAG, "DB duplicated number: " + Integer.toString(res.size()));
            Log.v(TAG, Long.toString(d.id) + " " + d.question);
            return;
        }
        */
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(()->{
            questionDao.insert(q);
        });
        executor.shutdown();
    }

    public void deleteQuestionById(int id) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(()->{
            questionDao.deleteById(id);
        });
        executor.shutdown();
    }

    public List<Question> getQuestionById(int id) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(()->{
            results = questionDao.selectById(id);
        });
        executor.shutdown();
        return results;
    }

    public List<Question> getQuestionByQuestion(String qstr) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(()->{
            results = questionDao.selectByQuestion(qstr);
        });
        executor.shutdown();
        return results;
    }

    public List<Question> getAllQuestions() {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(()->{
            results = questionDao.selectAll();
        });
        executor.shutdown();
        return results;
    }

    private void show_question_list(List<Question> qlist) {
        if (qlist != null && !qlist.isEmpty()) {
            int sz = qlist.size();
            for (int i = 0; i < sz; i++) {
                Question q = qlist.get(i);
                if (q != null)
                    Log.d(TAG, Integer.toString(q.id) + " : " + q.question);
            }
        }
    }

    private void clear_null_question(List<Question> qlist) {
        if (qlist == null) return;
        if (qlist.isEmpty()) return;

        //qlist.removeIf(e -> (e.question == null));
        ListIterator<Question> iter_first = qlist.listIterator();
        while (iter_first.hasNext()) {
            Question e = (Question)iter_first.next();
            if (e != null && e.question == null) {
                iter_first.remove();
                deleteQuestionById(e.id);
            }
        }
        show_question_list(qlist);
    }

    private void clear_duplicated(List<Question> qlist) {
        if (qlist == null) return;
        if (qlist.isEmpty()) return;

        Log.d(TAG, "Before clear duplicated");

        int sz = qlist.size();

        for (int i = 0; i < qlist.size(); i++) {
            Question fq = (Question)qlist.get(i);
            Log.d(TAG, "fq: " + Integer.toString(fq.id) + " : " + fq.question);

            for (int j = i + 1; j < qlist.size();) {
                Question nq = (Question)qlist.get(j);
                Log.d(TAG, "nq: " + Integer.toString(nq.id) + " : " + nq.question);
                if (fq.question.compareTo(nq.question) == 0) {
                    qlist.remove(j);
                    deleteQuestionById(nq.id);
                } else {
                    j++;
                }
            }
        }
        Log.d(TAG, "After clear duplicated");

        show_question_list(qlist);

    }

    public void rearrange(List<Question> qlist) {
        clear_null_question(qlist);
        clear_duplicated(qlist);
    }
}