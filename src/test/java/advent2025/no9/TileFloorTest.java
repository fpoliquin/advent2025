package advent2025.no9;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static org.assertj.core.api.Assertions.assertThat;

public class TileFloorTest {
    private static final String SAMPLE = """
            7,1
            11,1
            11,7
            9,7
            9,5
            2,5
            2,3
            7,3
            """;

    @ParameterizedTest
    @CsvSource({
            "0, 0, false",
            "1, 0, false",
            "2, 0, true"})
    public void ShouldParseRedTiles(int x, int y, boolean expected) {
        var floor = new TileFloor("2,0");

        var res = floor.isRed(x, y);

        assertThat(res).isEqualTo(expected);
    }

    @Test
    public void AreaShouldBeZero_Given1Point() {
        var floor = new TileFloor("1,0");

        var res = floor.computeLargestRedArea();

        assertThat(res).isEqualTo(1);
    }

    @Test
    public void ShouldComputeAreaBetween2ConsecutivePoints() {
        var floor = new TileFloor("0,0\n1,0");

        var res = floor.computeLargestRedArea();

        assertThat(res).isEqualTo(2);
    }

    @Test
    public void ShouldComputeAreaBetween2DistancedPoints() {
        var floor = new TileFloor("0,0\n10,12");

        var res = floor.computeLargestRedArea();

        assertThat(res).isEqualTo(143);
    }

    @Test
    public void ShouldFindTheLargestArea() {
        var floor = new TileFloor("0,0\n2,3\n10,12");

        var res = floor.computeLargestRedArea();

        assertThat(res).isEqualTo(143);
    }

    @Test
    public void ShouldComputeTheSampleArea() {
        var floor = new TileFloor(SAMPLE);

        var res = floor.computeLargestRedArea();

        assertThat(res).isEqualTo(50);
    }
}
