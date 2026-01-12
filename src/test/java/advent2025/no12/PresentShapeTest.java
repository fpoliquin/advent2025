package advent2025.no12;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static org.assertj.core.api.Assertions.assertThat;

public class PresentShapeTest {

    @ParameterizedTest
    @CsvSource({
            "###, 0, 0",
            "'##\n##', 1, 1"
    })
    public void ShouldParseDimensions(String data, int width, int height) {
        var cut = new PresentShape(data);

        assertThat(cut.isPartOfTheShape(width, height)).isTrue();
    }
}
