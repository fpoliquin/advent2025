package advent2025.no10;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

public class LightDiagramTest {
    @ParameterizedTest
    @CsvSource({
            "[....], 4",
            "[.], 1",
            "[...], 3"
    })
    public void ShouldHaveGoodSize_GivenNoOpenLights(String data, int expected) {
        var lights = new LightDiagram(data);

        var res = lights.size();

        assertThat(res).isEqualTo(expected);
    }

    @Test
    public void ShouldGoodStatus_GivenNoOpenLights() {
        var lights = new LightDiagram("[....]");

        assertThat(lights.isOpen(0)).isFalse();
        assertThat(lights.isOpen(1)).isFalse();
        assertThat(lights.isOpen(2)).isFalse();
        assertThat(lights.isOpen(3)).isFalse();
    }

    @Test
    public void ShouldGoodStatus_GivenSomeOpenLights() {
        var lights = new LightDiagram("[.#.#]");

        assertThat(lights.isOpen(0)).isFalse();
        assertThat(lights.isOpen(1)).isTrue();
        assertThat(lights.isOpen(2)).isFalse();
        assertThat(lights.isOpen(3)).isTrue();
    }

    @Test
    public void ShouldOpenAllIdentifiedClosedLight_WhenToggle() {
        var lights = new LightDiagram("[....]");

        lights.toggle(List.of(1, 2, 3));

        assertThat(lights.isOpen(1)).isTrue();
        assertThat(lights.isOpen(2)).isTrue();
        assertThat(lights.isOpen(3)).isTrue();
    }

    @Test
    public void ShouldCloseAllIdentifiedOpenedLights_WhenToggle() {
        var lights = new LightDiagram("[.###]");

        lights.toggle(List.of(1, 2, 3));

        assertThat(lights.isOpen(1)).isFalse();
        assertThat(lights.isOpen(2)).isFalse();
        assertThat(lights.isOpen(3)).isFalse();
    }

    @Test
    public void ShouldBeAbleToClone() {
        var lights = new LightDiagram("[.###]");

        var res = lights.clone();

        assertThat(res.isOpen(0)).isFalse();
        assertThat(res.isOpen(1)).isTrue();
        assertThat(res.isOpen(2)).isTrue();
    }

    @Test
    public void ShouldBeAbleToCompareEquality() {
        var l1 = new LightDiagram("[.###]");
        var l2 = new LightDiagram("[.###]");

        assertThat(l1).isEqualTo(l2);
    }

    @Test
    public void ShouldBeAbleToCloseAll() {
        var l1 = new LightDiagram("[.###]");

        l1.closeAll();

        assertThat(l1.isOpen(1)).isFalse();
    }
}
