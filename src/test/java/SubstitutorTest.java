import static org.junit.Assert.assertEquals;

import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;

import core.facts.EqualityFact;
import core.objects.numbers.GeometryNumber;
import core.objects.LineSegment;
import core.objects.Monomial;
import core.objects.numbers.NumberValue;
import core.objects.Polynomial;
import core.objects.RaisedInThePower;
import core.objects.Vertex;
import core.Substitutor;

public class SubstitutorTest {
    @Test
    public void substitutorTest(){

        Vertex A = new Vertex();
        Vertex B = new Vertex();
        Vertex C = new Vertex();
        LineSegment AB = new LineSegment(A, B);
        LineSegment BC = new LineSegment(B, C);
        LineSegment AC = new LineSegment(A, C);
        NumberValue ac = new NumberValue(AC, null),bc = new NumberValue(BC, null),ab = new NumberValue(AB, null);
        EqualityFact equation =  new EqualityFact(
                new RaisedInThePower(ab, GeometryNumber.createNumber(2)),
                new Polynomial(
                        new RaisedInThePower(new LinkedList<>(Arrays.asList(ac,GeometryNumber.createNumber(2))), GeometryNumber.createNumber(2)),
                        new RaisedInThePower(bc, GeometryNumber.createNumber(2))
                ));// AB^2 = (2AC)^2+BC^2
        HashMap<Monomial,Monomial> substituteTable = new HashMap<>();
        substituteTable.put(ab,GeometryNumber.createNumber(11));
        substituteTable.put(bc,GeometryNumber.createNumber(22));
        substituteTable.put(ac,GeometryNumber.createNumber(33));
        Monomial answerAB =  new Substitutor((Monomial)(equation.left),substituteTable).getSubstituted();
        Monomial answerBC_and_AC =  new Substitutor((Monomial)(equation.right),substituteTable).getSubstituted();
        String expectedStructureAB = "class core.objects.RaisedInThePower[class core.objects.numbers.GeometryNumber[]class core.objects.numbers.GeometryNumber[]]",
                expectedStructureBC_and_AC = "class core.objects.Polynomial[class core.objects.RaisedInThePower[class core.objects.numbers.GeometryNumber[]class core.objects.numbers.GeometryNumber[]class core.objects.numbers.GeometryNumber[]]class core.objects.RaisedInThePower[class core.objects.numbers.GeometryNumber[]class core.objects.numbers.GeometryNumber[]]]";
        assertEquals(answerAB.getUniqueStructureString(),expectedStructureAB);
        assertEquals(answerBC_and_AC.getUniqueStructureString(),expectedStructureBC_and_AC);
    }
}
