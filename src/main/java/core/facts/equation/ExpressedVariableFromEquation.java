package core.facts.equation;

import core.objects.expression.Monomial;

public class ExpressedVariableFromEquation extends EqualityFact {
    public ExpressedVariableFromEquation(EqualityFact fact, Monomial value) {
        super(value, Expressor.expressVariableFromEquation(value, (Monomial)fact.left,
                (Monomial)fact.right));
    }
}
