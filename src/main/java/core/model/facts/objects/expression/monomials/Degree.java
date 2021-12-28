package core.model.facts.objects.expression.monomials;

public class Degree extends GeometryNumber {

    Degree(Number number) {
        super(number);
    }

    public static Degree get(Number number) {
        return Monomial.buildOf(number, Number.class, Degree.class);
    }
}
