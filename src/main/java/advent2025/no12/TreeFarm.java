package advent2025.no12;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TreeFarm {
    private final List<? extends PresentShape> shapes;
    private final List<? extends Region> regions;

    public TreeFarm(String data) {
        var blocs = data.split("\n\r?\n\r?");

        var shapes = new ArrayList<PresentShape>();
        List<Region> regions = null;

        for (String bloc : blocs) {
            if (bloc.contains("x")) {
                regions = bloc.lines().map(Region::new).toList();
            } else {
                shapes.add(new PresentShape(bloc.replaceFirst("\\d+:\\n\\r?", "")));
            }
        }

        this.regions = regions;
        this.shapes = shapes;
    }

    public List<? extends PresentShape> shapes() {
        return shapes;
    }

    public long howManyRegionCanFit() {
        return regions.stream().filter(r -> r.canFitShapes(shapes)).count();
    }

    public static void main(String... args) throws Exception {
        var data = Files.readString(
                Paths.get(Objects.requireNonNull(TreeFarm.class.getResource("/12.txt")).toURI()), StandardCharsets.UTF_8);

        var farm = new TreeFarm(data);

        var res = farm.howManyRegionCanFit();

        System.out.println("Count: " + res);
    }
}
