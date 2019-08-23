package callmepunter.hackerrank;

import java.util.ArrayList;
import java.util.List;

public class SubStrings {


    /*
     * Complete the countSubstrings function below.
     */
    static int[] countSubstrings(String sample, int[][] queries) {
        /*
         * Write your code here.
         */

        int[] answers = new int[queries.length];
        List<String> solutions = new ArrayList<>();

        for (int qc = 1; qc <= queries.length; qc++) {
            int windowLeft = queries[qc - 1][0];
            int windowRight = queries[qc - 1][1];
            String textWindow = sample.substring(windowLeft, windowRight + 1);
            int windowSize = 1;

            while (windowSize <= textWindow.length()) {
                for (int fl = 0, fr = fl + windowSize; fr <= textWindow.length(); fl++, fr++) {
                    String value = textWindow.substring(fl, fr);
                    addIfDoesntExists(solutions, value);
                }
                windowSize++;
            }
            System.out.println(textWindow);
            System.out.println(solutions);
            answers[qc - 1] = solutions.size();
            solutions.clear();
        }

        return answers;
    }


    public static void addIfDoesntExists(List<String> solutions, String value) {
        if (!value.isEmpty() && !solutions.contains(value)) {
            solutions.add(value);
        }
    }


    public static void main(String[] args) {

        String text = "aabaa";//1, 4 - abaa are a, b, ab, ba, aa, aba, baa, and abaa,
        int[][] queries = new int[2][2];
        queries[0][0] = 1;
        queries[0][1] = 4;
        queries[1][0] = 0;
        queries[1][1] = 2;
        countSubstrings(text, queries);
    }
}
