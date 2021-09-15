import static org.junit.Assert.assertEquals;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;

import GeometryCore.Expressor;
import GeometryCore.Facts.EqualityFact;
import GeometryCore.GeometryObjects.GeometryNumber;
import GeometryCore.GeometryObjects.LineSegment;
import GeometryCore.GeometryObjects.Monomial;
import GeometryCore.GeometryObjects.NumberValue;
import GeometryCore.GeometryObjects.Polynomial;
import GeometryCore.GeometryObjects.RaisedInThePower;
import GeometryCore.GeometryObjects.Vertex;
import GeometryCore.MonomialDeconstructor;
import GeometryCore.PolynomialDeconstructor;
import GeometryCore.Substitutor;
import GeometryCore.UniqueVariableSeeker;

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
        String expectedStructureAB = "class GeometryCore.GeometryObjects.RaisedInThePower[class GeometryCore.GeometryObjects.GeometryNumber[]class GeometryCore.GeometryObjects.GeometryNumber[]]",
                expectedStructureBC_and_AC = "class GeometryCore.GeometryObjects.Polynomial[class GeometryCore.GeometryObjects.RaisedInThePower[class GeometryCore.GeometryObjects.GeometryNumber[]class GeometryCore.GeometryObjects.GeometryNumber[]class GeometryCore.GeometryObjects.GeometryNumber[]]class GeometryCore.GeometryObjects.RaisedInThePower[class GeometryCore.GeometryObjects.GeometryNumber[]class GeometryCore.GeometryObjects.GeometryNumber[]]]";
        assertEquals(answerAB.getUniqueStructureString(),expectedStructureAB);
        assertEquals(answerBC_and_AC.getUniqueStructureString(),expectedStructureBC_and_AC);
    }
}
