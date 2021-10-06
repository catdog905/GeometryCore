package core.objects.expression;

import java.util.LinkedList;
import java.util.stream.Collectors;

public class AllBracketsExpandedMonomial extends Monomial{

    public AllBracketsExpandedMonomial(Monomial monomial) {
        super(expandAllBrackets(monomial));
    }

    private static Monomial expandAllBrackets(Monomial expression) {
        if (expression instanceof Polynomial) {
            return new Polynomial(expression.getAllSubObjects().stream()
                    .map(AllBracketsExpandedMonomial::expandAllBrackets)
                    .collect(Collectors.toCollection(LinkedList::new)));
        } else if (expression instanceof RaisedInThePower) {
            return new RaisedInThePower(expandAllBrackets(((RaisedInThePower) expression).base),
                    expandAllBrackets(((RaisedInThePower) expression).power));
        }
        if (expression.getAllSubObjects().size() == 0)
            return expression;
        return new MultipliedMonomial(expression.getAllSubObjects()
                .stream().map(AllBracketsExpandedMonomial::expandAllBrackets)
                .collect(Collectors.toCollection(LinkedList::new)));
    }
}
