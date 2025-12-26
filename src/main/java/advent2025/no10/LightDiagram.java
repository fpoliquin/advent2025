package advent2025.no10;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

public class LightDiagram implements Cloneable {
    private final ArrayList<Boolean> lights;

    public LightDiagram(String data) {
        lights = data
                .substring(1, data.length()-1)
                .chars()
                .mapToObj(c -> c == '#')
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public LightDiagram(Collection<Boolean> lights) {
        this.lights = new ArrayList<>(lights);
    }

    public int size() {
        return lights.size();
    }

    public boolean isOpen(int index) {
        return lights.get(index);
    }

    public void toggle(Iterable<Integer> indexes) {
        for (int index : indexes) {
            lights.set(index, !lights.get(index));
        }
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (other instanceof LightDiagram l) {
            return lights.equals(l.lights);
        }

        return false;
    }

    @Override
    public LightDiagram clone() {
        return new LightDiagram(lights);
    }

    public void closeAll() {
        Collections.fill(lights, false);
    }
}
