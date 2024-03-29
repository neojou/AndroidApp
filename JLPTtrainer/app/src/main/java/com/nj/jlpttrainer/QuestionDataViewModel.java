package com.nj.jlpttrainer;

import android.app.Application;
import android.util.Log;

import androidx.databinding.ObservableBoolean;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;
import java.util.List;
import java.util.LinkedHashSet;
import java.util.Random;
import java.util.Set;

public class QuestionDataViewModel {
    private static final String TAG="JLPT_trainer:QuestionDataViewModel";
    Random random = new Random();

    public final ObservableBoolean isLoading = new ObservableBoolean(false);
    public final ObservableBoolean isDBloaded = new ObservableBoolean(false);

    Application app;
    private QuestionRepository q_rep;
    private QuestionKeywordRepository qk_rep;
    private QuestionToAnswer qta;
    int total_questions = 0;
    QuestionHashMap questions;
    WrongAnswerBook wab = new WrongAnswerBook();


    interface onDataReadyCallback {
        void onDataReady(List<Question> questions);
    }

    public QuestionDataViewModel(Application application) {
        app = application;
        q_rep = new QuestionRepository(app);;
        qk_rep = null;
        qta = null;
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

    private void load_keywords() {
        qk_rep = new QuestionKeywordRepository(total_questions);

        /* keywords.txt */
        InputStream is = app.getResources().openRawResource(R.raw.keywords);
        KeywordIndexParser kp = new KeywordIndexParser(is);
        KeywordIndex ki;
        while ((ki = kp.getKeywordIndex()) != null) {
            //ki.log_dump();
            qk_rep.addByKeywordIndex(ki);
        }
        kp.close();
        //qk_rep.log_dump();
    }

    public void load_db() {
        isDBloaded.set(false);
        issueGetAllQuestions(new QuestionDataViewModel.onDataReadyCallback() {
            @Override
            public void onDataReady(List<Question> lq) {
                questions = new QuestionHashMap();
                questions.insertAll(lq);
                total_questions = questions.size();
                Log.d(TAG, "total_questions :" + Integer.toString(total_questions));

                load_keywords();

                isLoading.set(false);
                isDBloaded.set(true);
            }
        });
    }

    public void start_to_answer() {
        Log.d(TAG, "start_to_answer");
        qta = new QuestionToAnswer(questions);
        qta.insertAll(qk_rep.getAllIDs());
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

    public void removeFromQuestionToAnswer(int id) {
        qta.remove(id);
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

        if ((q = wab.getWrongAnswer()) != null)
            return q;

        if ((q = qta.getQuestion()) != null)
            return q;

        int random_id = random.nextInt(total_questions);
        q = questions.findById(random_id);

        return q;
    }

    private void addIntoWrongBook(Question q) {
        wab.addWrongAnswer(q);
    }

    private void removeFromWrongBook(Question q) {
        wab.removeWrongAnswerById(q.id);
    }

    public void answered_correctly(Question q) {
        removeFromQuestionToAnswer(q.id);
        removeFromWrongBook(q);
        q.answered_times++;
        q.right_times++;
        q.correct_rate = (q.right_times * 100) / q.answered_times;
        q_rep.update(q);
    }

    public void answered_wrongly(Question q) {
        removeFromQuestionToAnswer(q.id);
        addIntoWrongBook(q);
        q.answered_times++;
        q.wrong_times++;
        q.correct_rate = (q.right_times * 100) / q.answered_times;
        q_rep.update(q);
    }

    private void import_from_txt_file_thread() {
        InputStream is;

        /* questions.txt */
        is = app.getResources().openRawResource(R.raw.questions);
        QuestionParser qp = new QuestionParser(is);
        ArrayList<Question> ql = new ArrayList<Question>();
        Question q;
        while ((q = qp.getQuestion()) != null) {
                //q.log_dump();
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
