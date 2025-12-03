package advent2025.no1;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Objects;

public class Dial {
    public static final int DIAL_RANGE = 100;
    public static final String LEFT_SYMBOL = "L";
    private int timesTo0 = 0;
    private int currentValue;

    public Dial() {
        this(50);
    }

    public Dial(int startPosition) {
        currentValue = startPosition;
    }

    public int value() {
        return currentValue;
    }

    public int rotate(String vector) {
        var direction = vector.substring(0, 1);
        var rawStrength = Integer.parseInt(vector.substring(1));
        var strength = rawStrength % DIAL_RANGE;
        var nbOfFullCircle = Math.abs(rawStrength / DIAL_RANGE);

        var newValue = LEFT_SYMBOL.equals(direction) ? currentValue - strength : currentValue + strength;

        if (newValue < 0) {
            newValue = DIAL_RANGE + newValue;

            if (currentValue > 0) {
                ++timesTo0;
            }
        } else if (newValue >= DIAL_RANGE) {
            newValue -= DIAL_RANGE;

            if (currentValue > 0) {
                ++timesTo0;
            }
        } else if (newValue == 0) {
            ++timesTo0;
        }

        timesTo0 += nbOfFullCircle;

        currentValue = newValue;

        return currentValue;
    }

    public void rotate(Collection<? extends String> rotations) {
        rotations.forEach(this::rotate);
    }

    public int timesTo0() {
        return timesTo0;
    }

    public static void main(String... args) throws Exception {

        var vectors = Files.readAllLines(
                Paths.get(Objects.requireNonNull(Dial.class.getResource("/1.1.txt")).toURI()), StandardCharsets.UTF_8);

        var dial = new Dial();

        dial.rotate(vectors);

        System.out.println(vectors.size() + " rotations.");
        System.out.println(dial.timesTo0 + " times to 0");

    }
}
