import static org.junit.Assert.assertEquals;

import org.junit.Test;

import core.UniqueVariableSeeker;
import core.facts.equation.EqualityFact;
import core.objects.LineSegment;
import core.objects.Vertex;
import core.objects.expression.GeometryNumber;
import core.objects.expression.Monomial;
import core.objects.expression.Polynomial;
import core.objects.expression.RaisedInThePower;

public class ExpressorTest {
    @Test
    public void uniqueVariableSeekerTest() {
        Vertex A = new Vertex();
        Vertex B = new Vertex();
        Vertex C = new Vertex();
        LineSegment AB = new LineSegment(A, B);
        LineSegment BC = new LineSegment(B, C);
        LineSegment AC = new LineSegment(A, C);
        GeometryNumber num2 = GeometryNumber.get(2);
        EqualityFact equation =  new EqualityFact(
                new RaisedInThePower(new Monomial(new RaisedInThePower(AB.getMonomial(),num2))
                        , num2),
                new Polynomial(
                        new RaisedInThePower(new Monomial(BC.getMonomial(),num2), num2),
                        new RaisedInThePower(AC.getMonomial(), num2)
                ));
        Monomial equationLeft = (Monomial)equation.left;
        Monomial equationRight = (Monomial)equation.right;
        assert(UniqueVariableSeeker.findAllMonomialsWithUniqueVariable(AB.getMonomial(),equationRight) == null);
        assert(UniqueVariableSeeker.findAllMonomialsWithUniqueVariable(BC.getMonomial(),equationLeft) == null);
        assert(UniqueVariableSeeker.findAllMonomialsWithUniqueVariable(AC.getMonomial(),equationLeft) == null);
        var answerAB = UniqueVariableSeeker.findAllMonomialsWithUniqueVariable(AB.getMonomial(),equationLeft);
        var answerBC = UniqueVariableSeeker.findAllMonomialsWithUniqueVariable(BC.getMonomial(),equationRight);
        var answerAC = UniqueVariableSeeker.findAllMonomialsWithUniqueVariable(AC.getMonomial(),equationRight);
        assertEquals(answerAB.size(),4);
        assertEquals(answerBC.size(),4);
        assertEquals(answerAC.size(),3);
    }
    //@Test
    //public void polynomialDeconstructorTest() {
    //    GeometryNumber num2 = GeometryNumber.get(2);
    //    GeometryNumber variable = GeometryNumber.get(1);
    //    Polynomial left = new Polynomial(variable,num2);
    //    Polynomial right =  new Polynomial(GeometryNumber.get(3),GeometryNumber.get(4));
    //    HashSet<Monomial> mon = new HashSet<>();
    //    mon.add((Monomial)variable);
    //    PolynomialDeconstructor polynomialDeconstructor = new PolynomialDeconstructor(left,right,mon);
    //    String expectedStructureLeft = "class core.objects.expression.GeometryNumber[]",
    //            expectedStructureRight = "class core.objects.expression.Polynomial[class core.objects.expression.GeometryNumber[]class core.objects.expression.GeometryNumber[]class core.objects.expression.Monomial[class core.objects.expression.GeometryNumber[]class core.objects.expression.GeometryNumber[]]]";
    //    assertEquals(expectedStructureLeft,polynomialDeconstructor.getLeftoversOfDeconstructable().getUniqueStructureString());
    //    assertEquals(expectedStructureRight,polynomialDeconstructor.getOppositeSide().getUniqueStructureString());
    //}
    //@Test
    //public void MonomialDeconstructorTest() {
    //    GeometryNumber num2 = GeometryNumber.get(2);
    //    GeometryNumber variable = GeometryNumber.get(1);
    //    Monomial left = new Monomial(variable,num2);
    //    Polynomial right =  new Polynomial(GeometryNumber.get(3),GeometryNumber.get(4));
    //    HashSet<Monomial> mon = new HashSet<>();
    //    mon.add((Monomial)variable);
    //    MonomialDeconstructor monomialDeconstructor = new MonomialDeconstructor(left,right,mon);
    //    String expectedStructureLeft = "class core.objects.expression.GeometryNumber[]",
    //            expectedStructureRight = "class core.objects.expression.Monomial[class core.objects.expression.Polynomial[class core.objects.expression.GeometryNumber[]class core.objects.expression.GeometryNumber[]]class core.objects.expression.RaisedInThePower[class core.objects.expression.GeometryNumber[]class core.objects.expression.GeometryNumber[]]]";
    //    assertEquals(expectedStructureLeft,monomialDeconstructor.getLeftoversOfDeconstructable().getUniqueStructureString());
    //    assertEquals(expectedStructureRight,monomialDeconstructor.getOppositeSide().getUniqueStructureString());
//
    //}
    //@Test
    //public void expressorTest() {
    //    Vertex A = new Vertex();
    //    Vertex B = new Vertex();
    //    Vertex C = new Vertex();
    //    LineSegment AB = new LineSegment(A, B);
    //    LineSegment BC = new LineSegment(B, C);
    //    LineSegment AC = new LineSegment(A, C);
    //    GeometryNumber num2 = GeometryNumber.get(2);
    //    EqualityFact equation =  new EqualityFact(
    //            new RaisedInThePower(AB.getMonomial(), num2),
    //            new Polynomial(
    //                    new RaisedInThePower(new Monomial(AC.getMonomial(),num2), num2),
    //                    new RaisedInThePower(BC.getMonomial(), num2)
    //            ));// AB^2 = (2AC)^2+BC^2
    //    Monomial answerAB = (Monomial) new ExpressedVariableFromEquation(equation, AB.getMonomial()).right;
    //    Monomial answerAC = (Monomial) new ExpressedVariableFromEquation(equation, AC.getMonomial()).right;
    //    Monomial answerBC = (Monomial) new ExpressedVariableFromEquation(equation, BC.getMonomial()).right;
//
    //    final String expectedStructureAB = "class core.objects.expression.RaisedInThePower[class core.objects.expression.Polynomial[class core.objects.expression.RaisedInThePower[class core.objects.expression.MonomialEnveloper[]class core.objects.expression.GeometryNumber[]class core.objects.expression.GeometryNumber[]]class core.objects.expression.RaisedInThePower[class core.objects.expression.MonomialEnveloper[]class core.objects.expression.GeometryNumber[]]]class core.objects.expression.RaisedInThePower[class core.objects.expression.GeometryNumber[]class core.objects.expression.GeometryNumber[]]]"
    //            ,expectedStructureAC = "class core.objects.expression.Monomial[class core.objects.expression.RaisedInThePower[class core.objects.expression.Polynomial[class core.objects.expression.RaisedInThePower[class core.objects.expression.MonomialEnveloper[]class core.objects.expression.GeometryNumber[]]class core.objects.expression.Monomial[class core.objects.expression.RaisedInThePower[class core.objects.expression.MonomialEnveloper[]class core.objects.expression.GeometryNumber[]]class core.objects.expression.GeometryNumber[]]]class core.objects.expression.RaisedInThePower[class core.objects.expression.GeometryNumber[]class core.objects.expression.GeometryNumber[]]]class core.objects.expression.RaisedInThePower[class core.objects.expression.GeometryNumber[]class core.objects.expression.GeometryNumber[]]]"
    //            ,expectedStructureBC = "class core.objects.expression.RaisedInThePower[class core.objects.expression.Polynomial[class core.objects.expression.RaisedInThePower[class core.objects.expression.MonomialEnveloper[]class core.objects.expression.GeometryNumber[]]class core.objects.expression.Monomial[class core.objects.expression.RaisedInThePower[class core.objects.expression.MonomialEnveloper[]class core.objects.expression.GeometryNumber[]class core.objects.expression.GeometryNumber[]]class core.objects.expression.GeometryNumber[]]]class core.objects.expression.RaisedInThePower[class core.objects.expression.GeometryNumber[]class core.objects.expression.GeometryNumber[]]]";
//
    //    assertEquals(expectedStructureAB, answerAB.getUniqueStructureString());
//
    //    assertEquals(expectedStructureAC, answerAC.getUniqueStructureString());
//
    //    assertEquals(expectedStructureBC, answerBC.getUniqueStructureString());
    //}
}
