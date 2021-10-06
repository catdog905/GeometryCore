import org.junit.Test;

import core.objects.expression.GeometryNumber;
import core.objects.expression.Monomial;
import core.objects.expression.MultipliedMonomial;
import core.objects.expression.Polynomial;

public class MultipliedMonomialTest {
    @Test
    public void MonomialAndPolynomialTest() {
        Polynomial polynomial = new Polynomial(GeometryNumber.get(2), GeometryNumber.get(3),
                GeometryNumber.get(3));
        Monomial monomial = new Monomial(GeometryNumber.get(1));
        Monomial multipliedMonomial = new MultipliedMonomial(monomial, polynomial);
    } //Broken
}
