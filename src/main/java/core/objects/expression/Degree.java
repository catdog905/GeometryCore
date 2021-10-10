package core.objects.expression;

public class Degree extends GeometryNumber {

    Degree(Number number) {
        super(number);
    }

    public static Degree get(Number number) {
        return Monomial.buildOf(number, Number.class, Degree.class);
    }
}
