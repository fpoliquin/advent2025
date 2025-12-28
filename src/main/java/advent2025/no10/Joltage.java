package advent2025.no10;

import java.util.*;
import java.util.stream.Collectors;

public class Joltage {
    private final ArrayList<? extends Integer> values;

    public Joltage(ArrayList<Integer> values) {
        this.values = values;
    }

    public Joltage(String data) {
        this((ArrayList<Integer>)Arrays.stream(data.substring(1, data.length()-1)
                .split(","))
                .map(Integer::parseInt)
                .collect(Collectors.toCollection(ArrayList::new)));
    }

    public int size() {
        return values.size();
    }

    public int value(int index) {
        return values.get(index);
    }

    public Joltage press(Collection<? extends Integer> indexes) {
        var newState = new ArrayList<Integer>(values);

        for(var index : indexes) {
            newState.set(index, values.get(index)+1);
        }
        return new Joltage(newState);
    }

    public Joltage unpress(Collection<? extends Integer> indexes) {
        var newState = new ArrayList<Integer>(values);

        for(var index : indexes) {
            newState.set(index, values.get(index)-1);
        }
        return new Joltage(newState);
    }

    public Joltage reset() {
        var newVals = new ArrayList<Integer>(values);
        Collections.fill(newVals, 0);
        return new Joltage(newVals);
    }

    @Override
    public String toString() {
        return values.toString();
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (other instanceof Joltage j) {
            return values.equals(j.values);
        }

        return false;
    }

    @Override
    public int hashCode() {
        return values.hashCode();
    }

    public boolean isAbove(Joltage other) {
        for (int i=0; i < values.size(); ++i) {
            if (values.get(i) > other.values.get(i)) {
                return true;
            }
        }
        return false;
    }

    public boolean hasOneValueToOne() {
        return values.stream().anyMatch(v -> v == 1);
    }

    public int getFirstIndexToOne() {
        for (int i=0; i < values.size(); ++i) {
            if (values.get(i) == 1) {
                return i;
            }
        }

        throw new NoSuchElementException();
    }
}
