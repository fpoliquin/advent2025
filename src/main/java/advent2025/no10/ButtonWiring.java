package advent2025.no10;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class ButtonWiring {
    private final Set<Integer> lightIndexes;

    public ButtonWiring(String data) {
        lightIndexes = Arrays.stream(data.substring(1, data.length()-1)
                .split(","))
                .map(Integer::parseInt)
                .collect(Collectors.toSet());
    }

    public ButtonWiring(Set<Integer> indexes) {
        this.lightIndexes = indexes;
    }

    public boolean includes(int lightIndex) {
        return lightIndexes.contains(lightIndex);
    }

    public void toggle(LightDiagram lights) {
        lights.toggle(lightIndexes);
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
}
