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

    @ParameterizedTest
    @CsvSource({
            "0,0,true",
            "5,0,true",
            "10,0,true",
            "11,0,false",
            "10,4,true",
            "10,8,true",
            "10,9,false",
            "0,8,true",
            "0,4,true",
            "0,9,false"
    })
    public void ShouldFindPointOnRedAndGreenLines(int x, int y, boolean expected) {
        var floor = new TileFloor("0,0\n10,0\n10,8\n0,8");

        var res = floor.isRedOrGreen(x, y);

        assertThat(res).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({
            "1,1,true",
            "9,1,true",
            "9,7,true",
            "1,7,true",
            "0,11,false",
            "10,9,false",
            "0,9,false"
    })
    public void ShouldFindPointInsideRedAndGreenLines(int x, int y, boolean expected) {
        var floor = new TileFloor("0,0\n10,0\n10,8\n0,8");

        var res = floor.isRedOrGreen(x, y);

        assertThat(res).isEqualTo(expected);
    }

    @Test
    public void ShouldComputeLargestAreaWithinRedOrGreenTilesWithoutObstacle() {
        var floor = new TileFloor("0,0\n10,0\n10,8\n0,8");

        var res = floor.computeLargestRedAreaWithinRedOrGreenTiles();

        assertThat(res).isEqualTo(99);
    }

    @Test
    public void ShouldComputeLargestAreaWithinRedOrGreenTiles() {
        var floor = new TileFloor(SAMPLE);

        var res = floor.computeLargestRedAreaWithinRedOrGreenTiles();

        assertThat(res).isEqualTo(24);
    }
}
