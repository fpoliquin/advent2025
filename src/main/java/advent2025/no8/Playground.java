package advent2025.no8;


import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Objects;
import java.util.stream.Collectors;

public class Playground {
    final ArrayList<Circuit> circuits;
    double lastMinDistance = 0D;

    public Playground(String data) {
        circuits = data.lines()
                .map(line -> line.split(","))
                .map(parts -> new JunctionBox(Long.parseLong(parts[0]), Long.parseLong(parts[1]), Long.parseLong(parts[2])))
                .map(Circuit::new)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public Circuit largestCircuit() {
        return circuits.stream().max(Comparator.comparingInt(Circuit::size)).orElseThrow();
    }

    public void connectNextBoxes() {
        double min = Double.MAX_VALUE;
        int minI = -1;
        int minJ = -1;

        for (int i=0; i < circuits.size()-1; ++i) {
            for (int j=i+1; j < circuits.size(); ++j) {
                var distance = circuits.get(i).distanceTo(circuits.get(j));

                if (distance == min) {
                    throw new RuntimeException();
                }

                if (distance < min && distance > lastMinDistance) {
                    min = distance;
                    minI = i;
                    minJ = j;
                }
            }
        }

        boolean closestIsWithinCircuit = false;
        for (var circuit : circuits) {
            var distance = circuit.minDistanceAbove(lastMinDistance);
            if (distance < min) {
                min = distance;
                closestIsWithinCircuit = true;
            }
        }

        lastMinDistance = min;

        if (closestIsWithinCircuit) {
            return;
        }

        circuits.get(minI).mergeWith(circuits.get(minJ));
        circuits.remove(minJ);
    }

    public void connectClosestJunctionBoxes(int numberOfConnexions) {
        for (int i=0; i < numberOfConnexions; ++i) {
            connectNextBoxes();
        }
    }

    public long connectUntilLastTwo() {
        while (circuits.size() > 2) {
            connectNextBoxes();
        }

        return circuits.get(0).multiplyXs(circuits.get(1), lastMinDistance);
    }

    public int circuitCount() {
        return circuits.size();
    }

    public long multiplyLargestCircuits(int count) {
        return circuits.stream()
                .map(Circuit::size)
                .sorted(Comparator.reverseOrder())
                .limit(count)
                .reduce(1, Math::multiplyExact);
    }

    public record JunctionBox(long x, long y, long z) {
        public double computeDistance(JunctionBox b2) {
            long x = Math.abs(this.x - b2.x);
            long y = Math.abs(this.y - b2.y);
            long z = Math.abs(this.z - b2.z);
            return Math.sqrt(x*x + y*y + z*z);
        }
    }

    public static class Circuit {
        ArrayList<JunctionBox> boxes = new ArrayList<>();

        Circuit(JunctionBox b) {
            boxes.add(b);
        }

        int size() {
            return boxes.size();
        }

        public double distanceTo(Circuit circuit) {
            double min = Double.MAX_VALUE;

            for (var b1 : boxes) {
                for (var b2 : circuit.boxes) {
                    var distance = b1.computeDistance(b2);

                    if (distance == min) {
                        throw new RuntimeException();
                    }

                    if (distance < min) {
                        min = distance;
                    }
                }
            }

            return min;
        }

        public void mergeWith(Circuit circuit) {
            boxes.addAll(circuit.boxes);
        }

        public double minDistanceAbove(double above) {
            double min = Double.MAX_VALUE;

            for (int i=0; i < boxes.size()-1; ++i) {
                for (int j=i+1; j < boxes.size(); ++j) {
                    var distance = boxes.get(i).computeDistance(boxes.get(j));

                    if (distance == min) {
                        throw new RuntimeException();
                    }
                    if (distance < min && distance > above) {
                        min = distance;
                    }
                }
            }

            return min;
        }

        public long multiplyXs(Circuit circuit, double distanceAbove) {
            double min = Double.MAX_VALUE;
            JunctionBox minB1 = null;
            JunctionBox minB2 = null;

            for (var b1 : boxes) {
                for (var b2 : circuit.boxes) {
                    var distance = b1.computeDistance(b2);

                    if (distance == min) {
                        throw new RuntimeException();
                    }

                    if (distance < min && distance > distanceAbove) {
                        min = distance;
                        minB1 = b1;
                        minB2 = b2;
                    }
                }
            }

            return minB1.x * minB2.x;
        }
    }

    public static void main(String... args) throws Exception {

        var data = Files.readString(
                Paths.get(Objects.requireNonNull(Playground.class.getResource("/8.txt")).toURI()), StandardCharsets.UTF_8);

        var playground = new Playground(data);

        playground.connectClosestJunctionBoxes(1000);

        var res = playground.multiplyLargestCircuits(3);

        System.out.println(res);
        System.out.println(playground.connectUntilLastTwo());
    }
}
