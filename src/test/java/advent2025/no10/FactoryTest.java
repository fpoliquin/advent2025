package advent2025.no10;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class FactoryTest {
    private static final String SAMPLE = """
            [.##.] (3) (1,3) (2) (2,3) (0,2) (0,1) {3,5,4,7}
            [...#.] (0,2,3,4) (2,3) (0,4) (0,1,2) (1,2,3,4) {7,5,12,7,2}
            [.###.#] (0,1,2,3,4) (0,3,4) (0,1,2,4,5) (1,2) {10,11,11,5,10,5}
            """;

    @Test
    public void ShouldParseData() {
        var factory = new Factory(SAMPLE);

        var res = factory.size();

        assertThat(res).isEqualTo(3);
    }

    @Test
    public void ShouldFindTotalFewestPresses() {
        var factory = new Factory(SAMPLE);

        var res = factory.findTotalFewestPresses();

        assertThat(res).isEqualTo(7);
    }


}
