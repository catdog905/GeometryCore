import static org.junit.Assert.assertEquals;

import org.junit.Test;

import core.objects.LineSegment;
import core.objects.Vertex;
import core.objects.expression.GeometryNumber;
import core.objects.expression.Monomial;
import core.objects.expression.Polynomial;
import core.objects.expression.RaisedInThePower;

public class BracketsExpanderTest {
    @Test
    public void expandBracketsTest() {
        Vertex vertex = new Vertex();
        LineSegment A = new LineSegment(vertex, vertex);
        LineSegment B = new LineSegment(vertex, vertex);
        LineSegment C = new LineSegment(vertex, vertex);
        LineSegment D = new LineSegment(vertex, vertex);
        LineSegment E = new LineSegment(vertex, vertex);
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
        Vertex vertex = new Vertex();
        LineSegment A = new LineSegment(vertex, vertex);
        LineSegment B = new LineSegment(vertex, vertex);
        LineSegment C = new LineSegment(vertex, vertex);
        LineSegment D = new LineSegment(vertex, vertex);
        LineSegment E = new LineSegment(vertex, vertex);
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
        Vertex vertex = new Vertex();
        LineSegment A = new LineSegment(vertex, vertex);
        LineSegment B = new LineSegment(vertex, vertex);
        LineSegment C = new LineSegment(vertex, vertex);
        LineSegment D = new LineSegment(vertex, vertex);
        LineSegment F = new LineSegment(vertex, vertex);
        LineSegment G = new LineSegment(vertex, vertex);
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
        Vertex vertex = new Vertex();
        LineSegment A = new LineSegment(vertex, vertex);
        LineSegment B = new LineSegment(vertex, vertex);
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
