package advent2025.no1;

import static org.assertj.core.api.Assertions.assertThat;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class DialTest {

    @Test
    public void ShouldStartAt50() {
        var dial = new Dial();

        var res = dial.value();

        assertThat(res).isEqualTo(50);
    }

    @Test
    public void ShouldPointTo19_GivenAStartPositionOf11_And_R8() {
        var dial = new Dial(11);

        var res = dial.rotate("R8");

        assertThat(res).isEqualTo(19);
    }

    @ParameterizedTest
    @CsvSource({
            "0, L1, 99",
            "99, R1, 0",
            "5, L10, 95",
            "95, R5, 0",
            "99, R200, 99",
            "0, R1000, 0",
            "0, L1000, 0",
            "0, L999, 1"
    })
    public void ShouldMoveDialCorrectly(int startValue, String vector, int expectedResult) {
        var dial = new Dial(startValue);

        var res = dial.rotate(vector);

        assertThat(res).isEqualTo(expectedResult);
    }

    @Test
    public void ShouldCountNumberOfTimesReturningTo0() {
        var rotations = Lists.list("L68", "L30", "R48", "L5", "R60", "L55", "L1", "L99", "R14", "L82");
        var dial = new Dial();

        dial.rotate(rotations);

        assertThat(dial.timesTo0()).isEqualTo(3);
    }
}
