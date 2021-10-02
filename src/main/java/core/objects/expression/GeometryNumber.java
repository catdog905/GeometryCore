package core.objects.expression;

import java.util.HashMap;

import core.objects.GeometryObject;

public class GeometryNumber extends MonomialEnveloper<Number> {
    GeometryNumber(Number toEnvelop) {
        super(toEnvelop);
    }

    public static GeometryNumber get(Number number) {
        return Monomial.buildOf(number, Number.class, GeometryNumber.class);
    }

    @Override
    public boolean isEquivalentTo(GeometryObject object) {
        if (!getClass().equals(object.getClass()))
            return false;
        return ((MonomialEnveloper)object).toEnvelop().equals(super.toEnvelop());
    }

    @Override
    public Monomial substitute(HashMap<Monomial, Monomial> substituteTable) {
        return this;
    }
}
