package advent2025.no10;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

public class Algebra {
    public record Problem(List<? extends Equation> equations) {
        public List<Result> solve() {
            return solve(equations, new ArrayList<>());
        }

        static List<Result> solve(List<? extends Equation> equations, List<Result> results) {
            if (equations.isEmpty()) {
                throw new NoSuchElementException();
            }

            var firstSolvedEquation = equations.stream().filter(Equation::isSolved).findFirst();

            if (firstSolvedEquation.isPresent()) {
                return handleSolvedEquation(equations, results, firstSolvedEquation.get());
            }

            var newEquation = equations.getFirst().isolateFirstVariable();
            var remainingEquations = new ArrayList<Equation>(equations);

            if (newEquation.isSolved()) {
                remainingEquations.set(0, newEquation);
                return handleSolvedEquation(remainingEquations, results, newEquation);
            }

            remainingEquations.removeFirst();

            remainingEquations.replaceAll(e -> e.replaceVariable(newEquation));

            return solve(remainingEquations, results);
        }

        private static List<Result> handleSolvedEquation(List<? extends Equation> equations, List<Result> results, Equation solvedEquation) {
            var result = solvedEquation.toResult();
            results.add(result);
            var remainingEquations = new ArrayList<Equation>(equations);
            remainingEquations.remove(solvedEquation);

            if (remainingEquations.isEmpty()) {
                return results;
            }

            remainingEquations.replaceAll(e -> e.applyResult(result));
            return solve(remainingEquations, results);
        }
    }

    public record Equation(Expression left, Expression right) {
        public Equation {
            if (left.hasNoVariables() && right.hasNoVariables()) {
                throw new IllegalStateException("No variables in equation.");
            }
        }

        Equation applyResult(Result result) {
            return new Equation(left.applyResult(result), right.applyResult(result));
        }

        Equation isolateFirstVariable() {
            var expressions = left.isolateFirstVariable();
            var newLeft = expressions[0];
            var newRight = right.merge(expressions[1]);
            return new Equation(newLeft, newRight);
        }

        public boolean isSolved() {
            return (left.hasNoVariables() && right.hasSingleVariable())
                    || (right.hasNoVariables() && left.hasSingleVariable());
        }

        public Result toResult() {
            if (!isSolved()) {
                throw new IllegalStateException("Not solved.");
            }

            if (left.hasNoVariables()) {
                return new Result(right.solvedVariable(), left.solvedValue());
            }

            return new Result(left.solvedVariable(), right.solvedValue());
        }

        public Equation replaceVariable(Equation newEquation) {
            var variable = newEquation.left.terms.getFirst().variable();

            Expression newLeft = left.replaceVariable(variable, newEquation.right);
            Expression newRight = right.replaceVariable(variable, newEquation.right);

            var single = newLeft.findSingleCoefficient();
            if (single.isPresent()) {
                newLeft = newLeft.removeSingleCoefficient();
                newRight = newRight.merge(single.get().multiplyBy(-1));
            }
            return new Equation(newLeft, newRight);
        }

        @Override
        public String toString() {
            return left.toString() + " = " + right.toString();
        }
    }

    public record Result(String variable, int value) {
        public Result {
            if (variable == null) {
                throw new IllegalStateException("Variable is null in result.");
            }
        }
    }

    public record Expression(List<? extends Term> terms) {
        Expression merge(Term term) {
            return merge(List.of(term));
        }

        Expression merge(Expression expression) {
            return merge(expression.terms);
        }

        Expression merge(List<? extends Term> otherTerms) {
            var newTerms = new ArrayList<Term>(terms);

            for (var termToAdd : otherTerms) {
                int index = findVariableIndex(newTerms, termToAdd.variable);

                if (index >= 0) {
                    newTerms.set(index, newTerms.get(index).add(termToAdd));
                    if (newTerms.get(index).coefficient == 0 && newTerms.get(index).hasVariable()) {
                        newTerms.remove(index);
                    }
                } else {
                    newTerms.add(termToAdd);
                }
            }
            return new Expression(newTerms);
        }

        static int findVariableIndex(List<? extends Term> terms, String variable) {
            for (int i=0; i < terms.size(); ++i) {
                var existingTerm = terms.get(i);
                if (existingTerm.isSameVariable(variable)) {
                    return i;
                }
            }

            return -1;
        }


        Expression replaceVariable(String variable, Expression replacement) {
            var newList = new ArrayList<Term>();

            for (var term : terms) {
                if (term.isSameVariable(variable)) {
                    newList.addAll(term.replaceVariableWith(replacement));
                } else {
                    newList.add(term);
                }
            }

            return new Expression(newList);
        }

        public Expression[] isolateFirstVariable() {
            for (int i=0; i < terms.size(); ++i) {
                var term = terms.get(i);

                if (term.hasVariable()) {
                    var newLeft = List.of(term.resetCoefficient());
                    var newRight = new ArrayList<Term>(terms);
                    newRight.remove(term);
                    newRight.removeIf(t -> t.coefficient == 0);
                    newRight.replaceAll(t -> t.divideBy(-1*term.coefficient));
                    return new Expression[] { new Expression(newLeft), new Expression(newRight)};
                }
            }

            return new Expression[] { this, new Expression(List.of())};
        }

        public boolean hasNoVariables() {
            return terms.stream().noneMatch(Term::hasVariable);
        }

        public String solvedVariable() {
            return terms.getFirst().variable();
        }

        public int solvedValue() {
            return terms.getFirst().coefficient();
        }

        public Expression applyResult(Result result) {
            return new Expression(terms.stream().map(t -> t.applyResult(result)).toList());
        }

        public boolean hasSingleVariable() {
            return terms.stream().filter(Term::hasVariable).count() == 1;
        }

        @Override
        public String toString() {
            return terms.stream().map(Term::toString).collect(Collectors.joining(" + "));
        }

        public Optional<Term> findSingleCoefficient() {
            for (Term term : terms) {
                if (!term.hasVariable()) {
                    return Optional.of(term);
                }
            }
            return Optional.empty();
        }

        public Expression removeSingleCoefficient() {
            return new Expression(terms.stream().filter(Term::hasVariable).toList());
        }
    }

    public record Term(int coefficient, String variable) {

        Term applyResult(Result result) {
            if (result.variable.equals(variable)) {
                return new Term(coefficient*result.value, null);
            }
            return this;
        }

        boolean hasVariable() {
            return variable != null;
        }

        List<? extends Term> replaceVariableWith(Expression expr) {
            if (variable == null) {
                throw new IllegalStateException("No variable");
            }

            return expr.terms.stream().map(t -> t.multiplyBy(coefficient)).toList();
        }

        boolean isSameVariable(Term t2) {
            return isSameVariable(t2.variable);
        }

        boolean isSameVariable(String v) {
            if (variable == null) {
                return v == null;
            }
            return variable.equals(v);
        }

        Term multiplyBy(int value) {
            return new Term(coefficient*value, variable);
        }

        Term divideBy(int value) {
            return new Term(coefficient / value, variable);
        }

        Term add(Term t2) {
            if (!isSameVariable(t2)) {
                throw new IllegalStateException();
            }

            return new Term(coefficient + t2.coefficient, variable);
        }

        public Term resetCoefficient() {
            return new Term(1, variable);
        }

        @Override
        public String toString() {
            return coefficient + (variable == null ? "" : "x" + variable);
        }
    }
}
