package callmepunter.hackerrank;

import java.io.IOException;

public class ClimbLeaderboard {

    static int[] doRanking(int[] scores) {
        int[] ranks = new int[scores.length];
        ranks[0] = 1;
        for (int i = 1; i < scores.length; i++) {
            if (scores[i - 1] == scores[i]) {
                ranks[i] = ranks[i - 1];
            }
            if (scores[i] < scores[i - 1]) {
                ranks[i] = ranks[i - 1] + 1;
            }
        }
        return ranks;
    }


    // Complete the climbingLeaderboard function below.
    static int[] climbingLeaderboard(int[] scores, int[] alice) {
        int n = scores.length;
        int[] ranks = doRanking(scores);
        int[] aliceRanks = new int[alice.length];
        prints("Scores", scores);
        prints("Ranks", ranks);
        prints("alice's scores", alice);

        //for each of alice score
        int aliceCounter = 0;
        while (aliceCounter < alice.length) {
            int score = alice[aliceCounter];
            if (score > scores[0]) {
                aliceRanks[aliceCounter] = 1;
                break;
            }
            if (score < scores[n - 1]) {
                aliceRanks[aliceCounter] = ranks[n - 1] + 1;
            } else {
                int position = bs(scores, score);
                aliceRanks[aliceCounter] = ranks[position];
            }

            aliceCounter++;
        }
        return aliceRanks;
    }

    static public int bs(int[] array, int targetValue) {

        int left = 0;
        int right = array.length - 1;
        int mid;
        while (left <= right) {
            mid = left + (right - left) / 2;
            if (array[mid] == targetValue) {
                return mid;
            } else if (targetValue > array[mid] & targetValue < array[mid - 1]) {
                return mid;
            } else if (targetValue < array[mid] && targetValue >= array[mid + 1]) {
                return mid + 1;
            } else if (targetValue < array[mid]) {
                left = mid + 1;//move left
            } else if (targetValue > array[mid]) {
                right = mid - 1;//move left
            }

        }
        return -1;
    }

    public static void main(String[] args) throws IOException {

        int[] scores = {100, 90, 90, 80, 75, 60};
        int[] alice = {50, 65, 77, 90, 102};

        int[] result = climbingLeaderboard(scores, alice);

        prints("alice's ranks", result);

    }

    static void prints(String message, int[] array) {

        System.out.print(message + "\t");
        for (int i = 0; i < array.length; i++) {
            System.out.print(" " + (String.valueOf(array[i])));

        }
        System.out.println();
    }
}
