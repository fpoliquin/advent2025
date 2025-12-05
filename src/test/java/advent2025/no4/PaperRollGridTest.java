package advent2025.no4;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class PaperRollGridTest {

    @Test
    public void ShouldFindRollsWithLessThan4AdjacentPositions() {
        var grid = new PaperRollGrid("""
                ..@@.@@@@.
                @@@.@.@.@@
                @@@@@.@.@@
                @.@@@@..@.
                @@.@@@@.@@
                .@@@@@@@.@
                .@.@.@.@@@
                @.@@@.@@@@
                .@@@@@@@@.
                @.@.@@@.@.
                """);

        var res = grid.countRollsForkliftCanAccess();

        assertThat(res).isEqualTo(13);
    }

    @Test
    public void ShouldBeAbleToRemoveRollsRecursively() {
        var grid = new PaperRollGrid("""
                ..@@.@@@@.
                @@@.@.@.@@
                @@@@@.@.@@
                @.@@@@..@.
                @@.@@@@.@@
                .@@@@@@@.@
                .@.@.@.@@@
                @.@@@.@@@@
                .@@@@@@@@.
                @.@.@@@.@.
                """);

        var res = grid.removeAllRollsForkliftCanAccess();

        assertThat(res).isEqualTo(43);
    }
}
