package core.objects.expression;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.stream.Collectors;

public class BracketsExpander {
    private Monomial monomial;
    private Monomial result = null;

    public BracketsExpander(Monomial monomial) {
        this.monomial = monomial;
    }

    public Monomial get() {
        if (result == null)
            result = expandAllBrackets(monomial);
        return result;
    }

    private Monomial expandAllBrackets(Monomial expression) {
        if (expression instanceof Polynomial) {
            LinkedList<Monomial> newSubObjects = new LinkedList<>();
            for (int i = 0; i < expression.getAllSubObjects().size(); i++) {
                Monomial simplified = expandAllBrackets((Monomial) expression.getAllSubObjects().get(i));
                if (simplified instanceof Polynomial)
                    newSubObjects.addAll((LinkedList<Monomial>)simplified.getAllSubObjects());
                else
                    newSubObjects.add(simplified);
            }
            return new Polynomial(newSubObjects);
        } else if (expression instanceof RaisedInThePower) {
            LinkedList<LinkedList<? extends Monomial>> newFactors = ((RaisedInThePower) expression).getAllSubObjects().stream()
                    .map(x -> {
                        if (x instanceof RaisedInThePower) {
                            Monomial power = new Monomial(
                                    ((RaisedInThePower) x).power, ((RaisedInThePower) expression).power);
                            return  x.getAllSubObjects().stream()
                                    .map(y -> new RaisedInThePower(expandAllBrackets((Monomial) y), expandAllBrackets(power)))
                                    .collect(Collectors.toCollection(LinkedList::new));
                        } else if (x instanceof GeometryNumber) {
                            return new LinkedList<>(Arrays.asList(new RaisedInThePower((GeometryNumber) x,
                                    expandAllBrackets(((RaisedInThePower) expression).power))));
                        } else {
                            return x.getAllSubObjects().stream()
                                    .map(y -> new RaisedInThePower(expandAllBrackets((Monomial) y),
                                            ((RaisedInThePower) expression).power))
                                    .collect(Collectors.toCollection(LinkedList::new));
                        }
                    }).collect(Collectors.toCollection(LinkedList::new));
            LinkedList<Monomial> lineListFactors = new LinkedList<>();
            for (LinkedList<? extends Monomial> list : newFactors) {
                lineListFactors.addAll(list);
            }
            return new Monomial(lineListFactors);
        }
        if (expression instanceof GeometryNumber || expression instanceof GeometryNumber)
            return expression;
        return new MultiplyThis(
                expression.getAllSubObjects().stream().map(
                        x -> new BracketsExpander((Monomial) x).get()
                ).collect(Collectors.toCollection(LinkedList::new))
        ).get();
    }
}
