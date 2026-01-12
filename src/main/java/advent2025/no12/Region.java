package advent2025.no12;

import java.util.Arrays;
import java.util.List;

public class Region {
    private final int width;
    private final int height;
    private final List<? extends Integer> quantities;

    public Region(String data) {
        var dimensions = data.substring(0, data.indexOf(':'));

        var parts = dimensions.split("x");

        width = Integer.parseInt(parts[0]);
        height = Integer.parseInt(parts[1]);

        quantities = Arrays.stream(data.substring(data.indexOf(':')+1)
                .trim()
                .split("\\s+"))
                .map(Integer::parseInt)
                .toList();
    }

    public int width() {
        return width;
    }

    public int height() {
        return height;
    }

    public int quantity(int index) {
        return quantities.get(index);
    }

    public boolean canFitShapes(List<? extends PresentShape> shapes) {
        var sum = 0;
        for (int i=0; i < quantities.size(); ++i) {
            sum += quantities.get(i) * shapes.get(i).size();
        }

        return sum <= height*width;
    }
}
