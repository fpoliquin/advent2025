package advent2025.no5;

import advent2025.no1.Dial;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class IngredientsDb {
    private final ArrayList<Range> ranges = new ArrayList<>();
    private final ArrayList<Long> ingredients = new ArrayList<>();

    public IngredientsDb(String data) {
        boolean[] readingRanges = new boolean[] { true };

        data.lines().forEach(line -> {
            if (line.isBlank()) {
                readingRanges[0] = false;
                return;
            }

            if (readingRanges[0]) {
                var parts = line.split("-");
                ranges.add(new Range(Long.parseLong(parts[0]), Long.parseLong(parts[1])));
            } else {
                ingredients.add(Long.parseLong(line));
            }
        });
    }

    public int rangeCount() {
        return ranges.size();
    }

    public int ingredientCount() {
        return ingredients.size();
    }

    public long howManyAvailableIngredientsAreFresh() {
        return ingredients.stream()
                .filter(this::isFresh)
                .count();
    }

    private boolean isFresh(long ingredientId) {
        return ranges.stream()
                .anyMatch(r -> r.includes(ingredientId));
    }

    public long howManyIngredientsAreConsideredFresh() {
        var mergedRanges = Range.mergeRanges(ranges);
        System.out.println(mergedRanges);
        return mergedRanges.stream()
                .map(Range::idCount)
                .reduce(0L, Long::sum);
    }

    private record Range(long start, long end) {
        public boolean includes(long ingredientId) {
            return ingredientId >= start && ingredientId <= end;
        }

        public boolean overlaps(Range other) {
            return end >= other.start && other.end >= start;
        }

        public static ArrayList<Range> mergeRanges(ArrayList<Range> _ranges) {
            var ranges = new ArrayList<>(_ranges);

            ranges.sort(Comparator.comparingLong(r -> r.start));

            for(int i=1; i < ranges.size(); ++i) {
                var first = ranges.get(i-1);
                var second = ranges.get(i);
                if (first.overlaps(second)) {
                    ranges.set(i-1, new Range(Math.min(first.start, second.start), Math.max(first.end, second.end)));
                    ranges.remove(i);
                    --i;
                }
            }

            return ranges;
        }

        public long idCount() {
            return end - start + 1;
        }
    }

    public static void main(String... args) throws Exception {

        var databaseFile = Files.readString(
                Paths.get(Objects.requireNonNull(Dial.class.getResource("/5.txt")).toURI()), StandardCharsets.UTF_8);

        var db = new IngredientsDb(databaseFile);

        var availableFreshIngredients = db.howManyAvailableIngredientsAreFresh();
        var possibleFreshIngredients = db.howManyIngredientsAreConsideredFresh();

        System.out.println(availableFreshIngredients + " ingredients are available and fresh.");
        System.out.println(possibleFreshIngredients + " ingredients are considered fresh.");
    }
}
