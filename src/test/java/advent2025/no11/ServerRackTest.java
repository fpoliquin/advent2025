package advent2025.no11;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static org.assertj.core.api.Assertions.assertThat;

public class ServerRackTest {
    private static final String SAMPLE = """
            aaa: you hhh
            you: bbb ccc
            bbb: ddd eee
            ccc: ddd eee fff
            ddd: ggg
            eee: out
            fff: out
            ggg: out
            hhh: ccc fff iii
            iii: out
            """;

    private static final String SAMPLE_PART2 = """
            svr: aaa bbb
            aaa: fft
            fft: ccc
            bbb: tty
            tty: ccc
            ccc: ddd eee
            ddd: hub
            hub: fff
            eee: dac
            dac: fff
            fff: ggg hhh
            ggg: out
            hhh: out
            """;

    @ParameterizedTest
    @CsvSource({
            "D1: D2 D3",
            "you: out",
    })
    public void ShouldParseDevice(String data) {
        var rack = new ServerRack(data);

        var res = rack.device(0);

        assertThat(res).isEqualTo(new Device(data));
    }

    @Test
    public void ShouldFindDirectPath() {
        var rack = new ServerRack("you: out");

        var res = rack.findHowManyPathToOut();

        assertThat(res).isEqualTo(1);
    }

    @Test
    public void ShouldFollowPath() {
        var rack = new ServerRack("you: d1\nd1: out");

        var res = rack.findHowManyPathToOut();

        assertThat(res).isEqualTo(1);
    }

    @Test
    public void ShouldFollowTwoPaths() {
        var rack = new ServerRack("you: d1 d2\nd1: out\nd2: out");

        var res = rack.findHowManyPathToOut();

        assertThat(res).isEqualTo(2);
    }

    @Test
    public void ShouldStartWithYou() {
        var rack = new ServerRack("bad: doesn't_exist\nyou: out");

        var res = rack.findHowManyPathToOut();

        assertThat(res).isEqualTo(1);
    }

    @Test
    public void ShouldWorkWithSampleData() {
        var rack = new ServerRack(SAMPLE);

        var res = rack.findHowManyPathToOut();

        assertThat(res).isEqualTo(5);
    }

    @Test
    public void Part2ShouldStartWithSvr() {
        var rack = new ServerRack("bad: doesn't_exist\nsvr: dac\ndac: fft\nfft: out");

        var res = rack.findHowManyPathFromSvrToDacAndFftAndOut();

        assertThat(res).isEqualTo(1);
    }

    @ParameterizedTest
    @CsvSource({
            "'svr: dac\ndac: fft\nfft: out', 1",
            "'svr: fft\nfft: dac\ndac: out', 1",
            "'svr: out', 0"
    })
    public void Part2ShouldPassByDacAndFft(String data, int expected) {
        var rack = new ServerRack(data);

        var res = rack.findHowManyPathFromSvrToDacAndFftAndOut();

        assertThat(res).isEqualTo(expected);
    }

    @Test
    public void Part2ShouldWorkWithSampleData() {
        var rack = new ServerRack(SAMPLE_PART2);

        var res = rack.findHowManyPathFromSvrToDacAndFftAndOut();

        assertThat(res).isEqualTo(2);
    }
}
