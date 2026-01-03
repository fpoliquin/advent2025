package advent2025.no11;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static org.assertj.core.api.Assertions.assertThat;

public class DeviceTest {
    @ParameterizedTest
    @CsvSource({
            "My Device: any, My Device",
            "Bob: any, Bob"
    })
    public void ShouldParseDeviceName(String data, String expected) {
        var device = new Device(data);

        var res = device.name();

        assertThat(res).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({
            "Any: o1, 1",
            "Any: o1 o2, 2"
    })
    public void ShouldParseAllOutputs(String data, int count) {
        var device = new Device(data);

        var res = device.outputCount();

        assertThat(res).isEqualTo(count);
    }

    @Test
    public void ShouldParseOutputName() {
        var device = new Device("Any: o1");

        var res = device.output(0);

        assertThat(res).isEqualTo("o1");
    }

    @Test
    public void ShouldParseOutputName2() {
        var device = new Device("Any: o1 o2");

        var res = device.output(1);

        assertThat(res).isEqualTo("o2");
    }

    @ParameterizedTest
    @CsvSource({
            "Any: out, true",
            "Any: aaa, false"
    })
    public void ShouldIndicateIsItLeadsToOut(String data, boolean leadsToOut) {
        var device = new Device(data);

        var res = device.leadsToOut();

        assertThat(res).isEqualTo(leadsToOut);
    }

    @ParameterizedTest
    @CsvSource({
            "Any: o1, Any: o1, true",
            "Any: o1, Any: o2, false",
            "Any: o1, Any2: o1, false",
            "Any: o1, Any: o1 o2, false"
    })
    public void ShouldCompareEquality(String data1, String data2, boolean equals) {
        var device1 = new Device(data1);
        var device2 = new Device(data2);

        var res = device1.equals(device2);

        assertThat(res).isEqualTo(equals);
    }
}
