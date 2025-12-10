package advent2025.no7;

import advent2025.no1.Dial;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Objects;

public class TachyonManifold {
    private final char[][] manifold;
    private final HashSet<Object> activeSplitters = new HashSet<>();

    public TachyonManifold(String manifold) {
        this.manifold = manifold.lines()
                .map(String::toCharArray)
                .toArray(char[][]::new);
    }

    public int countNumberOfSplits() {
        activeSplitters.clear();

        for (int i=0; i < manifold.length-1; ++i) {
            var line = manifold[i];

            for (int j=0; j < line.length; ++j) {
                if (line[j] == '^' || line[j] == '>') {
                    addRay(i+1, j-1);
                    addRay(i+1, j+1);
                }
                if (line[j] == 'S' || line[j] == '|') {
                    addRay(i+1, j);
                }
            }
        }

        return activeSplitters.size();
    }

    private void addRay(int i, int j) {
        if (manifold[i][j] == '^') {
            manifold[i][j] = '>';
            activeSplitters.add(i + "-" + j);
        } else {
            manifold[i][j] = '|';
        }
    }

    public static void main(String... args) throws Exception {

        var data = Files.readString(
                Paths.get(Objects.requireNonNull(TachyonManifold.class.getResource("/7.txt")).toURI()), StandardCharsets.UTF_8);

        var manifold = new TachyonManifold(data);

        var res = manifold.countNumberOfSplits();

        System.out.println(res + " active splitters");

    }
}
