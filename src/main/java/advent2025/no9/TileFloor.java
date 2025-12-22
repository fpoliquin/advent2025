package advent2025.no9;

import advent2025.no8.Playground;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;

public class TileFloor {
    private final List<Point> redPoints;

    public TileFloor(String data) {
        redPoints = data.lines()
                .map(line -> line.split(","))
                .map(parts -> new Point(Integer.parseInt(parts[0]), Integer.parseInt(parts[1])))
                .toList();
    }

    public boolean isRed(int x, int y) {
        return redPoints.contains(new Point(x, y));
    }

    public long computeLargestRedArea() {
        if (redPoints.size() <= 1) {
            return 1;
        }

        var maxArea = 0L;

        for (int i=0; i < redPoints.size()-1; ++i) {
            for (int j=i+1; j < redPoints.size(); ++j) {
                var area = redPoints.get(i).computeArea(redPoints.get(j));

                if (area > maxArea) {
                    maxArea = area;
                }
            }
        }

        return maxArea;
    }

    private record Point(int x, int y) {

        public long computeArea(Point point) {
            var width = Math.abs(x - point.x) + 1;
            var height = Math.abs(y - point.y) + 1;
            return (long) width * height;
        }
    }

    public static void main(String... args) throws Exception {

        var data = Files.readString(
                Paths.get(Objects.requireNonNull(TileFloor.class.getResource("/9.txt")).toURI()), StandardCharsets.UTF_8);

        var floor = new TileFloor(data);

        var res = floor.computeLargestRedArea();

        System.out.println("Largest area: " + res);
    }
}
