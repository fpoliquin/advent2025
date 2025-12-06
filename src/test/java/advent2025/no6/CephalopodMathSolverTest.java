package advent2025.no6;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class CephalopodMathSolverTest {

    public static final String SAMPLE = """
            123 328  51 64\s
             45 64  387 23\s
              6 98  215 314
            *   +   *   + \s
            """;

    @Test
    public void ShouldWorkoutMath() {
        var solver = new CephalopodMathSolver(SAMPLE);

        var res = solver.computeGrandTotal();

        assertThat(res).isEqualTo(4277556L);
    }

    @Test
    public void ShouldWorkoutMathRightToLeft() {
        var solver = new CephalopodMathSolver2(SAMPLE);

        var res = solver.computeGrandTotalRightToLeft();

        assertThat(res).isEqualTo(3263827L);
    }
}
