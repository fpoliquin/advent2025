package advent2025.no7;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class TachyonManifoldTest {

    @Test
    public void ShouldNotCountIfSplitterNotAligned() {
        var manifold = new TachyonManifold("""
                .S.
                ...
                ..^
                """);

        var res = manifold.countNumberOfSplits();

        assertThat(res).isEqualTo(0);
    }

    @Test
    public void ShouldCountAlignedSplitters() {
        var manifold = new TachyonManifold("""
                .S.
                ...
                .^.
                """);

        var res = manifold.countNumberOfSplits();

        assertThat(res).isEqualTo(1);
    }

    @Test
    public void ShouldActivateSplitter_GivenNewRay() {
        var manifold = new TachyonManifold("""
                .S.
                ...
                .^.
                ^..
                """);

        var res = manifold.countNumberOfSplits();

        assertThat(res).isEqualTo(2);
    }

    @Test
    public void ShouldPassSample() {
        var manifold = new TachyonManifold("""
                .......S.......
                   .......|.......
                   ......|^|......
                   ......|.|......
                   .....|^|^|.....
                   .....|.|.|.....
                   ....|^|^|^|....
                   ....|.|.|.|....
                   ...|^|^|||^|...
                   ...|.|.|||.|...
                   ..|^|^|||^|^|..
                   ..|.|.|||.|.|..
                   .|^|||^||.||^|.
                   .|.|||.||.||.|.
                   |^|^|^|^|^|||^|
                   |.|.|.|.|.|||.|
                """);

        var res = manifold.countNumberOfSplits();

        assertThat(res).isEqualTo(21);
    }
}
