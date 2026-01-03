package advent2025.no11;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

public class ServerRack {
    private final List<? extends Device> devices;

    public ServerRack(String data) {
        devices = data.lines().map(Device::new).toList();
    }

    public Device device(int index) {
        return devices.get(index);
    }

    public int findHowManyPathToOut() {
        var youDevice = find("you");

        return findHowManyPathToOut(youDevice, false, false).count();
    }

    public int findHowManyPathFromSvrToDacAndFftAndOut() {
        var device = find("svr");
        return findHowManyPathToOut(device, false, false).countByDacAndFft();
    }

    private PathData findHowManyPathToOut(Device device, boolean passesByDac, boolean passesByFft) {
        if (device.isDac()) {
            passesByDac = true;
        }

        if (device.isFft()) {
            passesByFft = true;
        }

        if (device.leadsToOut()) {
            device.pathCount = 1;
            return new PathData(1, passesByDac && passesByFft ? 1 : 0);
        }

        if (passesByDac && passesByFft && device.pathCount != null) {
            return new PathData(device.pathCount, device.pathCount);
        }

        int sum = 0;
        int sum2 = 0;
        for(var output : device.outputs()) {
            PathData pathData = findHowManyPathToOut(find(output), passesByDac, passesByFft);
            sum += pathData.count();
            sum2 += pathData.countByDacAndFft();
        }

        device.pathCount = sum;
        return new PathData(sum, sum2);
    }

    private Device find(String name) {
        for(Device d : devices) {
            if (d.name().equals(name)) {
                return d;
            }
        }
        throw new NoSuchElementException(name);
    }

    public static void main(String... args) throws Exception {
        var data = Files.readString(
                Paths.get(Objects.requireNonNull(ServerRack.class.getResource("/11.txt")).toURI()), StandardCharsets.UTF_8);

        var rack = new ServerRack(data);

        System.out.println("Count: " + rack.findHowManyPathToOut());
        System.out.println("Count 2: " + rack.findHowManyPathFromSvrToDacAndFftAndOut());
    }
}
