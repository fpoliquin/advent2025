package advent2025.no10;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

public class ButtonWiringTest {
    @Test
    public void ShouldParseWiring() {
        var wiring = new ButtonWiring("(1)");

        assertThat(wiring.includes(0)).isFalse();
        assertThat(wiring.includes(1)).isTrue();
    }

    @Test
    public void ShouldParseMultipleWiring() {
        var wiring = new ButtonWiring("(1,2)");

        assertThat(wiring.includes(0)).isFalse();
        assertThat(wiring.includes(1)).isTrue();
        assertThat(wiring.includes(2)).isTrue();
        assertThat(wiring.includes(3)).isFalse();
    }

    @Test
    public void ShouldToggleLights() {
        var lights = new LightDiagram(List.of(false, false, false, false));
        var button = new ButtonWiring("(1,2)");

        button.toggle(lights);

        assertThat(lights.isOpen(0)).isFalse();
        assertThat(lights.isOpen(1)).isTrue();
        assertThat(lights.isOpen(2)).isTrue();
        assertThat(lights.isOpen(3)).isFalse();
    }
}
