package advent2025.no10;

import advent2025.no10.Algebra.*;

import java.util.*;

public class Machine {

    private final LightDiagram diagram;
    private final LightDiagram machineState;
    private final Joltage joltage;
    private final List<ButtonWiring> buttons;

    public Machine(LightDiagram lightDiagram, List<ButtonWiring> buttons, Joltage joltage) {
        this.diagram = lightDiagram;
        this.machineState = lightDiagram.clone();
        this.joltage = joltage;
        this.machineState.closeAll();
        this.buttons = buttons;
    }

    public Machine(String data) {
        this(new LightDiagram(data.substring(0, data.indexOf(']')+1)),
                Arrays.stream(data
                        .substring(data.indexOf('('), data.indexOf('{'))
                        .split("\\s+"))
                        .map(ButtonWiring::new)
                        .toList(),
                new Joltage(data.substring(data.indexOf('{'))));
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

    public Joltage joltage() {
        return joltage;
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

    public int findFewestTotalPressesForJoltage() {
        var equations = new ArrayList<Equation>();

        for (int i=0; i < joltage.size(); ++i) {
            var terms = new ArrayList<Term>();
            for (int j=0; j < buttons.size(); ++j) {
                if (buttons.get(j).pressesIndex(i)) {
                    terms.add(new Term(1, "b" + j));
                }
            }

            equations.add(new Equation(new Expression(terms),
                    new Expression(List.of(new Term(joltage.value(i), null)))));
        }

        return new Problem(equations).solve().stream().map(Result::value).reduce(0, Integer::sum);
    }
}
