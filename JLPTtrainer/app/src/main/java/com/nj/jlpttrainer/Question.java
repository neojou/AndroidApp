package com.nj.jlpttrainer;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import android.content.ContentValues;
import android.provider.BaseColumns;
import android.util.Log;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.ListIterator;

@Entity(tableName = Question.TABLE_NAME)
public class Question {
    private static final String TAG="JLPT_trainer:Question";

    /** The name of the Question table. */
    public static final String TABLE_NAME = "JLPTquestions";

    /** The name of the ID column. */
    public static final String COLUMN_ID = "id";

    /** The name of the question column */
    public static final String COLUMN_QUESTION = "question";

    /** The name of the first choice column */
    public static final String COLUMN_CHOICE1 = "choice1";

    /** The name of the second choice column */
    public static final String COLUMN_CHOICE2 = "choice2";

    /** The name of the third choice column */
    public static final String COLUMN_CHOICE3 = "choice3";

    /** The name of the fourth choice column */
    public static final String COLUMN_CHOICE4 = "choice4";

    /** The right answer number */
    public static final String COLUMN_RIGHT_CHOICE = "right_choice";

    /** The answered times */
    public static final String COLUMN_ANSWERED_TIMES = "answered_times";

    /** The wrong times */
    public static final String COLUMN_WRONG_TIMES = "wrong_times";

    /** The correct times */
    public static final String COLUMN_RIGHT_TIMES = "right_times";

    /** The correct rates */
    public static final String COLUMN_CORRECT_RATE = "correct_rate";

    /** The unique ID of the Question */
    @PrimaryKey
    @ColumnInfo(name = COLUMN_ID)
    public int id;

    /** The description of the question */
    @ColumnInfo(name = COLUMN_QUESTION)
    public String question;

    /** The first choice for the answer */
    @ColumnInfo(name = COLUMN_CHOICE1)
    public String choice1;

    /** The second choice for the answer */
    @ColumnInfo(name = COLUMN_CHOICE2)
    public String choice2;

    /** The third choice for the answer */
    @ColumnInfo(name = COLUMN_CHOICE3)
    public String choice3;

    /** The fourth choice for the answer */
    @ColumnInfo(name = COLUMN_CHOICE4)
    public String choice4;

    /** The right one in choices */
    @ColumnInfo(name = COLUMN_RIGHT_CHOICE)
    int right_choice;

    /** The answered times */
    @ColumnInfo(name = COLUMN_ANSWERED_TIMES)
    int answered_times;

    /** The wrong times */
    @ColumnInfo(name = COLUMN_WRONG_TIMES)
    int wrong_times;

    /** The correct times */
    @ColumnInfo(name = COLUMN_RIGHT_TIMES)
    int right_times;

    /** The correct rate */
    @ColumnInfo(name = COLUMN_CORRECT_RATE)
    int correct_rate;

    /** To sort the questions by correct rate */
    public static class QuestionComparator implements Comparator<Question> {

        // override the compare() method
        public int compare(Question q1, Question q2) {
            if (q1.correct_rate == q2.correct_rate) {
                return 0;
            } else if (q1.correct_rate < q2.correct_rate) {
                return -1;
            }
            return 1;
        }
    }

    public Question() {
        answered_times = 0;
        wrong_times = 0;
        right_times = 0;
        correct_rate = 0;
    }

    public void copy(Question qc) {
        this.id = qc.id;
        this.question = qc.question;
        this.choice1 = qc.choice1;
        this.choice2 = qc.choice2;
        this.choice3 = qc.choice3;
        this.choice4 = qc.choice4;
        this.right_choice = qc.right_choice;
        this.answered_times = qc.answered_times;
        this.wrong_times = qc.wrong_times;
    }

    public static Question fromContentValues(@Nullable ContentValues values) {
        final Question question = new Question();

        if (values == null) return question;

        if (values.containsKey(COLUMN_ID)) {
            question.id = values.getAsInteger(COLUMN_ID);
        }
        if (values.containsKey(COLUMN_QUESTION)) {
            question.question = values.getAsString(COLUMN_QUESTION);
        }
        if (values.containsKey(COLUMN_CHOICE1)) {
            question.choice1 = values.getAsString(COLUMN_CHOICE1);
        }
        if (values.containsKey(COLUMN_CHOICE2)) {
            question.choice2 = values.getAsString(COLUMN_CHOICE2);
        }
        if (values.containsKey(COLUMN_CHOICE3)) {
            question.choice2 = values.getAsString(COLUMN_CHOICE3);
        }
        if (values.containsKey(COLUMN_CHOICE4)) {
            question.choice2 = values.getAsString(COLUMN_CHOICE4);
        }
        if (values.containsKey(COLUMN_RIGHT_CHOICE)) {
            question.id = values.getAsInteger(COLUMN_RIGHT_CHOICE);
        }
        if (values.containsKey(COLUMN_ANSWERED_TIMES)) {
            question.id = values.getAsInteger(COLUMN_ANSWERED_TIMES);
        }
        if (values.containsKey(COLUMN_WRONG_TIMES)) {
            question.id = values.getAsInteger(COLUMN_WRONG_TIMES);
        }
        return question;
    }

    public void log_dump() {
        Log.d(TAG, id + " : " + question);
        Log.d(TAG, "[1]" + choice1 + " [2]" + choice2 + " [3]" + choice3 + " [4]" + choice4);
        Log.d(TAG, "right answer: " + right_choice);
        Log.d(TAG, "answered times: " + answered_times);
        Log.d(TAG, "wrong times: " + wrong_times);
        Log.d(TAG, "right times: " + right_times);
        Log.d(TAG, "correct rate: " + correct_rate);
    }

    public static void log_dump(List<Question> qlist) {
        int sz;

        if (qlist == null) return;
        sz = qlist.size();
        Log.d(TAG, "Question List size =" + sz);
        for (int i = 0; i < sz; i++) {
            Question q = qlist.get(i);
            q.log_dump();
        }
    }

    public static void sortByCorrectRate(List<Question> ql) {
        Collections.sort(ql, new Question.QuestionComparator());
        int lastZeroCorrectPos = 0;
        for (int i = 0; i < ql.size(); i++) {
            Question q = ql.get(i);
            if (q == null) continue;
            if (q.correct_rate != 0) {
                break;
            }
            lastZeroCorrectPos = i;
        }

        Log.d(TAG, "lastZeroCorrectPos=" + lastZeroCorrectPos);
        if (lastZeroCorrectPos > 0)
            Collections.shuffle(ql.subList(0, lastZeroCorrectPos));
    }
};


