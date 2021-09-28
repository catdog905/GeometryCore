import static org.junit.Assert.assertEquals;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;

import core.UniqueVariableSeeker;
import core.facts.equation.EqualityFact;
import core.facts.equation.ExpressedVariableFromEquation;
import core.facts.equation.MonomialDeconstructor;
import core.facts.equation.PolynomialDeconstructor;
import core.objects.LineSegment;
import core.objects.Vertex;
import core.objects.expression.Monomial;
import core.objects.expression.Polynomial;
import core.objects.expression.RaisedInThePower;
import core.objects.expression.GeometryNumber;
import core.objects.expression.NumberValue;

public class ExpressorTest {
    @Test
    public void uniqueVariableSeekerTest() {
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
    public void polynomialDeconstructorTest() {
        GeometryNumber variable = GeometryNumber.createNumber(1);
        Polynomial left = new Polynomial(variable,GeometryNumber.createNumber(2));
        Polynomial right =  new Polynomial(GeometryNumber.createNumber(3),GeometryNumber.createNumber(4));
        HashSet<Monomial> mon = new HashSet<>();
        mon.add((Monomial)variable);
        PolynomialDeconstructor polynomialDeconstructor = new PolynomialDeconstructor(left,right,mon);
        String expectedStructureLeft = "class core.objects.expression.numbers.GeometryNumber[]",
                expectedStructureRight = "class core.objects.expression.Polynomial[class core.objects.expression.numbers.GeometryNumber[]class core.objects.expression.numbers.GeometryNumber[]class core.objects.expression.Monomial[class core.objects.expression.numbers.GeometryNumber[]class core.objects.expression.numbers.GeometryNumber[]]]";
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
        String expectedStructureLeft = "class core.objects.expression.numbers.GeometryNumber[]",
                expectedStructureRight = "class core.objects.expression.Monomial[class core.objects.expression.Polynomial[class core.objects.expression.numbers.GeometryNumber[]class core.objects.expression.numbers.GeometryNumber[]]class core.objects.expression.RaisedInThePower[class core.objects.expression.numbers.GeometryNumber[]class core.objects.expression.numbers.GeometryNumber[]]]";
        assertEquals(expectedStructureLeft,monomialDeconstructor.getLeftoversOfDeconstructable().getUniqueStructureString());
        assertEquals(expectedStructureRight,monomialDeconstructor.getOppositeSide().getUniqueStructureString());

    }
    @Test
    public void expressorTest() {
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
        Monomial answerAB = (Monomial) new ExpressedVariableFromEquation(equation, ab).right;
        Monomial answerAC = (Monomial) new ExpressedVariableFromEquation(equation, ac).right;
        Monomial answerBC = (Monomial) new ExpressedVariableFromEquation(equation, bc).right;

        final String expectedStructureAB = "class core.objects.expression.RaisedInThePower[class core.objects.expression.Polynomial[class core.objects.expression.RaisedInThePower[class core.objects.expression.numbers.NumberValue[class core.objects.LineSegment]class core.objects.expression.numbers.GeometryNumber[]class core.objects.expression.numbers.GeometryNumber[]]class core.objects.expression.RaisedInThePower[class core.objects.expression.numbers.NumberValue[class core.objects.LineSegment]class core.objects.expression.numbers.GeometryNumber[]]]class core.objects.expression.RaisedInThePower[class core.objects.expression.numbers.GeometryNumber[]class core.objects.expression.numbers.GeometryNumber[]]]"
                ,expectedStructureAC = "class core.objects.expression.Monomial[class core.objects.expression.RaisedInThePower[class core.objects.expression.Polynomial[class core.objects.expression.RaisedInThePower[class core.objects.expression.numbers.NumberValue[class core.objects.LineSegment]class core.objects.expression.numbers.GeometryNumber[]]class core.objects.expression.Monomial[class core.objects.expression.RaisedInThePower[class core.objects.expression.numbers.NumberValue[class core.objects.LineSegment]class core.objects.expression.numbers.GeometryNumber[]]class core.objects.expression.numbers.GeometryNumber[]]]class core.objects.expression.RaisedInThePower[class core.objects.expression.numbers.GeometryNumber[]class core.objects.expression.numbers.GeometryNumber[]]]class core.objects.expression.RaisedInThePower[class core.objects.expression.numbers.GeometryNumber[]class core.objects.expression.numbers.GeometryNumber[]]]"
                ,expectedStructureBC = "class core.objects.expression.RaisedInThePower[class core.objects.expression.Polynomial[class core.objects.expression.RaisedInThePower[class core.objects.expression.numbers.NumberValue[class core.objects.LineSegment]class core.objects.expression.numbers.GeometryNumber[]]class core.objects.expression.Monomial[class core.objects.expression.RaisedInThePower[class core.objects.expression.numbers.NumberValue[class core.objects.LineSegment]class core.objects.expression.numbers.GeometryNumber[]class core.objects.expression.numbers.GeometryNumber[]]class core.objects.expression.numbers.GeometryNumber[]]]class core.objects.expression.RaisedInThePower[class core.objects.expression.numbers.GeometryNumber[]class core.objects.expression.numbers.GeometryNumber[]]]";

        assertEquals(expectedStructureAB, answerAB.getUniqueStructureString());

        assertEquals(expectedStructureAC, answerAC.getUniqueStructureString());

        assertEquals(expectedStructureBC, answerBC.getUniqueStructureString());
    }
}
