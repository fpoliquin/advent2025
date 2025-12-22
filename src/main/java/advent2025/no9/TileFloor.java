package advent2025.no9;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class TileFloor {
    private final List<Point> redPoints;
    private final Set<Point> redOrGreenPoints = new HashSet<>();
    private final Set<Edge> edges = new HashSet<>();
    private final Map<Point, Boolean> insideCache = new HashMap<>();
    private boolean linesArePainted = false;
    private boolean edgesAreDone = false;

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
        return computeLargestRedArea(false);
    }

    public long computeLargestRedAreaWithinRedOrGreenTiles() {
        return computeLargestRedArea(true);
    }

    private long computeLargestRedArea(boolean inside) {
        if (redPoints.size() <= 1) {
            return 1;
        }

        var maxArea = 0L;

        for (int i=0; i < redPoints.size()-1; ++i) {
            for (int j=i+1; j < redPoints.size(); ++j) {
                var area = redPoints.get(i).computeArea(redPoints.get(j));

                if (area > maxArea) {
                    if (inside) {
                        if (rectangleIsAllWithinEdges(redPoints.get(i), redPoints.get(j))) {
                            maxArea = area;
                        }
                    } else {
                        maxArea = area;
                    }
                }
            }
        }

        return maxArea;
    }

    private boolean rectangleIsAllWithinEdges(Point p1, Point p2) {
        var minX = Math.min(p1.x, p2.x);
        var maxX = Math.max(p1.x, p2.x);
        var minY = Math.min(p1.y, p2.y);
        var maxY = Math.max(p1.y, p2.y);

        return horizontalLineIsWithinEdges(minX, maxX, minY)
                && horizontalLineIsWithinEdges(minX, maxX, maxY)
                && verticalLineIsWithinEdges(minX, minY, maxY)
                && verticalLineIsWithinEdges(maxX, minY, maxY);
    }

    private boolean horizontalLineIsWithinEdges(int minX, int maxX, int y) {
        for (int i=minX; i <= maxX; ++i) {
            if (!isRedOrGreen(i, y)) {
                return false;
            }
        }
        return true;
    }

    private boolean verticalLineIsWithinEdges(int x, int minY, int maxY) {
        for (int i=minY; i <= maxY; ++i) {
            if (!isRedOrGreen(x, i)) {
                return false;
            }
        }
        return true;
    }

    public boolean isRedOrGreen(int x, int y) {
        paintLines();
        Point point = new Point(x, y);

        var cached = insideCache.get(point);

        if (cached != null) {
            return cached;
        }

        var isOnEdge = redOrGreenPoints.contains(point);

        if (isOnEdge) {
            return true;
        }

        var res = isWithinEdges(point);

        insideCache.put(point, res);

        return res;
    }

    private boolean isWithinEdges(Point point) {
        computeEdges();

        var count = 0;
        for(Edge edge : edges) {
            if (edge.crossesRay(point)) {
                ++count;
            }
        }

        return isOdd(count);
    }

    private static boolean isOdd(int n) {
        return (n & 1) != 0;
    }

    private void computeEdges() {
        if (edgesAreDone) {
            return;
        }

        Point lastPoint = null;

        for (Point p : redPoints) {
            if (lastPoint != null) {
                edges.add(new Edge(lastPoint, p));
            }

            lastPoint = p;
        }

        edges.add(new Edge(lastPoint, redPoints.getFirst()));

        edgesAreDone = true;
    }

    private void paintLines() {
        if (linesArePainted) {
            return;
        }

        Point lastPoint = null;

        for (var point : redPoints) {
            redOrGreenPoints.add(point);

            if (lastPoint != null) {
                paintLine(point, lastPoint);
            }

            lastPoint = point;
        }

        if (lastPoint != null) {
            paintLine(redPoints.getFirst(), lastPoint);
        }

        linesArePainted = true;
    }

    private void paintLine(Point p1, Point p2) {
        if (p1.x == p2.x) {
            var minY = Math.min(p1.y, p2.y);
            var maxY = Math.max(p1.y, p2.y);

            for (int i=minY+1; i < maxY; ++i) {
                redOrGreenPoints.add(new Point(p1.x, i));
            }
        } else if (p1.y == p2.y) {
            var minX = Math.min(p1.x, p2.x);
            var maxX = Math.max(p1.x, p2.x);

            for (int i=minX+1; i < maxX; ++i) {
                redOrGreenPoints.add(new Point(i, p1.y));
            }
        } else {
            throw new RuntimeException("Points non in line: " + p1 + " " + p2);
        }
    }

    private record Point(int x, int y) {

        public long computeArea(Point point) {
            var width = Math.abs(x - point.x) + 1;
            var height = Math.abs(y - point.y) + 1;
            return (long) width * height;
        }
    }

    private record Edge(Point p1, Point p2) {

        public boolean crossesRay(Point point) {
            if (p1.x != p2.x) {
                return false;
            }

            return point.x < p1.x && isWithinY(point.y);
        }

        private boolean isWithinY(int y) {
            var minY = Math.min(p1.y, p2.y);
            var maxY = Math.max(p1.y, p2.y);

            return y > minY && y <= maxY;
        }
    }

    public static void main(String... args) throws Exception {

        var data = Files.readString(
                Paths.get(Objects.requireNonNull(TileFloor.class.getResource("/9.txt")).toURI()), StandardCharsets.UTF_8);

        var floor = new TileFloor(data);

        var res = floor.computeLargestRedArea();
        System.out.println("Largest area: " + res);

        var res2 = floor.computeLargestRedAreaWithinRedOrGreenTiles();
        System.out.println("Largest area (green or red): " + res2);
    }
}
