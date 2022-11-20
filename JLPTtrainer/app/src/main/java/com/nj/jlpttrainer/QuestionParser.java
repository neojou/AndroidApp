package com.nj.jlpttrainer;

import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class QuestionParser {
    private static final String TAG="JLPT_trainer:QuestionParser";

    InputStream is;
    InputStreamReader is_reader = null;
    BufferedReader br = null;


    public QuestionParser(InputStream is) {
        this.is = is;
        try {
            is_reader = new InputStreamReader(is, StandardCharsets.UTF_8);
            br = new BufferedReader(is_reader);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Question getQuestion() {
        final Question q = new Question();

        try {
            final String line1 = br.readLine();
            final Pattern p1 = Pattern.compile("(^\\d+) (.*)");
            final Matcher m1 = p1.matcher(line1);
            if (m1.matches()) {
                String str = m1.group(1);
                if (str != null)
                    q.id = Integer.parseInt(str);
                str = m1.group(2);
                if (str != null)
                    q.question = m1.group(2);
            } else {
                Log.v(TAG, "line1 :" + line1 + "not match");
                return null;
            }

            final String line2 = br.readLine();
            final Pattern p2 = Pattern.compile("1\\S(\\S+) 2\\S(\\S+) 3\\S(\\S+) 4\\S(\\S+)(.*)");
            Matcher m2 = p2.matcher(line2);
            if (m2.matches()) {
                String str = m2.group(1);
                if (str != null)
                    q.choice1 = str;
                str = m2.group(2);
                if (str != null)
                    q.choice2 = str;
                str = m2.group(3);
                if (str != null)
                    q.choice3 = str;
                str = m2.group(4);
                if (str != null)
                    q.choice4 = str;
            } else {
                Log.v(TAG, "line1 : " + line1);
                Log.v(TAG, "line2 :" + line2 + "not match");
                return null;
            }

            final String line3 = br.readLine();
            final Pattern p3 = Pattern.compile("答案:(\\d+)(.*)");
            Matcher m3 = p3.matcher(line3);
            if (m3.matches()) {
                String str = m3.group(1);
                if (str != null)
                    q.right_choice = Integer.parseInt(str);
            } else {
                Log.v(TAG, "line1 : " + line1);
                Log.v(TAG, "line2 : " + line2);
                Log.v(TAG, "line3 :" + line3 + "not match");
                return null;
            }
            q.log_dump();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return q;
    }

    public void close() {
        try {
            if (br != null)
                br.close();
            if (is_reader != null)
                is_reader.close();
            if (is != null)
                is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
