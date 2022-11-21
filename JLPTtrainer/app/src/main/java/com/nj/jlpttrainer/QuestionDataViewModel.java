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
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.List;
import java.util.Random;

import javax.xml.parsers.DocumentBuilderFactory;

public class QuestionDataViewModel {
    private static final String TAG="JLPT_trainer:QuestionDataViewModel";

    public final ObservableBoolean isLoading = new ObservableBoolean(false);
    public final ObservableBoolean isDBloaded = new ObservableBoolean(false);

    Application app;
    private QuestionRepository q_rep;
    int total_questions = 0;
    List<Question> questions;

    Random random = new Random();

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
                Log.d(TAG, "total_questions :" + Integer.toString(total_questions));
                isLoading.set(false);
                isDBloaded.set(true);
            }
        });
    }

    public void issueGetAllQuestions(final onDataReadyCallback callback) {
        isLoading.set(true);
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.submit(()-> {
            Log.d(TAG, "issueGetAllQuestions()");
            List<Question> lq = q_rep.getAllQuestions();
            callback.onDataReady(lq);
        });
        executor.shutdown();
    }

    public Question randomGetQuestion() {
        Question q;

        if (!isDBloaded.get()) {
            Log.e(TAG, "DB is not loaded yet");
            return null;
        }
        if (total_questions <= 0) {
            Log.e(TAG, "No question in DB");
            return null;
        }
        if (total_questions != questions.size()) {
            Log.e(TAG, "Strange total_questions " + Integer.toString(total_questions) + "!="
                    + "questions Array List size: " + Integer.toString(questions.size()));
            return null;
        }

        int random_id = random.nextInt(total_questions);
        return questions.get(random_id);
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
