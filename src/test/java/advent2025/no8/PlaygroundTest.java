package advent2025.no8;

import advent2025.no8.Playground.JunctionBox;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class PlaygroundTest {

    private static final String SAMPLE = """
            162,817,812
            57,618,57
            906,360,560
            592,479,940
            352,342,300
            466,668,158
            542,29,236
            431,825,988
            739,650,466
            52,470,668
            216,146,977
            819,987,18
            117,168,530
            805,96,715
            346,949,466
            970,615,88
            941,993,340
            862,61,35
            984,92,344
            425,690,689
            """;

    @Test
    public void ShouldComputeTheDistanceBetween2JunctionBoxes() {
        var j1 = new JunctionBox(0, 0, 0);
        var j2 = new JunctionBox(0, 0, 4);

        var res = j1.computeDistance(j2);

        assertThat(res).isEqualTo(4D);
    }

    @Test
    public void EachJunctionBoxShouldStartInCircuit() {
        var playground = new Playground(SAMPLE);

        assertThat(playground.circuitCount()).isEqualTo(20);
    }

    @Test
    public void FirstCircuitShouldContainTwoClosestJunctionBoxes() {
        var playground = new Playground(SAMPLE);

        playground.connectNextBoxes();

        assertThat(playground.largestCircuit().boxes).contains(new JunctionBox(162, 817, 812));
        assertThat(playground.largestCircuit().boxes).contains(new JunctionBox(425, 690, 689));
        assertThat(playground.largestCircuit().size()).isEqualTo(2);
    }

    @Test
    public void SecondCircuitShouldContainTwoClosestJunctionBoxes() {
        var playground = new Playground(SAMPLE);

        playground.connectClosestJunctionBoxes(2);

        assertThat(playground.largestCircuit().boxes).contains(new JunctionBox(162, 817, 812));
        assertThat(playground.largestCircuit().boxes).contains(new JunctionBox(425, 690, 689));
        assertThat(playground.largestCircuit().boxes).contains(new JunctionBox(431,825,988));
        assertThat(playground.largestCircuit().size()).isEqualTo(3);
    }

    @Test
    public void ShouldHave11Circuits_AfterMaking10Connections() {
        var playground = new Playground(SAMPLE);

        playground.connectClosestJunctionBoxes(10);

        assertThat(playground.circuitCount()).isEqualTo(11);
    }

    @Test
    public void ShouldGive40_AfterMultiplying3LargestCircuits() {
        var playground = new Playground(SAMPLE);
        playground.connectClosestJunctionBoxes(10);

        var res = playground.multiplyLargestCircuits(3);

        assertThat(res).isEqualTo(40);
    }

    @Test
    public void XCoordinateProductFromLastTwoJunctionBoxes_ShouldProduce25272() {
        var playground = new Playground(SAMPLE);

        var res = playground.connectUntilLastTwo();

        assertThat(res).isEqualTo(25272L);
    }
}
