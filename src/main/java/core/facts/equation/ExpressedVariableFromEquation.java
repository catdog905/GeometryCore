package core.facts.equation;

import core.objects.expression.Monomial;
import core.objects.numbers.NumberValue;

public class ExpressedVariableFromEquation extends EqualityFact {
    public ExpressedVariableFromEquation(EqualityFact fact, NumberValue value) {
        super(value, Expressor.expressVariableFromEquation(value, (Monomial)fact.left,
                (Monomial)fact.right));
    }
}
