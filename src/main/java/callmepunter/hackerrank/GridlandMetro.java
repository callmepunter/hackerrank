package callmepunter.hackerrank;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigInteger;
import java.net.URL;
import java.util.*;

public class GridlandMetro {

    // Complete the gridlandMetro function below.
    static int gridlandMetro(int n, int m, int k, int[][] track) {
        Network network = new Network(n, m);
        for (int row = 0; row < track.length; row++) {
            network.addLine(BigInteger.valueOf(track[row][0]), BigInteger.valueOf(track[row][1]), BigInteger.valueOf(track[row][2]));
        }

        return network.computeAvailable().intValue();
    }


    public static class Network {
        Map<BigInteger, Track> grid = new HashMap();

        BigInteger total;
        BigInteger used = BigInteger.ZERO;

        public Network(int rows, int cols) {
            total = BigInteger.valueOf(rows).multiply(BigInteger.valueOf(cols));
        }

        public void addLine(BigInteger rowNum, BigInteger origin, BigInteger end) {
            if (grid.containsKey(rowNum)) {
                grid.get(rowNum).addLine(origin, end);
            }else {
                Track newTrack = new Track();
                newTrack.addLine(origin, end);
                grid.put(rowNum, newTrack);
            }

        }

        public BigInteger computeAvailable() {
            grid.values().stream().forEach(track -> {
                used = used.add(track.computeUsage());
            });
            return total.subtract(used);
        }


    }

    public static class Track {
        private List<Line> lines = new ArrayList();

        public void addLine(BigInteger origin, BigInteger end) {
            lines.add(new Line(origin, end));
        }

        public BigInteger computeUsage() {
            boolean first = true;
            BigInteger total = BigInteger.ZERO;
            Line computed = new Line("0", "0");

            for (Line line : lines) {
                if (first) {
                    computed = line;
                    first = false;
                    continue;
                }
                if (line.intersects(computed)) {
                    line.union(computed);
                    continue;
                }
                total = total.add(computed.size());
                computed = line;
            }
            return total.add(computed.size());
        }
    }

    public static class Line {
        BigInteger origin;
        BigInteger end;

        public Line(BigInteger origin, BigInteger end) {
            this.origin = origin;
            this.end = end;
        }

        public Line(String origin, String end) {
            this.origin = new BigInteger(origin);
            this.end = new BigInteger(end);
        }

        public Line union(Line other) {
            if (other == null) {
                return this;
            }
            origin = this.origin.min(other.origin);
            end = this.end.max(other.end);
            return this;
        }

        public boolean intersects(Line other) {
            return intersectsLeft(other) || intersectsRight(other);
        }


        public boolean intersectsCompletely(Line other) {
            return this.isBetween(other.origin) && this.isBetween(other.end);
        }

        public boolean intersectsLeft(Line other) {
            return other.isBetween(this.origin);
        }

        public boolean intersectsRight(Line other) {
            return other.isBetween(this.end);
        }

        public boolean isBetween(BigInteger median) {
            int left = this.origin.compareTo(median);
            int right = this.end.compareTo(median);
            return left <= 0 && right >= 0;
        }

        public BigInteger size() {
            return this.end.subtract(this.origin).add(BigInteger.ONE);
        }
    }


    public static void main(String[] args) {
        input();

    }


    public static void input() {

        URL testCase = GridlandMetro.class.getClassLoader().getResource("gridland/input/input02.txt");
        Scanner scanner =
                null;
        try {
            scanner = new Scanner(new File(testCase.getFile()));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        String[] nmk = scanner.nextLine().split(" ");

        int n = Integer.parseInt(nmk[0]);

        int m = Integer.parseInt(nmk[1]);

        int k = Integer.parseInt(nmk[2]);

        int[][] track = new int[k][3];

        for (int i = 0; i < k; i++) {
            String[] trackRowItems = scanner.nextLine().split(" ");
            scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

            for (int j = 0; j < 3; j++) {
                int trackItem = Integer.parseInt(trackRowItems[j]);
                track[i][j] = trackItem;
            }
        }
        prints("Tracks", track);

        int result = gridlandMetro(n, m, k, track);
        System.out.println(result);

        scanner.close();
    }

    static void prints(String message, int[][] matrix) {

        System.out.println(message + "\r\n");
        for (int row = 0; row < matrix.length; row++) {
            for (int col = 0; col < matrix[row].length; col++) {
                System.out.print(" " + matrix[row][col]);
            }
            System.out.println();
        }
    }
}
