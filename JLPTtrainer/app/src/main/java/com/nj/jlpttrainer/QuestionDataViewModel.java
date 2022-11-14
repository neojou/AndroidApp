package com.nj.jlpttrainer;

import android.app.Application;
import android.os.Handler;
import android.util.Log;

import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class QuestionDataViewModel {
    private static final String TAG="JLPT_trainer:QuestionDataViewModel";

    public final ObservableBoolean isLoading = new ObservableBoolean(false);

    private QuestionRepository q_rep;
    int total_questions = 0;
    List<Question> questions;

    interface onDataReadyCallback {
        void onDataReady(List<Question> questions);
    }

    public QuestionDataViewModel(Application application) {
        q_rep = new QuestionRepository(application);;
    }

    public void issueGetTotalNumber() {
        isLoading.set(true);
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.submit(()-> {
            total_questions = (int)q_rep.count();
            isLoading.set(false);
        });
        executor.shutdown();
    }

    void issueGetAllQuestions(final onDataReadyCallback callback) {
        isLoading.set(true);
        ScheduledExecutorService ses = Executors.newScheduledThreadPool(1);
        Runnable task = () -> {
            Log.v(TAG, "issueGetAllQuestions()");
            List<Question> questions = q_rep.getAllQuestions();
            callback.onDataReady(questions);
            /*
            if (questions != null) {
                Question.log_dump(questions);
                total_questions = questions.size();
            }

            isLoading.set(false);
            */
        };
        ses.schedule(task, 5, TimeUnit.SECONDS);
        ses.shutdown();
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
