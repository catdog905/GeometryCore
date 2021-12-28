package ModelTests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import core.model.facts.objects.LineSegment;
import core.model.facts.objects.Vertex;
import core.model.facts.objects.expression.monomials.GeometryNumber;
import core.model.facts.objects.expression.monomials.Monomial;
import core.model.facts.objects.expression.monomials.Polynomial;
import core.model.facts.objects.expression.monomials.RaisedInThePower;

public class BracketsExpanderTest {
    @Test
    public void expandBracketsTest() {
        Vertex a = new Vertex();
        Vertex b = new Vertex();
        LineSegment A = new LineSegment(a, b);
        LineSegment B = new LineSegment(a, b);
        LineSegment C = new LineSegment(a, b);
        LineSegment D = new LineSegment(a, b);
        LineSegment E = new LineSegment(a, b);
        Monomial expression = new Monomial(
               A.getMonomial(),
                new Polynomial(B.getMonomial(),C.getMonomial(),
                    new Polynomial(D.getMonomial(),E.getMonomial())
        ));
        Monomial expandedExpression = expression.expandAllBrackets();
        Monomial expectedMonomial = new Polynomial(
                new Monomial(A.getMonomial(), B.getMonomial()),
                new Monomial(A.getMonomial(), C.getMonomial()),
                new Monomial(A.getMonomial(), D.getMonomial()),
                new Monomial(A.getMonomial(), E.getMonomial()));
        assertEquals(expectedMonomial, expandedExpression);
    }

    @Test
    public void expandBracketsTestWithoutBrackets() {
        Vertex a = new Vertex();
        Vertex b = new Vertex();
        LineSegment A = new LineSegment(a, b);
        LineSegment B = new LineSegment(a, b);
        LineSegment C = new LineSegment(a, b);
        LineSegment D = new LineSegment(a, b);
        LineSegment E = new LineSegment(a, b);
        Monomial expandedExpression = new Monomial(
                A.getMonomial(),
                B.getMonomial(),
                C.getMonomial(),
                D.getMonomial(),
                E.getMonomial()
        );
        Monomial expectedMonomial = new Monomial(
                A.getMonomial(),
                B.getMonomial(),
                C.getMonomial(),
                D.getMonomial(),
                E.getMonomial()
        );
        assertEquals(expectedMonomial, expandedExpression);
    }

    @Test
    public void expandDeepIncludedBracketsTest() {
        Vertex a = new Vertex();
        Vertex b = new Vertex();
        LineSegment A = new LineSegment(a, b);
        LineSegment B = new LineSegment(a, b);
        LineSegment C = new LineSegment(a, b);
        LineSegment D = new LineSegment(a, b);
        LineSegment F = new LineSegment(a, b);
        LineSegment G = new LineSegment(a, b);
        Monomial expandedExpression = new Monomial(
                A.getMonomial(),
                new Polynomial(
                        B.getMonomial(),
                        C.getMonomial()),
                new Polynomial(
                        D.getMonomial(),
                        new Polynomial(
                                F.getMonomial(),
                                G.getMonomial()
                        )
                )
        ).expandAllBrackets();
        Monomial expectedMonomial = new Polynomial(
                new Monomial(A.getMonomial(), B.getMonomial(), D.getMonomial()),
                new Monomial(A.getMonomial(), B.getMonomial(), F.getMonomial()),
                new Monomial(A.getMonomial(), B.getMonomial(), G.getMonomial()),
                new Monomial(A.getMonomial(), C.getMonomial(), D.getMonomial()),
                new Monomial(A.getMonomial(), C.getMonomial(), F.getMonomial()),
                new Monomial(A.getMonomial(), C.getMonomial(), G.getMonomial())
        );
        assertEquals(expectedMonomial, expandedExpression);
    }

    @Test
    public void expandBracketsWithPowersTest() {
        Vertex a = new Vertex();
        Vertex b = new Vertex();
        LineSegment A = new LineSegment(a, b);
        LineSegment B = new LineSegment(a, b);
        Monomial expression = new Monomial(
                new RaisedInThePower(
                        new Monomial(
                                A.getMonomial(),
                                B.getMonomial()
                        ),
                        GeometryNumber.get(2)
                )
        );
        Monomial expandedExpression = expression.expandAllBrackets();

        Monomial expectedMonomial = new Monomial(
                new RaisedInThePower(A.getMonomial(), GeometryNumber.get(2)),
                new RaisedInThePower(B.getMonomial(), GeometryNumber.get(2))
        );
        assertEquals(expectedMonomial, expandedExpression);
    }
}
