package com.nj.jlpttrainer;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class QuestionToAnswer {
    public Set<Integer> qids = new LinkedHashSet<Integer>();

    public void insertAll(List<Integer> ids) {
        for (Integer id : ids)
            qids.add(id);
    }

    public boolean contains(int id) {
        return qids.contains(id);
    }

    public boolean remove(int id) {
        return qids.remove(id);
    }

    public int size() {
        return qids.size();
    }

    public int get(int idx) {
        int size = qids.size();
        if (size <= 0) return 0;
        if (idx >= size) return 0;

        //Creating an empty integer array
        Integer[] array = new Integer[size];
        //Converting Set object to integer array
        qids.toArray(array);
        return array[idx];
    }
}
