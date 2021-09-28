package core.objects.expression;

import java.util.HashMap;

public interface Substitutable {
    Monomial substitute(HashMap<Monomial, Monomial> substituteTable);
}
