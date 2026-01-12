package advent2025.no12;

import java.util.ArrayList;
import java.util.List;

public class PresentShape {
    private final String data;
    private final List<Point> points = new ArrayList<>();

    public PresentShape(String data) {
        this.data = data;
        var lines = data.split("\n\r?");

        for (var currentHeight=0; currentHeight < lines.length; currentHeight++) {
            var line = lines[currentHeight];

            for(var currentWidth=0; currentWidth < line.length(); currentWidth++) {
                if (line.charAt(currentWidth) == '#') {
                    points.add(new Point(currentWidth, currentHeight));
                }
            }
        }
    }

    public int size() {
        return points.size();
    }

    public boolean isPartOfTheShape(int width, int height) {
        return points.contains(new Point(width, height));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (other instanceof PresentShape s) {
            return this.points.equals(s.points);
        }

        return false;
    }

    @Override
    public int hashCode() {
        return points.hashCode();
    }

    @Override
    public String toString() {
        return data;
    }

    public boolean fitsIn(int width, int height) {
        for (var p : points) {
            if (p.x >= width) {
                return false;
            }

            if (p.y >= height) {
                return false;
            }
        }

        return true;
    }

    private record Point(int x, int y) {

    }
}
