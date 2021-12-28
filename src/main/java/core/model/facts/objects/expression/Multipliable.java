package core.model.facts.objects.expression;

import core.model.facts.objects.expression.monomials.Monomial;

public interface Multipliable {
    Monomial multiplyWith(Monomial monomial);
}
