package advent2025.no10;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class ButtonWiring {
    private final Set<Integer> lightIndexes;
    private final int sum;

    public ButtonWiring(String data) {
        this(Arrays.stream(data.substring(1, data.length()-1)
                .split(","))
                .map(Integer::parseInt)
                .collect(Collectors.toSet()));

    }

    public ButtonWiring(Set<Integer> indexes) {
        this.lightIndexes = indexes;
        sum = lightIndexes.stream().reduce(0, Integer::sum);
    }

    public boolean includes(int lightIndex) {
        return lightIndexes.contains(lightIndex);
    }

    public int sum() {
        return sum;
    }

    public void toggle(LightDiagram lights) {
        lights.toggle(lightIndexes);
    }

    public Joltage press(Joltage currentState) {
        return currentState.press(lightIndexes);
    }

    public Joltage unpress(Joltage currentState) {
        return currentState.unpress(lightIndexes);
    }

    @Override
    public String toString() {
        return this.lightIndexes.toString();
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (other instanceof ButtonWiring b) {
            return lightIndexes.equals(b.lightIndexes);
        }

        return false;
    }

    public boolean pressesIndex(int index) {
        return lightIndexes.contains(index);
    }
}
