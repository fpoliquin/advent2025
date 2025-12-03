package advent2025.no2;

import advent2025.no1.Dial;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class ProductIdValidator {
    public boolean isValid(long number) {
        return !hasSequenceOfDigitsRepeatedAtLeastTwice(number);
    }

    private boolean hasSequenceOfDigitsRepeatedAtLeastTwice(long number) {
        var chars = Long.toString(number);
        var charCount = chars.length();
        var longestPossiblePattern = charCount / 2;

        for (var lengthOfPattern=1; lengthOfPattern <= longestPossiblePattern; ++lengthOfPattern) {
            if (hasRepeatingPattern(chars, chars.substring(0, lengthOfPattern))) {
                return true;
            }
        }

        return false;
    }

    private boolean hasRepeatingPattern(String chars, String pattern) {
        int lengthOfPattern = pattern.length();

        if (chars.length() % lengthOfPattern != 0) {
            return false;
        }

        var repetitionCount = chars.length() / lengthOfPattern;

        for (int i=0; i < repetitionCount; ++i) {
            var nextChars = chars.substring(i*lengthOfPattern, i*lengthOfPattern + lengthOfPattern);

            if (!nextChars.equals(pattern)) {
                return false;
            }
        }

        return true;
    }

    public List<Long> findInvalidNumbers(String multiRange) {
        var ranges = multiRange.split(",");
        return Arrays.stream(ranges)
                .flatMap(range -> findInvalidNumbersInSingleRange(range).stream())
                .toList();
    }

    private ArrayList<Long> findInvalidNumbersInSingleRange(String range) {
        var parts = range.split("-");
        var start = Long.parseLong(parts[0]);
        var end = Long.parseLong(parts[1]);

        var invalids = new ArrayList<Long>();

        for (long i=start; i <= end; ++i) {
            if (!isValid(i)) {
                invalids.add(i);
            }
        }

        return invalids;
    }

    public long findInvalidNumbersSum(String multiRange) {
        return findInvalidNumbers(multiRange).stream().reduce(0L, Long::sum);
    }

    public static void main(String... args) throws Exception {
        var ranges = Files.readString(
                Paths.get(Objects.requireNonNull(Dial.class.getResource("/2.1.txt")).toURI()), StandardCharsets.UTF_8);

        var validator = new ProductIdValidator();

        var sum = validator.findInvalidNumbersSum(ranges);

        System.out.println("Sum is : " + sum);
    }
}
