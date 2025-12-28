package advent2025.no10;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

public class JoltageTest {
    @ParameterizedTest
    @CsvSource({
            "'{0,1,2,3}', 4",
            "'{2}', 1",
            "'{1,2,3}', 3"
    })
    public void ShouldHaveGoodSize(String data, int expected) {
        var lights = new Joltage(data);

        var res = lights.size();

        assertThat(res).isEqualTo(expected);
    }

    @Test
    public void ShouldHaveGoodStatus() {
        var joltage = new Joltage("{1,2,3,4}");

        assertThat(joltage.value(0)).isEqualTo(1);
        assertThat(joltage.value(1)).isEqualTo(2);
        assertThat(joltage.value(2)).isEqualTo(3);
        assertThat(joltage.value(3)).isEqualTo(4);
    }

    @Test
    public void ShouldIncrementWhenPressed() {
        var joltage = new Joltage("{1,2,3,4}");

        var res = joltage.press(List.of(1, 2, 3));

        assertThat(res.value(1)).isEqualTo(3);
        assertThat(res.value(2)).isEqualTo(4);
        assertThat(res.value(3)).isEqualTo(5);
    }

    @Test
    public void ShouldBeAbleToReset() {
        var joltage = new Joltage("{1,2,3,4}");

        var res = joltage.reset();

        assertThat(res.value(0)).isEqualTo(0);
        assertThat(res.value(3)).isEqualTo(0);
    }
}
