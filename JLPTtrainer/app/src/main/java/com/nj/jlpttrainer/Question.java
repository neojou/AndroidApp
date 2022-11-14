package com.nj.jlpttrainer;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import android.content.ContentValues;
import android.provider.BaseColumns;
import android.util.Log;
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

    public Question() {
    }

    public void copy(Question qc) {
        this.id = qc.id;
        this.question = qc.question;
        this.choice1 = qc.choice1;
        this.choice2 = qc.choice2;
        this.choice3 = qc.choice3;
        this.choice4 = qc.choice4;
        this.right_choice = qc.right_choice;
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
        return question;
    }

    public void log_dump() {
        Log.d(TAG, Integer.toString(id) + " : " + question);
        Log.d(TAG, "[1] " + choice1 + "[2] " + choice2 + "[3] " + choice3 + "[4] " + choice4);
        Log.d(TAG, "right answer: " + right_choice);
    }

    public static void log_dump(List<Question> qlist) {
        int sz;

        if (qlist == null) return;
        sz = qlist.size();
        Log.d(TAG, "Question List size =" + Integer.toString(sz));
        for (int i = 0; i < sz; i++) {
            Question q = qlist.get(i);
            q.log_dump();
        }
    }

    public void set_to_q1() {
        id = 1;
        question = "都心（　　　　）、このあたりはまだ緑が多く残っていて鳥の声も聞こえる。";
        choice1 = "とあって";
        choice2 = "とはいえ";
        choice3 = "ときたら";
        choice4 = "とあれば";
        right_choice = 2;
    }

    public void set_to_q2() {
        id = 2;
        question = "子供では（　　　　　）、自分のことぐらい自分でしなさい";
        choice1 = "あるまいし";
        choice2 = "あれ";
        choice3 = "あろうが";
        choice4 = "なくて";
        right_choice = 1;
    }


    public void set_to_q3() {
        id = 3;
        question = "家庭は子供が大部分の時間を過ごす場所である。（　　　　　）、私は家庭でのしつけが一番重要だとかんがえるのです。";
        choice1 = "それなりに";
        choice2 = "それゆえに";
        choice3 = "それこそが";
        choice4 = "それにひきかえ";
        right_choice = 2;
    }


    public void set_to_q4() {
        id = 4;
        question = "彼は貧しさ（　　　　）十分な教育が受けられなかった。";
        choice1 = "ゆえに";
        choice2 = "なりに";
        choice3 = "ながから";
        choice4 = "なしに";
        right_choice = 1;
    }

    public void set_to_q5() {
        id = 5;
        question = "さんざん考えた末、私（　　　　）作った企画がこれです。";
        choice1 = "だけに";
        choice2 = "ゆえに";
        choice3 = "ように";
        choice4 = "なりに";
        right_choice = 4;
    }

    public void set_to_q6() {
        id = 6;
        question = "国民の期待というプレッシャーの大きさは（　　　　　　　　）が、彼女はそれを見事に克服して、金メダルを獲得した。";
        choice1 = "想像にかたくない";
        choice2 = "想像できな";
        choice3 = "想像できることはできる";
        choice4 = "想像することはない";
        right_choice = 1;
    }

    public void generate_question() {
        set_to_q6();
    }

};


