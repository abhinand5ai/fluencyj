package com.abhinand.lc;

import java.util.ArrayList;
import java.util.List;

public class ValidWordSquare {
    public boolean validWordSquare(List<String> words) {
        ArrayList<String> wordsArray = new ArrayList<>(words);
        for (int i = 0; i < wordsArray.size(); i++) {
            String row = wordsArray.get(i);
            StringBuilder sb = new StringBuilder();
            for (String wrd : wordsArray) {
                if (i < wrd.length()) {
                    sb.append(wrd.charAt(i));
                }
            }
            if (!sb.toString().equals(row)) {
                return false;
            }
        }
        return true;
    }
}
