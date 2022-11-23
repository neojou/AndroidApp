package com.nj.jlpttrainer;

import android.util.Log;

public class KeywordIndex {
    private static final String TAG="JLPT_trainer:KeywordIndex";

    public String keyword;
    public int[] indexArray;

    public void log_dump() {
        int size = 0;

        if (keyword == null) {
            Log.d(TAG,"keyword is null");
            return;
        }
        if (indexArray == null) {
            Log.d(TAG, "indexArray is null");
            return;
        }

        size = indexArray.length;
        Log.d(TAG, keyword + "[" + Integer.toString(size) + "]");
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < size - 1; i++) {
            sb.append(Integer.toString(indexArray[i]));
            sb.append(" : ");
        }
        sb.append(Integer.toString(indexArray[size - 1]));
        Log.d(TAG, sb.toString());
    }

}

