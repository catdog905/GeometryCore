package ModelTests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import java.util.HashMap;

import core.model.facts.equation.EqualityFact;
import core.model.facts.objects.LineSegment;
import core.model.facts.objects.Vertex;
import core.model.facts.objects.expression.monomials.GeometryNumber;
import core.model.facts.objects.expression.monomials.Monomial;
import core.model.facts.objects.expression.monomials.Polynomial;
import core.model.facts.objects.expression.monomials.RaisedInThePower;

public class SubstitutorTest {

    @Test
    public void substitutorTest(){

        Vertex A = new Vertex();
        Vertex B = new Vertex();
        Vertex C = new Vertex();
        LineSegment AB = new LineSegment(A, B);
        LineSegment BC = new LineSegment(B, C);
        LineSegment AC = new LineSegment(A, C);
        GeometryNumber num2 = GeometryNumber.get(2);
        EqualityFact equation =  new EqualityFact(
                new RaisedInThePower(AB.getMonomial(), num2),
                new Polynomial(
                        new RaisedInThePower(new Monomial(AC.getMonomial(),num2), num2),
                        new RaisedInThePower(BC.getMonomial(), num2)
                ));// AB^2 = (2AC)^2+BC^2
        HashMap<Monomial,Monomial> substituteTable = new HashMap<>();
        substituteTable.put(AB.getMonomial(), GeometryNumber.get(11));
        substituteTable.put(BC.getMonomial(), GeometryNumber.get(22));
        substituteTable.put(AC.getMonomial(),GeometryNumber.get(33));
        Monomial answerAB =  ((Monomial)equation.left).substitute(substituteTable);
        Monomial answerBC_and_AC = ((Monomial)equation.right).substitute(substituteTable);
        Monomial expectedAB = new RaisedInThePower(
                GeometryNumber.get(11), num2
        );
        Monomial expectedBCandAC = new Polynomial(
                new RaisedInThePower(new Monomial(GeometryNumber.get(33),num2), num2),
                new RaisedInThePower(GeometryNumber.get(22), num2)
        );
        assertEquals(expectedAB, answerAB);
        assertEquals(expectedBCandAC, answerBC_and_AC);
    }
}
