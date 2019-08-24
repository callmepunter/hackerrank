package callmepunter.hackerrank;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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


    public static void main(String[] args) throws IOException {
        final String testcase = "testcase-10.txt";

        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(new File("testcase10-out.txt")));
        InputStream inputStream = SubStrings.class.getClassLoader().getResourceAsStream(testcase);
        Scanner scanner = new Scanner(inputStream);

        String[] nq = scanner.nextLine().split(" ");

        int n = Integer.parseInt(nq[0].trim());

        int q = Integer.parseInt(nq[1].trim());

        String s = scanner.nextLine();

        int[][] queries = new int[q][2];

        for (int queriesRowItr = 0; queriesRowItr < q; queriesRowItr++) {
            String[] queriesRowItems = scanner.nextLine().split(" ");

            for (int queriesColumnItr = 0; queriesColumnItr < 2; queriesColumnItr++) {
                int queriesItem = Integer.parseInt(queriesRowItems[queriesColumnItr].trim());
                queries[queriesRowItr][queriesColumnItr] = queriesItem;
            }
        }

        int[] result = countSubstrings(s, queries);

        for (int resultItr = 0; resultItr < result.length; resultItr++) {
            bufferedWriter.write(String.valueOf(result[resultItr]));

            if (resultItr != result.length - 1) {
                bufferedWriter.write("\n");
            }
        }

        bufferedWriter.newLine();

        bufferedWriter.close();
        String text = "aabaa";//1, 4 - abaa are a, b, ab, ba, aa, aba, baa, and abaa,

        //countSubstrings(text, queries);
    }
}
