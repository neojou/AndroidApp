package com.nj.jlpttrainer;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.app.Application;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.LiveData;

import java.util.List;

public class QuestionRepository {
    QuestionDatabase questionDB;
    private final QuestionDAO questionDao;
    public long count;
    private List<Question> result;

    public QuestionRepository(Application application) {
        questionDB = QuestionDatabase.getInstance(application);
        questionDao = questionDB.questionDao();
    }

    public long count() {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.submit(()-> {
            count = questionDao.count();
        });
        executor.shutdown();
        return count;
    }

    public void insert(Question q) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.submit(()->{
            questionDao.insert(q);
        });
        executor.shutdown();
    }

    public List<Question> getQuestionById(long id) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.submit(()->{
            result = questionDao.selectById(id);
        });
        executor.shutdown();
        return result;
    }

    public List<Question> getAllQuestions() {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.submit(()->{
            result = questionDao.selectAll();
        });
        executor.shutdown();
        return result;
    }
}
