package advent2025.no4;

import advent2025.no1.Dial;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class PaperRollGrid {
    private final ArrayList<ArrayList<Boolean>> grid;

    public PaperRollGrid(String grid) {
        this.grid = Arrays.stream(grid.split("\r?\n"))
                .map(line -> line.chars().mapToObj(c -> c == '@').collect(Collectors.toCollection(ArrayList::new)))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public int countRollsForkliftCanAccess() {
        var count = new int[] { 0 };

        forEachRollsForkliftCanAccess(p -> count[0]++);

        return count[0];
    }

    public int removeAllRollsForkliftCanAccess() {
        var count = 0;
        var positions = new ArrayList<Position>();

        do {
            positions.clear();
            forEachRollsForkliftCanAccess(positions::add);

            if (positions.isEmpty()) {
                return count;
            }

            count += positions.size();

            positions.forEach(p -> grid.get(p.x).set(p.y, false));
        } while (true);
    }

    private void forEachRollsForkliftCanAccess(Consumer<Position> consumer) {
        for (int x=0; x < grid.size(); ++x) {
            var row = grid.get(x);
            for (int y=0; y < row.size(); ++y) {
                if (isRollWithLessThan4AdjacentRolls(x, y)) {
                    consumer.accept(new Position(x, y));
                }
            }
        }
    }

    private boolean isRollWithLessThan4AdjacentRolls(int x, int y) {
        var possibilities = new Position[] {
            new Position(x, y-1),
            new Position(x-1, y-1),
            new Position(x-1, y),
            new Position(x-1, y+1),
            new Position(x, y+1),
            new Position(x+1, y+1),
            new Position(x+1, y),
            new Position(x+1, y-1),
        };

        if (!hasRoll(new Position(x, y))) {
            return false;
        }

        var count = Arrays.stream(possibilities)
                .filter(this::hasRoll)
                .count();

        return count < 4;
    }

    private boolean hasRoll(Position p) {
        if (isOutOfBounds(p)) {
            return false;
        }

        return grid.get(p.x).get(p.y);
    }

    private boolean isOutOfBounds(Position p) {
        return p.x < 0
                || p.x >= grid.size()
                || p.y < 0
                || p.y >= grid.getFirst().size();
    }

    private record Position(int x, int y){

    }

    public static void main(String... args) throws Exception {
        var rawGrid = Files.readString(
                Paths.get(Objects.requireNonNull(Dial.class.getResource("/4.txt")).toURI()), StandardCharsets.UTF_8);

        var grid = new PaperRollGrid(rawGrid);

        System.out.println("Count: " + grid.countRollsForkliftCanAccess());

        System.out.println("Removed: " + grid.removeAllRollsForkliftCanAccess());
    }
}
