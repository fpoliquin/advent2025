package advent2025.no10;

import java.time.Duration;
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
        var currentState = joltage.reset();

        var now = System.currentTimeMillis();
        var res = findFewestTotalPressesForJoltage(buttons, 0, currentState, joltage);
        var duration = Duration.ofMillis(System.currentTimeMillis() - now);
        System.out.println(duration.toMinutes() + ":" + duration.toSecondsPart());
        return res;
    }

    private static int findFewestTotalPressesForJoltage(List<ButtonWiring> allButtons, final int pressCount, Joltage currentState, Joltage goalState) {

        {
            var buttonCount = 0;
            var myState = currentState;
            var singlePressState = goalState.reset();

            for (var button : allButtons) {
                singlePressState = button.press(singlePressState);
            }

            if (singlePressState.hasOneValueToOne()) {
                int index = singlePressState.getFirstIndexToOne();
                var uniqueButton = allButtons.stream().filter(b -> b.pressesIndex(index)).findAny().orElseThrow();
                int existingValue = myState.value(index);
                int goalValue = goalState.value(index);
                buttonCount = goalValue - existingValue;

                for (int i = 0; i < buttonCount; ++i) {
                    myState = uniqueButton.press(myState);
                }

                var remainingButtons = new ArrayList<>(allButtons);
                remainingButtons.remove(uniqueButton);
                try {
                    return findFewestTotalPressesForJoltage(remainingButtons, pressCount + buttonCount, myState, goalState);
                } catch (NoSuchElementException ignored) {
                }
            }
        }

        var buttonCount = 0;

        var maxButton = allButtons.stream().max(Comparator.comparingInt(ButtonWiring::sum)).orElseThrow();

        var nextState = maxButton.press(currentState);
        while(!nextState.isAbove(goalState)) {
            ++buttonCount;
            currentState = nextState;
            nextState = maxButton.press(currentState);
        }

        if (currentState.equals(goalState)) {
            System.out.println(currentState);
            System.out.println(goalState);
            System.out.println(buttonCount + " x " + maxButton);
            return pressCount + buttonCount;
        }

        var availableButtons = new ArrayList<>(allButtons);
        availableButtons.remove(maxButton);

        while (buttonCount >= 0) {
            try {
                var res = findFewestTotalPressesForJoltage(availableButtons, pressCount+buttonCount, currentState, goalState);
                System.out.println(buttonCount + " x " + maxButton);
                return res;
            } catch (NoSuchElementException ignored) {
                currentState = maxButton.unpress(currentState);
                --buttonCount;
            }
        }

        throw new NoSuchElementException();
    }
}
