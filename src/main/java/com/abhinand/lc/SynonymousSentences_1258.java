package com.abhinand.lc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.BooleanSupplier;
import java.util.function.Function;

public class SynonymousSentences_1258 {
    HashMap<String, String> parent = new HashMap<>();

    public List<String> generateSentences(List<List<String>> synonyms, String text) {
        HashMap<String, String> graph = new HashMap<>();
        for (List<String> synonym : synonyms) {
            ArrayList<String> aB = new ArrayList<>(synonym);

        }


        return null;
    }

    public boolean union(String a, String b) {
        a = find(a);
        b = find(b);
        if (a.equals(b)) {
            return false;
        }
        parent.put(a, b);
        return false;
    }

    public String find(String a) {
        if (!parent.containsKey(a)) {
            parent.put(a, a);
        }
        String p = parent.get(a);
        if (p.equals(a)) {
            return p;
        }
        p = find(p);
        parent.put(a, p);
        return p;
    }
}
