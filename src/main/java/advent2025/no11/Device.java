package advent2025.no11;

import java.util.Arrays;
import java.util.List;

public class Device {
    private final String name;
    private final List<? extends String> outputs;
    public Integer pathCount;

    public Device(String data) {
        name = data.substring(0, data.indexOf(':'));
        outputs = Arrays.stream(data.substring(data.indexOf(':')+1).trim().split("\\s+")).toList();
    }

    public String name() {
        return name;
    }

    public int outputCount() {
        return outputs.size();
    }

    public String output(int index) {
        return outputs.get(index);
    }

    public boolean leadsToOut() {
        return outputs.contains("out");
    }

    public boolean isDac() {
        return name.equals("dac");
    }

    public boolean isFft() {
        return name.equals("fft");
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }

        if (object instanceof Device d) {
            return name.equals(d.name) && outputs.equals(d.outputs);
        }

        return false;
    }

    public List<? extends String> outputs() {
        return outputs;
    }
}
