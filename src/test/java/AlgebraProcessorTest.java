import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import java.util.Arrays;
import java.util.LinkedList;

import GeometryCore.AlgebraProcessor;
import GeometryCore.Facts.EqualityFact;
import GeometryCore.GeometryObjects.GeometryNumber;
import GeometryCore.GeometryObjects.LineSegment;
import GeometryCore.GeometryObjects.Monomial;
import GeometryCore.GeometryObjects.NumberValue;
import GeometryCore.GeometryObjects.Polynomial;
import GeometryCore.GeometryObjects.RaisedInThePower;
import GeometryCore.GeometryObjects.Vertex;

public class AlgebraProcessorTest {
    @Test
    public void ExpressorTest() {
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
                ));
        Monomial answerAB =  AlgebraProcessor.expressVariableFromEquation(ab,equation);
        Monomial answerAC =  AlgebraProcessor.expressVariableFromEquation(ac,equation);
        Monomial answerBC =  AlgebraProcessor.expressVariableFromEquation(bc,equation);

        final String expectedStructureAB = "class GeometryCore.GeometryObjects.RaisedInThePower[class GeometryCore.GeometryObjects.Polynomial[class GeometryCore.GeometryObjects.RaisedInThePower[class GeometryCore.GeometryObjects.NumberValue[class GeometryCore.GeometryObjects.LineSegment]class GeometryCore.GeometryObjects.GeometryNumber[]class GeometryCore.GeometryObjects.GeometryNumber[]]class GeometryCore.GeometryObjects.RaisedInThePower[class GeometryCore.GeometryObjects.NumberValue[class GeometryCore.GeometryObjects.LineSegment]class GeometryCore.GeometryObjects.GeometryNumber[]]]class GeometryCore.GeometryObjects.RaisedInThePower[class GeometryCore.GeometryObjects.GeometryNumber[]class GeometryCore.GeometryObjects.GeometryNumber[]]]"
        ,expectedStructureAC = "class GeometryCore.GeometryObjects.Monomial[class GeometryCore.GeometryObjects.RaisedInThePower[class GeometryCore.GeometryObjects.Polynomial[class GeometryCore.GeometryObjects.RaisedInThePower[class GeometryCore.GeometryObjects.NumberValue[class GeometryCore.GeometryObjects.LineSegment]class GeometryCore.GeometryObjects.GeometryNumber[]]class GeometryCore.GeometryObjects.Monomial[class GeometryCore.GeometryObjects.RaisedInThePower[class GeometryCore.GeometryObjects.NumberValue[class GeometryCore.GeometryObjects.LineSegment]class GeometryCore.GeometryObjects.GeometryNumber[]]class GeometryCore.GeometryObjects.GeometryNumber[]]]class GeometryCore.GeometryObjects.RaisedInThePower[class GeometryCore.GeometryObjects.GeometryNumber[]class GeometryCore.GeometryObjects.GeometryNumber[]]]class GeometryCore.GeometryObjects.RaisedInThePower[class GeometryCore.GeometryObjects.GeometryNumber[]class GeometryCore.GeometryObjects.GeometryNumber[]]]"
        ,expectedStructureBC = "class GeometryCore.GeometryObjects.RaisedInThePower[class GeometryCore.GeometryObjects.Polynomial[class GeometryCore.GeometryObjects.RaisedInThePower[class GeometryCore.GeometryObjects.NumberValue[class GeometryCore.GeometryObjects.LineSegment]class GeometryCore.GeometryObjects.GeometryNumber[]]class GeometryCore.GeometryObjects.Monomial[class GeometryCore.GeometryObjects.RaisedInThePower[class GeometryCore.GeometryObjects.NumberValue[class GeometryCore.GeometryObjects.LineSegment]class GeometryCore.GeometryObjects.GeometryNumber[]class GeometryCore.GeometryObjects.GeometryNumber[]]class GeometryCore.GeometryObjects.GeometryNumber[]]]class GeometryCore.GeometryObjects.RaisedInThePower[class GeometryCore.GeometryObjects.GeometryNumber[]class GeometryCore.GeometryObjects.GeometryNumber[]]]";

        assertEquals(expectedStructureAB, answerAB.getUniqueStructureString());

        assertEquals(expectedStructureAC, answerAC.getUniqueStructureString());

        assertEquals(expectedStructureBC, answerBC.getUniqueStructureString());
    }
}
