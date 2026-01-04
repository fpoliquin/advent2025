package advent2025.no11;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.stream.Collectors;

public class ServerRack {
    private final Map<String, ? extends Device> devices;

    public ServerRack(String data) {
        devices = data.lines().map(Device::new).collect(Collectors.toMap(Device::name, d -> d));
    }

    public long findHowManyPathToOut() {
        var youDevice = find("you");

        return findHowManyPathToOut(youDevice, false, false, 1).count();
    }

    public long findHowManyPathFromSvrToDacAndFftAndOut() {
        var device = find("svr");
        return findHowManyPathToOut(device, false, false, 1).confirmedCount();
    }

    private PathData findHowManyPathToOut(Device device, boolean passesByDac, boolean passesByFft, int depth) {
        if (device.pathCount != null) {
            return new PathData(device.pathCount, device.partialPathCount, device.confirmedPathCount);
        }

        if (device.isDac()) {
            System.out.println("DAC");
            passesByDac = true;
        }

        if (device.isFft()) {
            System.out.println("FFT");
            passesByFft = true;
        }

        if (device.leadsToOut()) {
            if (passesByDac && passesByFft && (device.isFft() || device.isDac())) {
                device.pathCount = 1L;
                device.partialPathCount = 1L;
                device.confirmedPathCount = 0L;
                return device.toData();
            }

            device.pathCount = 1L;
            device.partialPathCount = 0L;
            device.confirmedPathCount = 0L;
            return device.toData();
        }

        long sum = 0;
        long sum2 = 0;
        long sum3 = 0;
        for(var output : device.outputs()) {
            var child = find(output);
            PathData pathData = findHowManyPathToOut(child, passesByDac, passesByFft, depth+1);
            sum += pathData.count();
            sum2 += pathData.partialCount();
            sum3 += pathData.confirmedCount();
        }

        if (device.isFft() || device.isDac()) {
            device.pathCount = sum;

            if (sum2 > 0) {
                System.out.println("Confirming:" + device.name() + ": " + sum2);
                device.partialPathCount = sum2;
                device.confirmedPathCount = sum2;
            } else {
                System.out.println("Starting:" + device.name() + ": " + sum);
                device.partialPathCount = sum;
                device.confirmedPathCount = 0;
            }
        } else {
            device.pathCount = sum;
            device.partialPathCount = sum2;
            device.confirmedPathCount = sum3;
        }

        return device.toData();
    }

    private Device find(String name) {
        var d = devices.get(name);

        if (d == null) {
            throw new NoSuchElementException(name);
        }

        return d;
    }

    public static void main(String... args) throws Exception {
        var data = Files.readString(
                Paths.get(Objects.requireNonNull(ServerRack.class.getResource("/11.txt")).toURI()), StandardCharsets.UTF_8);

        var rack = new ServerRack(data);

        System.out.println("Count: " + rack.findHowManyPathToOut());
        System.out.println("From dac to fft: " + rack.findHowManyPathToOut(rack.find("dac"), false, false, 1));
        System.out.println("From fft to dac: " + rack.findHowManyPathToOut(rack.find("fft"), false, false, 1));
        System.out.println("Count 2: " + rack.findHowManyPathToOut(rack.find("svr"), false, false, 1));
    }
}
