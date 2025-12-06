package advent2025.no6;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class CephalopodMathProblemTest {

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

        assertThat(res).isEqualTo(4277556);
    }
}
