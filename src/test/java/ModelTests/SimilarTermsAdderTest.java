package ModelTests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import core.model.facts.objects.LineSegment;
import core.model.facts.objects.Vertex;
import core.model.facts.objects.expression.monomials.GeometryNumber;
import core.model.facts.objects.expression.monomials.Monomial;
import core.model.facts.objects.expression.monomials.Polynomial;
import core.model.facts.objects.expression.monomials.RaisedInThePower;

public class SimilarTermsAdderTest {

    @Test
    public void NoVariablesTest() {
        Monomial before = new Polynomial(GeometryNumber.get(1), GeometryNumber.get(2), GeometryNumber.get(3));
        Monomial after = before.addSimilarTerms();
        assertEquals(after, GeometryNumber.get(6));
    }

    @Test
    public void VariablesTest() {
        Vertex a = new Vertex();
        Vertex b = new Vertex();
        LineSegment AB = new LineSegment(a, b);
        Monomial before = new Polynomial(AB.getMonomial(),
                new Monomial(GeometryNumber.get(2), AB.getMonomial()),
                new Monomial(GeometryNumber.get(3), AB.getMonomial()));
        Monomial after = before.addSimilarTerms();
        assertEquals(after, new Monomial(AB.getMonomial(), GeometryNumber.get(6)));
    }

    @Test
    public void DifferentVariablesTest() {
        Vertex a = new Vertex();
        Vertex b = new Vertex();
        ;
        Vertex c = new Vertex();
        LineSegment AB = new LineSegment(a, b);
        LineSegment AC = new LineSegment(a, c);
        Monomial before = new Polynomial(AB.getMonomial(),
                new Monomial(GeometryNumber.get(2), AC.getMonomial()),
                new Monomial(GeometryNumber.get(21), AC.getMonomial()),
                new Monomial(GeometryNumber.get(3), AB.getMonomial()));
        Monomial after = before.addSimilarTerms();
        assertEquals(after,
                new Polynomial(
                        new Monomial(AB.getMonomial(), GeometryNumber.get(4)),
                        new Monomial(AC.getMonomial(), GeometryNumber.get(23))
                )
        );
    }

    @Test
    public void CancelOutTest() {
        Vertex a = new Vertex();
        Vertex b = new Vertex();
        ;
        Vertex c = new Vertex();
        LineSegment AB = new LineSegment(a, b);
        LineSegment AC = new LineSegment(a, c);
        Monomial before = new Polynomial(AB.getMonomial(),
                new Monomial(GeometryNumber.get(2), AB.getMonomial()),
                new Monomial(GeometryNumber.get(-3), AB.getMonomial()));
        Monomial after = before.addSimilarTerms();
        assertEquals(after, GeometryNumber.get(0));
    }

    @Test
    public void OneIsRemovedTest() {
        Vertex a = new Vertex();
        Vertex b = new Vertex();
        ;
        Vertex c = new Vertex();
        LineSegment AB = new LineSegment(a, b);
        LineSegment AC = new LineSegment(a, c);
        Monomial before = new Polynomial(AB.getMonomial(),
                new Monomial(GeometryNumber.get(2), AB.getMonomial()),
                new Monomial(GeometryNumber.get(-2), AB.getMonomial()));
        Monomial after = before.addSimilarTerms();
        assertEquals(after, AB.getMonomial());
    }

    @Test
    public void FewRaisedInThePowersTest() {
        Vertex a = new Vertex();
        Vertex b = new Vertex();
        ;
        Vertex c = new Vertex();
        LineSegment AB = new LineSegment(a, b);
        LineSegment AC = new LineSegment(a, c);
        Monomial before = new Polynomial(
                new RaisedInThePower(
                        GeometryNumber.get(1),
                        new Polynomial(
                                new Monomial(AB.getMonomial(), GeometryNumber.get(2)),
                                new Monomial(AB.getMonomial(), GeometryNumber.get(2))
                        )
                ),
                new RaisedInThePower(
                        GeometryNumber.get(1),
                        new Monomial(AB.getMonomial(), GeometryNumber.get(4))
                ));
        Monomial after = before.addSimilarTerms();
        assertEquals(after,
                new Monomial(
                        GeometryNumber.get(2),
                        new RaisedInThePower(
                                GeometryNumber.get(1),
                                new Monomial(AB.getMonomial(), GeometryNumber.get(4))
                        )
                )
        );
    }

    @Test
    public void FewRaisedInThePowersWithNumbersTest() {
        Vertex a = new Vertex();
        Vertex b = new Vertex();
        ;
        Vertex c = new Vertex();
        LineSegment AB = new LineSegment(a, b);
        LineSegment AC = new LineSegment(a, c);
        Monomial before = new Polynomial(
                new RaisedInThePower(
                        GeometryNumber.get(1),
                        new Polynomial(
                                new Monomial(AB.getMonomial(), GeometryNumber.get(2)),
                                new Monomial(AB.getMonomial(), GeometryNumber.get(2))
                        )
                ),
                new Monomial(
                        GeometryNumber.get(2),
                        new RaisedInThePower(
                                GeometryNumber.get(1),
                                new Monomial(AB.getMonomial(), GeometryNumber.get(4))
                        )
                ));
        Monomial after = before.addSimilarTerms();
        assertEquals(after,
                new Monomial(
                        GeometryNumber.get(3),
                        new RaisedInThePower(
                                GeometryNumber.get(1),
                                new Monomial(AB.getMonomial(), GeometryNumber.get(4))
                        )
                )
        );
    }
    @Test
    public void ExpressionInRaisedInPowerTest() {
        Vertex a = new Vertex();
        Vertex b = new Vertex();
        ;
        Vertex c = new Vertex();
        LineSegment AB = new LineSegment(a, b);
        LineSegment AC = new LineSegment(a, c);
        Monomial before = new RaisedInThePower(GeometryNumber.get(1),
                new Polynomial(new Monomial(AB.getMonomial(), GeometryNumber.get(2)),
                        new Monomial(AB.getMonomial(), GeometryNumber.get(2))));
        Monomial after = before.addSimilarTerms();
        assertEquals(after, new RaisedInThePower(GeometryNumber.get(1),
                        new Monomial(AB.getMonomial(), GeometryNumber.get(4))
                )
        );
    }

    @Test
    public void ExpressionInRaisedInPowerInPolynomialTest() {
        Vertex a = new Vertex();
        Vertex b = new Vertex();
        ;
        Vertex c = new Vertex();
        LineSegment AB = new LineSegment(a, b);
        LineSegment AC = new LineSegment(a, c);
        Monomial before = new Polynomial(AB.getMonomial(),
                new RaisedInThePower(GeometryNumber.get(1),
                        new Polynomial(new Monomial(AB.getMonomial(), GeometryNumber.get(2)),
                                new Monomial(AB.getMonomial(), GeometryNumber.get(2)))));
        Monomial after = before.addSimilarTerms();
        assertEquals(after, new Polynomial(AB.getMonomial(),
                new RaisedInThePower(GeometryNumber.get(1),
                        new Monomial(AB.getMonomial(), GeometryNumber.get(4))
                )
        ));
    }


    @Test
    public void ExpressionInRaisedInPowerInMonomialTest() {
        Vertex a = new Vertex();
        Vertex b = new Vertex();
        ;
        Vertex c = new Vertex();
        LineSegment AB = new LineSegment(a, b);
        LineSegment AC = new LineSegment(a, c);
        Monomial before = new Monomial(AB.getMonomial(),
                new RaisedInThePower(GeometryNumber.get(1),
                        new Polynomial(new Monomial(AB.getMonomial(), GeometryNumber.get(2)),
                                new Monomial(AB.getMonomial(), GeometryNumber.get(2)))));
        Monomial after = before.addSimilarTerms();
        assertEquals(after, new Monomial(AB.getMonomial(),
                new RaisedInThePower(GeometryNumber.get(1),
                        new Monomial(AB.getMonomial(), GeometryNumber.get(4))
                )
        ));
    }
}
