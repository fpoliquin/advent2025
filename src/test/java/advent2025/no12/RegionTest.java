package advent2025.no12;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

public class RegionTest {
    @ParameterizedTest
    @CsvSource({
            "4x5: 0 0, 4, 5"
    })
    public void ShouldParseDimensions(String data, int expectedWidth, int expectedHeight) {
        var region = new Region(data);

        var width = region.width();
        var height = region.height();

        assertThat(width).isEqualTo(expectedWidth);
        assertThat(height).isEqualTo(expectedHeight);
    }

    @Test
    public void ShouldParseQuantities() {
        var region = new Region("4x5: 0 10 2 3 4");

        assertThat(region.quantity(0)).isEqualTo(0);
        assertThat(region.quantity(1)).isEqualTo(10);
        assertThat(region.quantity(2)).isEqualTo(2);
        assertThat(region.quantity(3)).isEqualTo(3);
        assertThat(region.quantity(4)).isEqualTo(4);
    }

    @Test
    public void ShouldNotBeAbleToFitShape_GivenItIsTooWide() {
        var region = new Region("2x1: 1");
        var shapes = List.of(new PresentShape("###"));

        var res = region.canFitShapes(shapes);

        assertThat(res).isFalse();
    }

    @Test
    public void ShouldBeAbleToFitSimpleShape() {
        var region = new Region("2x1: 1");
        var shapes = List.of(new PresentShape("##"));

        var res = region.canFitShapes(shapes);

        assertThat(res).isTrue();
    }

    @Test
    public void ShouldNotBeAbleToFitShape_GivenTooLong() {
        var region = new Region("10x1: 1");
        var shapes = List.of(new PresentShape("##\n##"));

        var res = region.canFitShapes(shapes);

        assertThat(res).isFalse();
    }

    @Test
    public void ShouldNotBeAbleToFitShape_GivenTooWide() {
        var region = new Region("2x2: 1");
        var shapes = List.of(new PresentShape("###\n##."));

        var res = region.canFitShapes(shapes);

        assertThat(res).isFalse();
    }

    @Test
    public void ShouldBeAbleToFitShape_GivenTooWideButEmpty() {
        var region = new Region("2x2: 1");
        var shapes = List.of(new PresentShape("##.\n##."));

        var res = region.canFitShapes(shapes);

        assertThat(res).isTrue();
    }

    @Test
    public void ShouldBeAbleToFitShapes() {
        var region = new Region("2x2: 4");
        var shapes = List.of(shape("#"));

        var res = region.canFitShapes(shapes);

        assertThat(res).isTrue();
    }

    @Test
    public void ShouldNotBeAbleToFitShapes_GivenTooManyTimes() {
        var region = new Region("2x2: 5");
        var shapes = List.of(shape("#"));

        var res = region.canFitShapes(shapes);

        assertThat(res).isFalse();
    }

    @Test
    public void ShouldNotBeAbleToFitShapes_GivenShapesDoNotFit() {
        var region = new Region("3x2: 3");
        var shapes = List.of(shape("#.#"));

        var res = region.canFitShapes(shapes);

        assertThat(res).isFalse();
    }

    private static PresentShape shape(String data) {
        return new PresentShape(data);
    }
}
