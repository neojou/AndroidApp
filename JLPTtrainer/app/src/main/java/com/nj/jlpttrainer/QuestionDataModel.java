package com.nj.jlpttrainer;

import android.app.Application;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class QuestionDataModel {
    private QuestionRepository q_rep;
    int total;
    List<Question> questions;



    public QuestionDataModel(Application application) {
        q_rep = new QuestionRepository(application);;
    }

    int getQuestionsTotalNumber() {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(()-> {
            total = (int)q_rep.count();
        });
        executor.shutdown();
        return total;
    }

    List<Question> getAllQuestions() {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(()->{
            questions = q_rep.getAllQuestions();
        });
        executor.shutdown();
        return questions;
    }
    public List<Question> getQuestionById(int id) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(()->{
            questions = q_rep.getQuestionById(id);
        });
        executor.shutdown();
        return questions;
    }


}
