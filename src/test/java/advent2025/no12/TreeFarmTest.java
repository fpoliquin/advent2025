package advent2025.no12;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

public class TreeFarmTest {
    @Test
    public void ShouldParseData() {
        var farm = new TreeFarm("""
                0:
                ###
                
                1:
                #.#
                ###
                
                4x4: 0 1
                """);

        assertThat(farm.shapes()).isEqualTo(List.of(new PresentShape("###"), new PresentShape("#.#\n###")));
    }
}
