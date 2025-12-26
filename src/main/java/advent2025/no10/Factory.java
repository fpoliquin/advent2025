package advent2025.no10;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;

public class Factory {
    private final List<Machine> machines;

    public Factory(String data) {
        machines = data.lines().map(Machine::new).toList();
    }

    public int size() {
        return machines.size();
    }

    public int findTotalFewestPresses() {
        return machines.stream().map(Machine::findFewestTotalPresses).reduce(0, Integer::sum);
    }

    public static void main(String... args) throws Exception {

        var data = Files.readString(
                Paths.get(Objects.requireNonNull(Factory.class.getResource("/10.txt")).toURI()), StandardCharsets.UTF_8);

        var factory = new Factory(data);

        var res = factory.findTotalFewestPresses();

        System.out.println("Res: " + res);
    }
}
