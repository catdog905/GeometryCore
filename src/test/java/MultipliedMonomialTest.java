import static org.junit.Assert.assertEquals;

import org.junit.Test;

import core.objects.expression.GeometryNumber;
import core.objects.expression.Monomial;
import core.objects.expression.Polynomial;

public class MultipliedMonomialTest {
    @Test
    public void MonomialAndPolynomialTest() {
        Polynomial polynomial = new Polynomial(GeometryNumber.get(2), GeometryNumber.get(3),
                GeometryNumber.get(3));
        Monomial monomial = GeometryNumber.get(1);
        Monomial multipliedMonomial = monomial.multiplyWith(polynomial);
        Monomial expectedMonomial = new Polynomial(
                new Monomial(GeometryNumber.get(1), GeometryNumber.get(2)),
                new Monomial(GeometryNumber.get(1), GeometryNumber.get(3)),
                new Monomial(GeometryNumber.get(1), GeometryNumber.get(3)));
        assertEquals(expectedMonomial, multipliedMonomial);
    }

    @Test
    public void MonomialAndMonomialTest() {
        Monomial monomial1 = new Monomial(GeometryNumber.get(2), GeometryNumber.get(3),
                GeometryNumber.get(3));
        Monomial monomial2 = GeometryNumber.get(1);
        Monomial multipliedMonomial = monomial1.multiplyWith(monomial2);
        Monomial expectedMonomial = new Monomial(GeometryNumber.get(2), GeometryNumber.get(3),
                GeometryNumber.get(3));
        assertEquals(expectedMonomial, multipliedMonomial);
    }

    @Test
    public void MonomialAndPolynomialInverseTest() {
        Polynomial polynomial = new Polynomial(GeometryNumber.get(2), GeometryNumber.get(3),
                GeometryNumber.get(3));
        Monomial monomial = GeometryNumber.get(1);
        Monomial multipliedMonomial = polynomial.multiplyWith(monomial);
        Monomial expectedMonomial = new Polynomial(
                new Monomial(GeometryNumber.get(1), GeometryNumber.get(2)),
                new Monomial(GeometryNumber.get(1), GeometryNumber.get(3)),
                new Monomial(GeometryNumber.get(1), GeometryNumber.get(3)));
        assertEquals(expectedMonomial, multipliedMonomial);
    }

    @Test
    public void PolynomialAndPolynomialTest() {
        Polynomial polynomial1 = new Polynomial(GeometryNumber.get(2), GeometryNumber.get(3),
                GeometryNumber.get(3));
        Monomial polynomial2 = new Polynomial(GeometryNumber.get(1), GeometryNumber.get(3),
                GeometryNumber.get(4));;
        Monomial multipliedMonomial = polynomial1.multiplyWith(polynomial2);
        Monomial expectedMonomial = new Polynomial(
                new Monomial(GeometryNumber.get(1), GeometryNumber.get(2)),
                new Monomial(GeometryNumber.get(2), GeometryNumber.get(3)),
                new Monomial(GeometryNumber.get(2), GeometryNumber.get(4)),
                new Monomial(GeometryNumber.get(1), GeometryNumber.get(3)),
                new Monomial(GeometryNumber.get(3), GeometryNumber.get(3)),
                new Monomial(GeometryNumber.get(3), GeometryNumber.get(4)),
                new Monomial(GeometryNumber.get(1), GeometryNumber.get(3)),
                new Monomial(GeometryNumber.get(3), GeometryNumber.get(3)),
                new Monomial(GeometryNumber.get(3), GeometryNumber.get(4)));
        assertEquals(expectedMonomial, multipliedMonomial);
    }
}
