package advent2025.no10;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Set;

public class MachineTest {
    @Test
    public void ShouldStartMachine_WhenPressingGoodButton() {
        var machine = new Machine(new LightDiagram(List.of(true)), List.of(new ButtonWiring(Set.of(0))), new Joltage("{1}"));

        machine.press(0);

        assertThat(machine.isStated()).isTrue();
    }

    @Test
    public void ShouldNotStartMachine_WhenPressingWrongButton() {
        var machine = new Machine(new LightDiagram(List.of(true, false)), List.of(new ButtonWiring(Set.of(1))), new Joltage("{1}"));

        machine.press(0);

        assertThat(machine.isStated()).isFalse();
    }

    @Test
    public void ShouldParseLightDiagram() {
        var machine = new Machine("[...] (1) (2) {1,2}");

        assertThat(machine.lightDiagram()).isEqualTo(new LightDiagram("[...]"));
    }

    @Test
    public void ShouldParseButtons() {
        var machine = new Machine("[...] (1) (2) {1,2}");

        assertThat(machine.buttons()).isEqualTo(List.of(new ButtonWiring("(1)"), new ButtonWiring("(2)")));
    }

    @Test
    public void ShouldFindFewestTotalPresses() {
        var machine = new Machine("[.##.] (3) (1,3) (2) (2,3) (0,2) (0,1) {3,5,4,7}");

        var res = machine.findFewestTotalPresses();

        assertThat(res).isEqualTo(2);
    }

    @Test
    public void ShouldFindFewestTotalPresses2() {
        var machine = new Machine("[...#.] (0,2,3,4) (2,3) (0,4) (0,1,2) (1,2,3,4) {7,5,12,7,2}");

        var res = machine.findFewestTotalPresses();

        assertThat(res).isEqualTo(3);
    }

    @Test
    public void ShouldFindFewestTotalPresses3() {
        var machine = new Machine("[.###.#] (0,1,2,3,4) (0,3,4) (0,1,2,4,5) (1,2) {10,11,11,5,10,5}");

        var res = machine.findFewestTotalPresses();

        assertThat(res).isEqualTo(2);
    }

    @Test
    public void ShouldParseJoltage() {
        var machine = new Machine("[...] (1) (2) {1,2}");

        assertThat(machine.joltage()).isEqualTo(new Joltage("{1,2}"));
    }

    @Test
    public void ShouldFindFewestTotalPressesForJoltage() {
        var machine = new Machine("[.##.] (3) (1,3) (2) (2,3) (0,2) (0,1) {3,5,4,7}");

        var res = machine.findFewestTotalPressesForJoltage();

        assertThat(res).isEqualTo(10);
    }

    @Test
    public void ShouldFindFewestTotalPressesForJoltage2() {
        var machine = new Machine("[...#.] (0,2,3,4) (2,3) (0,4) (0,1,2) (1,2,3,4) {7,5,12,7,2}");

        var res = machine.findFewestTotalPressesForJoltage();

        assertThat(res).isEqualTo(12);
    }

    @Test
    public void ShouldFindFewestTotalPressesForJoltage3() {
        var machine = new Machine("[.###.#] (0,1,2,3,4) (0,3,4) (0,1,2,4,5) (1,2) {10,11,11,5,10,5}");

        var res = machine.findFewestTotalPressesForJoltage();

        assertThat(res).isEqualTo(11);
    }
}
