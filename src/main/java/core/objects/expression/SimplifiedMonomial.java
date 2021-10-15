package core.objects.expression;

import java.util.HashMap;

public class SimplifiedMonomial extends Monomial {
    public SimplifiedMonomial(Monomial monomial, HashMap<Monomial, Monomial> substituteTable) {
        super(monomial.substitute(substituteTable));
    }
}
