package callmepunter.hackerrank;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigInteger;
import java.net.URL;
import java.util.*;

public class GridlandMetro {

    // Complete the gridland Metro function below.
    static BigInteger gridlandMetro(int n, int m, int k, int[][] track) {
        Network network = new Network(n, m);
        for (int row = 0; row < k; row++) {
            network.addLine(BigInteger.valueOf(track[row][0]), BigInteger.valueOf(track[row][1]), BigInteger.valueOf(track[row][2]));
        }

        return network.computeAvailable();
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
            } else {
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


    }//end of network

    public static class Track {
        private List<Line> lines = new ArrayList();

        public void addLine(BigInteger origin, BigInteger end) {
            lines.add(new Line(origin, end));
        }

        public BigInteger computeUsage() {
            boolean first = true;
            BigInteger total = BigInteger.ZERO;

            Collections.sort(lines, (line1, line2) -> line1.getOrigin().compareTo(line2.getOrigin()) > 0 ? 1 : -1);
            Stack<Line> groupMaker = new Stack<>();
            for (Line line : lines) {
                if (first) {
                    groupMaker.push(line);
                    first = false;
                    continue;
                }
                if (groupMaker.peek().intersects(line)) {
                    groupMaker.push(groupMaker.pop().union(line));
                } else {
                    groupMaker.push(line);
                }
            }
            while (!groupMaker.isEmpty()) {
                total = total.add(groupMaker.pop().size());
            }
            return total;
        }
    }

    public static class Line {
        BigInteger origin;
        BigInteger end;


        @Override
        public String toString() {
            return "Line{" +
                    "origin=" + origin +
                    ", end=" + end +
                    '}';
        }

        public BigInteger getOrigin() {
            return origin;
        }

        public BigInteger getEnd() {
            return end;
        }

        public Line(BigInteger origin, BigInteger end) {
            this.origin = origin;
            this.end = end;
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

        public boolean intersectsLeft(Line other) {
            return isBetween(other.end);
        }

        public boolean intersectsRight(Line other) {
            return isBetween(other.origin);
        }

        public boolean isBetween(BigInteger pointer) {
            int left = this.origin.compareTo(pointer);
            int right = this.end.compareTo(pointer);
            return left <= 0 && right >= 0;
        }

        public BigInteger size() {
            return this.end.subtract(this.origin).add(BigInteger.ONE);
        }
    }


    public static void main(String[] args) {
        int i = 511560154;
        System.out.println(i);
        input();
    }


    public static void input() {
        //case 10 expected 411903339261164011
        URL testCase = GridlandMetro.class.getClassLoader().getResource("gridland/input/input10.txt");
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

        BigInteger result = gridlandMetro(n, m, k, track);
        System.out.println("-----------------------");
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
