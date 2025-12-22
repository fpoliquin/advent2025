package advent2025.no7;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class TachyonManifold {
    private final Cell[][] manifold;

    public TachyonManifold(String manifold) {
        this.manifold = manifold.lines()
                .map(line -> line.chars().boxed().map(c -> new Cell((char)(int)c)).toArray(Cell[]::new))
                .toArray(Cell[][]::new);
    }

    public int countNumberOfSplits() {
        walkManifold();

        int activeSpliters = 0;
        for (Cell[] cells : manifold) {
            for (Cell cell : cells) {
                if (cell.isActiveSplitter()) {
                    ++activeSpliters;
                }
            }
        }

        return activeSpliters;
    }

    public long countNumberOfTimelines() {
        walkManifold();

        long timelines = 0;
        var lastLine = manifold[manifold.length-1];
        for (Cell cell : lastLine) {
            timelines += cell.count;
        }

        return timelines;
    }

    private void walkManifold() {
        resetManifold();

        for (int i=0; i < manifold.length-1; ++i) {
            var currentLine = manifold[i];
            var nextLine = manifold[i+1];

            for (int j=0; j < currentLine.length; ++j) {
                if (currentLine[j].isActiveSplitter()) {
                    nextLine[j-1].incrementCount(currentLine[j].count);
                    nextLine[j+1].incrementCount(currentLine[j].count);
                } else if (currentLine[j].isActive()) {
                    nextLine[j].incrementCount(currentLine[j].count);
                }
            }
        }
    }

    public String toString() {
        var buffer = new StringBuilder();

        for (var line : manifold) {
            for (var cell : line) {
                buffer.append(cell.count);
                buffer.append(' ');
            }
            buffer.append('\n');
        }

        return buffer.toString();
    }

    private void resetManifold() {
        for (Cell[] cells : manifold) {
            for (Cell cell : cells) {
                cell.reset();
            }
        }
    }

    private static class Cell {

        private final char c;
        private long count;
        Cell(char c) {
            this.c = c;
            this.count = c == 'S' ? 1 : 0;
        }

        boolean isSplitter() {
            return c == '^';
        }

        boolean isActiveSplitter() {
            return isActive() && isSplitter();
        }

        boolean isStart() {
            return c == 'S';
        }

        boolean isActive() {
            return count > 0;
        }

        void incrementCount(long inc) {
            count += inc <= 0 ? 1 : inc;
        }

        public void reset() {
            count = isStart() ? 1 : 0;
        }

    }

    public static void main(String... args) throws Exception {

        var data = Files.readString(
                Paths.get(Objects.requireNonNull(TachyonManifold.class.getResource("/7.txt")).toURI()), StandardCharsets.UTF_8);

        var manifold = new TachyonManifold(data);

        var splitCount = manifold.countNumberOfSplits();
        var timelines = manifold.countNumberOfTimelines();

        System.out.println(splitCount + " active splitters");
        System.out.println(timelines + " timelines");

    }
}
