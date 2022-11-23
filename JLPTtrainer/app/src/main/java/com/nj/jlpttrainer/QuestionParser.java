package com.nj.jlpttrainer;

import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class QuestionParser extends JParser {
    private static final String TAG="JLPT_trainer:QuestionParser";

    InputStream is;
    InputStreamReader is_reader = null;
    BufferedReader br = null;


    public QuestionParser(InputStream is) {
        super(is);
    }

    protected Matcher findOutTheFirstLine() {
        final Pattern p1 = Pattern.compile("(^\\d+) (.*)");
        Matcher m1;
        String line1;

        try {
            while ((line1 = readLine()) != null) {
                m1 = p1.matcher(line1);
                if (m1.matches())
                    return m1;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Question getQuestion() {
        final Question q = new Question();

        try {
            Matcher m1;
            String str;
            m1 = findOutTheFirstLine();
            if (m1 != null) {
                str = m1.group(1);
                if (str != null)
                    q.id = Integer.parseInt(str.trim());
                str = m1.group(2);
                if (str != null)
                    q.question = str.trim();
            }

            final String line2 = br.readLine();
            if (line2 == null) return null;
            final Pattern p2 = Pattern.compile("1\\S(.+) 2\\S(.+) 3\\S(.+) 4\\S(.+)(.*)");
            Matcher m2 = p2.matcher(line2);
            if (m2.matches()) {
                str = m2.group(1);
                if (str != null)
                    q.choice1 = str.trim();
                str = m2.group(2);
                if (str != null)
                    q.choice2 = str.trim();
                str = m2.group(3);
                if (str != null)
                    q.choice3 = str.trim();
                str = m2.group(4);
                if (str != null)
                    q.choice4 = str.trim();
            } else {
                Log.e(TAG, "ID:" + q.id);
                Log.e(TAG, "Question:" + q.question);
                Log.e(TAG, "line2 :" + line2 + "not match");
                return null;
            }

            final String line3 = br.readLine();
            if (line3 == null) return null;
            final Pattern p3 = Pattern.compile("答案:(\\d+)(.*)");
            Matcher m3 = p3.matcher(line3);
            if (m3.matches()) {
                str = m3.group(1);
                if (str != null)
                    q.right_choice = Integer.parseInt(str.trim());
            } else {
                Log.e(TAG, "ID:" + q.id);
                Log.e(TAG, "Question:" + q.question);
                Log.e(TAG, "line2 : " + line2);
                Log.e(TAG, "line3 :" + line3 + "not match");
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return q;
    }

}
