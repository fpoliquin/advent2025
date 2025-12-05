package advent2025.no5;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class IngredientsDbTest {
    private static final String SAMPLE = """
            3-5
            10-14
            16-20
            12-18
            
            1
            5
            8
            11
            17
            32
            """;

    @Test
    public void ShouldParseDatabaseFile() {
        var db = new IngredientsDb(SAMPLE);

        var rangeCount = db.rangeCount();
        var ingredientCount = db.ingredientCount();

        assertThat(rangeCount).isEqualTo(4);
        assertThat(ingredientCount).isEqualTo(6);
    }

    @Test
    public void ShouldCountHowManyAvailableIngredientsAreFresh() {
        var db = new IngredientsDb(SAMPLE);

        var res = db.howManyAvailableIngredientsAreFresh();

        assertThat(res).isEqualTo(3);
    }

    @Test
    public void ShouldCountHowManyIngredientsAreConsideredFresh() {
        var db = new IngredientsDb(SAMPLE);

        var res = db.howManyIngredientsAreConsideredFresh();

        assertThat(res).isEqualTo(14);
    }
}
