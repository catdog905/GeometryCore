package core.model.facts.objects.expression;

import java.util.HashMap;

import core.model.facts.objects.expression.monomials.Monomial;

public interface Substitutable {
    Monomial substitute(HashMap<Monomial, Monomial> substituteTable);
}
