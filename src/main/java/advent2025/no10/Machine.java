package advent2025.no10;

import java.util.Arrays;
import java.util.List;

public class Machine {

    private final LightDiagram diagram;
    private final LightDiagram machineState;
    private final List<ButtonWiring> buttons;

    public Machine(LightDiagram lightDiagram, List<ButtonWiring> buttons) {
        this.diagram = lightDiagram;
        this.machineState = lightDiagram.clone();
        this.machineState.closeAll();
        this.buttons = buttons;
    }

    public Machine(String data) {
        this(new LightDiagram(data.substring(0, data.indexOf(']')+1)),
                Arrays.stream(data
                        .substring(data.indexOf('('), data.indexOf('{'))
                        .split("\\s+"))
                        .map(ButtonWiring::new)
                        .toList());
    }

    public void press(int buttonIndex) {
        buttons.get(buttonIndex).toggle(machineState);
    }

    public boolean isStated() {
        return machineState.equals(diagram);
    }

    public LightDiagram lightDiagram() {
        return diagram;
    }

    public List<ButtonWiring> buttons() {
        return buttons;
    }

    public int findFewestTotalPresses() {
        for (var i=1; i < 10; ++i) {
            var res = findFewestTotalPresses(machineState, 0, i);

            if (res >= 0) {
                return res;
            }
        }

        throw new RuntimeException("Not found.");
    }

    private int findFewestTotalPresses(LightDiagram startState, int step, int maxStep) {
        if (step >= maxStep) {
            return -1;
        }

        for (var button : buttons) {
            var currentState = startState.clone();

            button.toggle(currentState);

            if (currentState.equals(diagram)) {
                return step+1;
            }

            var res = findFewestTotalPresses(currentState, step+1, maxStep);

            if (res >= 0) {
                return res;
            }
        }

        return -1;
    }
}
