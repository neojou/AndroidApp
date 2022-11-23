package com.nj.jlpttrainer;

import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;

public class JParser {
    private static final String TAG="JLPT_trainer:JParser";

    protected InputStream is;
    protected InputStreamReader is_reader = null;
    protected BufferedReader br = null;

    public JParser(InputStream is) {
        this.is = is;
        try {
            is_reader = new InputStreamReader(is, StandardCharsets.UTF_8);
            br = new BufferedReader(is_reader);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String readLine() {
        String line = null;

        try {
            while ((line = br.readLine()) != null) {
                Log.v(TAG, "line="+line);
                line = line.trim();
                if (line != "") break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return line;
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
