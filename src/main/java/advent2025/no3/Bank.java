package advent2025.no3;

import advent2025.no1.Dial;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Objects;

public class Bank {
    private final String digits;

    public Bank(String string) {
        this.digits = string;
    }

    public long findLargestPossibleJoltage() {
        var lines = digits.split("\r?\n");

        return Arrays.stream(lines)
                .map(this::findLargestPossibleJoltageInLine)
                .reduce(0L, Long::sum);
    }

    private long findLargestPossibleJoltageInLine(String line) {
        var builder = new StringBuilder();
        var nextIndex = 0;

        for (int i=0; i < 12; ++i) {
            var biggestDigitIndex = findLargestDigit(line, nextIndex, line.length()-(11-i));
            nextIndex = biggestDigitIndex + 1;
            builder.append(line.charAt(biggestDigitIndex));
        }

        return Long.parseLong(builder.toString());
    }

    private int findLargestDigit(String line, int startIndex, int endIndex) {
        int largestIndex = -1;
        char largest = 0;

        var possibilities = line.substring(startIndex, endIndex);

        for (int i=0; i < possibilities.length(); ++i) {
            char current = possibilities.charAt(i);

            if (current > largest) {
                largest = current;
                largestIndex = i;
            }
        }

        return largestIndex + startIndex;
    }

    public static void main(String... args) throws Exception {
        var lines = Files.readString(
                Paths.get(Objects.requireNonNull(Dial.class.getResource("/3.1.txt")).toURI()), StandardCharsets.UTF_8);

        var bank = new Bank(lines);

        System.out.println("Sum: " + bank.findLargestPossibleJoltage());
    }
}
