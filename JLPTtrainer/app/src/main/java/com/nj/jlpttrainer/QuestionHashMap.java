package com.nj.jlpttrainer;

import java.util.HashMap;
import java.util.List;

public class QuestionHashMap {

    HashMap<Integer, Question> map;

    public QuestionHashMap() {
        map = new HashMap<Integer, Question>();
    }

    public void insertAll(List<Question> ql) {
        for (Question q: ql) {
            if (q == null)
                continue;
            map.put(new Integer(q.id), q);
        }
    }

    public Question findById(int id) {
        return map.get(id);
    }

    public int size() {
        return map.size();
    }
}
