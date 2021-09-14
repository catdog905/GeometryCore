import static org.junit.Assert.assertEquals;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
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
import GeometryCore.UniqueVariableSeeker;

public class ExpressorTest {
    @Test
    public void UniqueVariableSeekerTest() {
        Vertex A = new Vertex();
        Vertex B = new Vertex();
        Vertex C = new Vertex();
        LineSegment AB = new LineSegment(A, B);
        LineSegment BC = new LineSegment(B, C);
        LineSegment AC = new LineSegment(A, C);
        NumberValue ac = new NumberValue(AC, null),bc = new NumberValue(BC, null),ab = new NumberValue(AB, null);
        EqualityFact equation =  new EqualityFact(
                new RaisedInThePower(new LinkedList<>(Collections.singletonList(new RaisedInThePower(ab,GeometryNumber.createNumber(2))))
                        , GeometryNumber.createNumber(2)),
                new Polynomial(
                        new RaisedInThePower(new LinkedList<>(Arrays.asList(bc,GeometryNumber.createNumber(2))), GeometryNumber.createNumber(2)),
                        new RaisedInThePower(ac, GeometryNumber.createNumber(2))
                ));
        Monomial equationLeft = (Monomial)equation.left;
        Monomial equationRight = (Monomial)equation.right;
        assert(UniqueVariableSeeker.findAllMonomialsWithUniqueVariable(ab,equationRight) == null);
        assert(UniqueVariableSeeker.findAllMonomialsWithUniqueVariable(bc,equationLeft) == null);
        assert(UniqueVariableSeeker.findAllMonomialsWithUniqueVariable(ac,equationLeft) == null);
        var answerAB = UniqueVariableSeeker.findAllMonomialsWithUniqueVariable(ab,equationLeft);
        var answerBC = UniqueVariableSeeker.findAllMonomialsWithUniqueVariable(bc,equationRight);
        var answerAC = UniqueVariableSeeker.findAllMonomialsWithUniqueVariable(ac,equationRight);
        assertEquals(answerAB.size(),3);
        assertEquals(answerBC.size(),3);
        assertEquals(answerAC.size(),3);
    }
    @Test
    public void PolynomialDeconstructorTest() {
        GeometryNumber variable = GeometryNumber.createNumber(1);
        Polynomial left = new Polynomial(variable,GeometryNumber.createNumber(2));
        Polynomial right =  new Polynomial(GeometryNumber.createNumber(3),GeometryNumber.createNumber(4));
        HashSet<Monomial> mon = new HashSet<>();
        mon.add((Monomial)variable);
        PolynomialDeconstructor polynomialDeconstructor = new PolynomialDeconstructor(left,right,mon);
        String expectedStructureLeft = "class GeometryCore.GeometryObjects.GeometryNumber[]",
                expectedStructureRight = "class GeometryCore.GeometryObjects.Polynomial[class GeometryCore.GeometryObjects.GeometryNumber[]class GeometryCore.GeometryObjects.GeometryNumber[]class GeometryCore.GeometryObjects.Monomial[class GeometryCore.GeometryObjects.GeometryNumber[]class GeometryCore.GeometryObjects.GeometryNumber[]]]";
        assertEquals(expectedStructureLeft,polynomialDeconstructor.getLeftoversOfDeconstructable().getUniqueStructureString());
        assertEquals(expectedStructureRight,polynomialDeconstructor.getOppositeSide().getUniqueStructureString());
    }
    @Test
    public void MonomialDeconstructorTest() {
        GeometryNumber variable = GeometryNumber.createNumber(1);
        Monomial left = new Monomial(variable,GeometryNumber.createNumber(2));
        Polynomial right =  new Polynomial(GeometryNumber.createNumber(3),GeometryNumber.createNumber(4));
        HashSet<Monomial> mon = new HashSet<>();
        mon.add((Monomial)variable);
        MonomialDeconstructor monomialDeconstructor = new MonomialDeconstructor(left,right,mon);
        String expectedStructureLeft = "class GeometryCore.GeometryObjects.GeometryNumber[]",
                expectedStructureRight = "class GeometryCore.GeometryObjects.Monomial[class GeometryCore.GeometryObjects.Polynomial[class GeometryCore.GeometryObjects.GeometryNumber[]class GeometryCore.GeometryObjects.GeometryNumber[]]class GeometryCore.GeometryObjects.RaisedInThePower[class GeometryCore.GeometryObjects.GeometryNumber[]class GeometryCore.GeometryObjects.GeometryNumber[]]]";
        assertEquals(expectedStructureLeft,monomialDeconstructor.getLeftoversOfDeconstructable().getUniqueStructureString());
        assertEquals(expectedStructureRight,monomialDeconstructor.getOppositeSide().getUniqueStructureString());

    }
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
                ));// AB^2 = (2AC)^2+BC^2
        Monomial answerAB =  Expressor.expressVariableFromEquation(ab,(Monomial) equation.left,(Monomial)equation.right);
        Monomial answerAC =  Expressor.expressVariableFromEquation(ac,(Monomial) equation.left,(Monomial)equation.right);
        Monomial answerBC =  Expressor.expressVariableFromEquation(bc,(Monomial) equation.left,(Monomial)equation.right);

        final String expectedStructureAB = "class GeometryCore.GeometryObjects.RaisedInThePower[class GeometryCore.GeometryObjects.Polynomial[class GeometryCore.GeometryObjects.RaisedInThePower[class GeometryCore.GeometryObjects.NumberValue[class GeometryCore.GeometryObjects.LineSegment]class GeometryCore.GeometryObjects.GeometryNumber[]class GeometryCore.GeometryObjects.GeometryNumber[]]class GeometryCore.GeometryObjects.RaisedInThePower[class GeometryCore.GeometryObjects.NumberValue[class GeometryCore.GeometryObjects.LineSegment]class GeometryCore.GeometryObjects.GeometryNumber[]]]class GeometryCore.GeometryObjects.RaisedInThePower[class GeometryCore.GeometryObjects.GeometryNumber[]class GeometryCore.GeometryObjects.GeometryNumber[]]]"
                ,expectedStructureAC = "class GeometryCore.GeometryObjects.Monomial[class GeometryCore.GeometryObjects.RaisedInThePower[class GeometryCore.GeometryObjects.Polynomial[class GeometryCore.GeometryObjects.RaisedInThePower[class GeometryCore.GeometryObjects.NumberValue[class GeometryCore.GeometryObjects.LineSegment]class GeometryCore.GeometryObjects.GeometryNumber[]]class GeometryCore.GeometryObjects.Monomial[class GeometryCore.GeometryObjects.RaisedInThePower[class GeometryCore.GeometryObjects.NumberValue[class GeometryCore.GeometryObjects.LineSegment]class GeometryCore.GeometryObjects.GeometryNumber[]]class GeometryCore.GeometryObjects.GeometryNumber[]]]class GeometryCore.GeometryObjects.RaisedInThePower[class GeometryCore.GeometryObjects.GeometryNumber[]class GeometryCore.GeometryObjects.GeometryNumber[]]]class GeometryCore.GeometryObjects.RaisedInThePower[class GeometryCore.GeometryObjects.GeometryNumber[]class GeometryCore.GeometryObjects.GeometryNumber[]]]"
                ,expectedStructureBC = "class GeometryCore.GeometryObjects.RaisedInThePower[class GeometryCore.GeometryObjects.Polynomial[class GeometryCore.GeometryObjects.RaisedInThePower[class GeometryCore.GeometryObjects.NumberValue[class GeometryCore.GeometryObjects.LineSegment]class GeometryCore.GeometryObjects.GeometryNumber[]]class GeometryCore.GeometryObjects.Monomial[class GeometryCore.GeometryObjects.RaisedInThePower[class GeometryCore.GeometryObjects.NumberValue[class GeometryCore.GeometryObjects.LineSegment]class GeometryCore.GeometryObjects.GeometryNumber[]class GeometryCore.GeometryObjects.GeometryNumber[]]class GeometryCore.GeometryObjects.GeometryNumber[]]]class GeometryCore.GeometryObjects.RaisedInThePower[class GeometryCore.GeometryObjects.GeometryNumber[]class GeometryCore.GeometryObjects.GeometryNumber[]]]";

        assertEquals(expectedStructureAB, answerAB.getUniqueStructureString());

        assertEquals(expectedStructureAC, answerAC.getUniqueStructureString());

        assertEquals(expectedStructureBC, answerBC.getUniqueStructureString());
    }
}
