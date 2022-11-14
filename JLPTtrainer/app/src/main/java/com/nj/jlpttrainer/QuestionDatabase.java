package com.nj.jlpttrainer;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import android.content.Context;
import androidx.annotation.VisibleForTesting;
import java.util.concurrent.*;

/**
 * The Room database.
 */
@Database(entities = {Question.class}, version = 1)
public abstract class QuestionDatabase extends RoomDatabase {

    /**
     * @return the DAO for the Question table.
     */
    @SuppressWarnings("WeakerAccess")
    public abstract QuestionDAO questionDao();

    /** The only instance */
    private static volatile QuestionDatabase sInstance;

    /**
     * Gets the singleton instance of QuestionDatabase
     *
     * @param context The context.
     * @return The singleton instance of QuestionDatabase.
     */
    public static synchronized QuestionDatabase getInstance(final Context context) {
        if (sInstance == null) {
            sInstance = Room
                    .databaseBuilder(context.getApplicationContext(), QuestionDatabase.class, "question_db")
                    .fallbackToDestructiveMigration()
                    .build();
        }

        return sInstance;
    }

    /**
     * Switches the internal implementation with an empty in-memory database.
     *
     * @param context The context
     */
    @VisibleForTesting
    public static void switchToInMemory(Context context) {
        sInstance = Room.inMemoryDatabaseBuilder(context.getApplicationContext(),
                QuestionDatabase.class).build();
    }
}
