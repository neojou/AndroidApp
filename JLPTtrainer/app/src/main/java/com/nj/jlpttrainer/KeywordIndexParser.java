package com.nj.jlpttrainer;

import java.io.InputStream;
import java.util.ArrayList;

public class KeywordIndexParser extends JParser {
    private static final String TAG = "JLPT_trainer:KeywordIndexParser";

    public KeywordIndexParser(InputStream is) {
        super(is);
    }

    public KeywordIndex getKeywordIndex() {
        final KeywordIndex ki = new KeywordIndex();

        String str;
        str = readLine();
        if (str == null) return null;
        ki.keyword = str;

        str = readLine();
        if (str == null) return null;
        String[] str_seperated = str.split(" ");
        int size = str_seperated.length;
        if (size <= 0) return null;

        ki.indexArray = new int[size];
        for (int i = 0; i < str_seperated.length; i++) {
            try {
                ki.indexArray[i] = Integer.parseInt(str_seperated[i]);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }

        return ki;
    }
}
