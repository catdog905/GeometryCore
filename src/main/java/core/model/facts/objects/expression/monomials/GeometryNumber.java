package core.model.facts.objects.expression.monomials;

import java.util.HashMap;

public class GeometryNumber extends MonomialEnveloper<Number> {
    GeometryNumber(Number toEnvelop) {
        super(toEnvelop);
    }

    public static GeometryNumber get(Number number) {
        return Monomial.buildOf(number, Number.class, GeometryNumber.class);
    }

    @Override
    public Monomial substitute(HashMap<Monomial, Monomial> substituteTable) {
        return this;
    }

    public static Number addNumbers(Number a, Number b) {
        if(a instanceof Double || b instanceof Double) {
            return a.doubleValue() + b.doubleValue();
        } else if(a instanceof Float || b instanceof Float) {
            return a.floatValue() + b.floatValue();
        } else if(a instanceof Long || b instanceof Long) {
            return a.longValue() + b.longValue();
        } else {
            return a.intValue() + b.intValue();
        }
    }
    private static final double EPSILON = 0.000000000000001d;
    public static boolean areNumbersEquivalent(Number a, Number b) {
        if (a == null)
        {
            return b == null;
        }
        else if (b == null)
        {
            return false;
        }
        else
        {
            return Math.abs(a.doubleValue() - b.doubleValue()) < EPSILON;
        }
    }
}
