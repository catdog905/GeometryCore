import static org.junit.Assert.assertEquals;

import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;

import core.facts.equation.EqualityFact;
import core.objects.LineSegment;
import core.objects.Vertex;
import core.objects.expression.GeometryNumber;
import core.objects.expression.Monomial;
import core.objects.expression.Polynomial;
import core.objects.expression.RaisedInThePower;

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
                        new RaisedInThePower(new LinkedList<>(Arrays.asList(AC.getMonomial(),num2)), num2),
                        new RaisedInThePower(BC.getMonomial(), num2)
                ));// AB^2 = (2AC)^2+BC^2
        HashMap<Monomial,Monomial> substituteTable = new HashMap<>();
        substituteTable.put(AB.getMonomial(), GeometryNumber.get(11));
        substituteTable.put(BC.getMonomial(), GeometryNumber.get(22));
        substituteTable.put(AC.getMonomial(),GeometryNumber.get(33));
        Monomial answerAB =  ((Monomial)equation.left).substitute(substituteTable);
        Monomial answerBC_and_AC = ((Monomial)equation.right).substitute(substituteTable);
        String expectedStructureAB = "class core.objects.expression.RaisedInThePower[class core.objects.expression.GeometryNumber[]class core.objects.expression.GeometryNumber[]]",
                expectedStructureBC_and_AC = "class core.objects.expression.Polynomial[class core.objects.expression.RaisedInThePower[class core.objects.expression.GeometryNumber[]class core.objects.expression.GeometryNumber[]class core.objects.expression.GeometryNumber[]]class core.objects.expression.RaisedInThePower[class core.objects.expression.GeometryNumber[]class core.objects.expression.GeometryNumber[]]]";
        assertEquals(answerAB.getUniqueStructureString(),expectedStructureAB);
        assertEquals(answerBC_and_AC.getUniqueStructureString(),expectedStructureBC_and_AC);
    }
}
