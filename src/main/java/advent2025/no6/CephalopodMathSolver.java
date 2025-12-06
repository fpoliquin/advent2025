package advent2025.no6;

import advent2025.no1.Dial;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class CephalopodMathSolver {
    private final ArrayList<Problem> problems = new ArrayList<>();

    public CephalopodMathSolver(String problems) {
        problems.lines().forEach(this::addData);
    }

    private void addData(String line) {
        var parts = Arrays.stream(line.split(" +"))
                .filter(p -> !p.isBlank())
                .toList();

        for (int i=0; i < parts.size(); ++i) {
            if (problems.size() <= i) {
                problems.add(new Problem());
            }

            var problem = problems.get(i);

            try {
                problem.data.add(Long.parseLong(parts.get(i)));
            } catch(NumberFormatException e) {
                problem.operator = parts.get(i);
            }
        }
    }

    public long computeGrandTotal() {
        return problems.stream().map(Problem::compute).reduce(0L, Long::sum);
    }

    private static class Problem {
        String operator;
        ArrayList<Long> data = new ArrayList<>();

        long compute() {
            if (operator.trim().equals("*")) {
                return data.stream().reduce(1L, Math::multiplyExact);
            }

            return data.stream().reduce(0L, Long::sum);
        }
    }

    public static void main(String... args) throws Exception {

        var problems = Files.readString(
                Paths.get(Objects.requireNonNull(Dial.class.getResource("/6.txt")).toURI()), StandardCharsets.UTF_8);

        var solver = new CephalopodMathSolver(problems);

        var res = solver.computeGrandTotal();

        System.out.println("Grand total is: " + res);
    }
}
