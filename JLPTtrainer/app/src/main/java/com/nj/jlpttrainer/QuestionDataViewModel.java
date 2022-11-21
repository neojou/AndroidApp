package com.nj.jlpttrainer;

import android.app.Application;
import android.os.Handler;
import android.util.Log;

import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.xml.parsers.DocumentBuilderFactory;

public class QuestionDataViewModel {
    private static final String TAG="JLPT_trainer:QuestionDataViewModel";

    public final ObservableBoolean isLoading = new ObservableBoolean(false);
    public final ObservableBoolean isDBloaded = new ObservableBoolean(false);

    Application app;
    private QuestionRepository q_rep;
    int total_questions = 0;
    List<Question> questions;

    interface onDataReadyCallback {
        void onDataReady(List<Question> questions);
    }

    public QuestionDataViewModel(Application application) {
        app = application;
        q_rep = new QuestionRepository(app);;
    }

    public void importFromTxtFile(final onDataReadyCallback callback)  {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        isLoading.set(true);
        executor.submit(()-> {
            import_from_txt_file_thread();
            callback.onDataReady(null);
            isLoading.set(false);
        });
        executor.shutdown();
    }

    public void load_db() {
        issueGetAllQuestions(new QuestionDataViewModel.onDataReadyCallback() {
            @Override
            public void onDataReady(List<Question> lq) {
                questions = lq;
                total_questions = questions.size();
                Log.v(TAG, "total_questions :" + Integer.toString(total_questions));
                isLoading.set(false);
                isDBloaded.set(true);
            }
        });
    }

    void issueGetAllQuestions(final onDataReadyCallback callback) {
        isLoading.set(true);
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.submit(()-> {
            Log.v(TAG, "issueGetAllQuestions()");
            List<Question> lq = q_rep.getAllQuestions();
            callback.onDataReady(lq);
        });
        executor.shutdown();
    }

    private void import_from_txt_file_thread() {
        InputStream is;

        is = app.getResources().openRawResource(R.raw.questions);
        QuestionParser qp = new QuestionParser(is);
        ArrayList<Question> ql = new ArrayList<Question>();
        Question q;
        while ((q = qp.getQuestion()) != null) {
                q.log_dump();
                ql.add(q);
        };
        qp.close();
        q_rep.updateAll_direct(ql);
    }

    /*
    public List<Question> getQuestionById(int id) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.submit(()->{
            questions = q_rep.getQuestionById(id);
        });
        executor.shutdown();
        return questions;
    }
     */
}
