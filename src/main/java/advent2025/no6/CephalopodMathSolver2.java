package advent2025.no6;

import advent2025.no1.Dial;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CephalopodMathSolver2 {
    private final ArrayList<Problem> problems = new ArrayList<>();

    public CephalopodMathSolver2(String problems) {
        var grid = Grid.parse(problems);
        var columns = grid.columns();

        Problem problem = new Problem();

        for (ArrayList<Integer> column : columns) {
            var builder = new StringBuilder();

            for (Integer val : column) {
                if (val >= '0' && val <= '9') {
                    builder.appendCodePoint(val);
                }

                if (val == '*' || val == '+') {
                    problem = new Problem();
                    problem.operator = val;
                    this.problems.add(problem);
                }
            }

            var number = builder.toString();

            if (!number.isBlank()) {
                problem.data.add(Long.parseLong(builder.toString()));
            }
        }
    }

    public long computeGrandTotalRightToLeft() {
        return problems.stream()
                .map(Problem::compute)
                .reduce(0L, Long::sum);
    }

    private static class Problem {
        int operator;
        ArrayList<Long> data = new ArrayList<>();

        long compute() {
            long res;
            if (operator == '*') {
                res = data.stream().reduce(1L, Math::multiplyExact);
            } else {
                res = data.stream().reduce(0L, Long::sum);
            }

            return res;
        }
    }

    record Grid(List<List<Integer>> lines) {
        static Grid parse(String data) {
            return new Grid(data.lines().map(String::chars).map(chars -> chars.boxed().toList()).toList());
        }

        ArrayList<ArrayList<Integer>> columns() {
            ArrayList<ArrayList<Integer>> cols = new ArrayList<>();

            for (List<Integer> line : lines) {
                for (int j = 0; j < line.size(); ++j) {
                    if (cols.size() <= j) {
                        cols.add(new ArrayList<>());
                    }

                    var column = cols.get(j);
                    column.add(line.get(j));
                }
            }
            return cols;
        }
    }

    public static void main(String... args) throws Exception {

        var problems = Files.readString(
                Paths.get(Objects.requireNonNull(Dial.class.getResource("/6.txt")).toURI()), StandardCharsets.UTF_8);

        var solver = new CephalopodMathSolver2(problems);

        var res = solver.computeGrandTotalRightToLeft();

        System.out.println("Grand total is: " + res);
    }
}
