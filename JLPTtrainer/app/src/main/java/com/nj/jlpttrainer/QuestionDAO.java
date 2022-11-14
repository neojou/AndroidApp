package com.nj.jlpttrainer;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import android.database.Cursor;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;


@Dao
public interface QuestionDAO {
    /**
     * Counts the number of questions in the table.
     *
     * @return The number of questions
     */
    @Query("SELECT COUNT(*) FROM " + Question.TABLE_NAME)
    int count();

    /**
     * Inserts a question into the table.
     *
     * @param question A new question.
     */
    @Insert
    void insert(Question question);

    /**
     * Inserts multiple cheeses into the database
     *
     * @param questions An array of the new questions
     * @return The row IDs of the newly inserted questions
     */
    @Insert
    long[] insertAll(Question[] questions);

    /**
     * Select all questions.
     *
     * @return A List of all the questions in the table.
     */
    @Query("SELECT * FROM " + Question.TABLE_NAME)
    List<Question> selectAll();

    /**
     * Select a question by the ID.
     *
     * @param id The row ID.
     * @return the List of the selected questions.
     */
    @Query("SELECT * FROM " + Question.TABLE_NAME + " WHERE " + Question.COLUMN_ID + " = :id")
    List<Question> selectById(int id);

    /**
     * Select a question by the ID.
     *
     * @param id The row ID.
     * @return the List of the selected questions.
     */
    @Query("SELECT * FROM " + Question.TABLE_NAME + " WHERE " + Question.COLUMN_QUESTION + " like :q")
    List<Question> selectByQuestion(String q);

    /**
     * Delete a question by the ID
     *
     * @param id The row ID
     * @return A number of questions deleted. This should always be {@code 1}.
     */
    @Query("DELETE FROM " + Question.TABLE_NAME + " WHERE " + Question.COLUMN_ID + " = :id")
    int deleteById(long id);

    /**
     * Update the question
     *
     * @param question The question to update.
     * @return A number of questions updated. This should always be {@code 1}.
     */
    @Update
    int update(Question question);
}
