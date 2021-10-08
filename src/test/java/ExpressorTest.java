import static org.junit.Assert.assertEquals;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;

import core.VariableSeeker;
import core.facts.equation.EqualityFact;
import core.facts.equation.ExpressedVariableFromEquation;
import core.facts.equation.MonomialDeconstructor;
import core.facts.equation.PolynomialDeconstructor;
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
        EqualityFact equation = new EqualityFact(
                new RaisedInThePower(new LinkedList<>(Collections.singletonList(new RaisedInThePower(AB.getMonomial(), num2)))
                        , num2),
                new Polynomial(
                        new RaisedInThePower(new LinkedList<>(Arrays.asList(BC.getMonomial(), num2)), num2),
                        new RaisedInThePower(AC.getMonomial(), num2)
                ));
        Monomial equationLeft = (Monomial) equation.left;
        Monomial equationRight = (Monomial) equation.right;
        assert (VariableSeeker.findAllMonomialsWithVariable(AB.getMonomial(), equationRight) == null);
        assert (VariableSeeker.findAllMonomialsWithVariable(BC.getMonomial(), equationLeft) == null);
        assert (VariableSeeker.findAllMonomialsWithVariable(AC.getMonomial(), equationLeft) == null);
        var answerAB = VariableSeeker.findAllMonomialsWithVariable(AB.getMonomial(), equationLeft);
        var answerBC = VariableSeeker.findAllMonomialsWithVariable(BC.getMonomial(), equationRight);
        var answerAC = VariableSeeker.findAllMonomialsWithVariable(AC.getMonomial(), equationRight);
        assertEquals(answerAB.size(), 3);
        assertEquals(answerBC.size(), 3);
        assertEquals(answerAC.size(), 3);
    }

    @Test
    public void polynomialDeconstructorTest() {
        GeometryNumber num2 = GeometryNumber.get(2);
        GeometryNumber variable = GeometryNumber.get(1);
        Polynomial left = new Polynomial(variable, num2);
        Polynomial right = new Polynomial(GeometryNumber.get(3), GeometryNumber.get(4));
        HashSet<Monomial> mon = new HashSet<>();
        mon.add((Monomial) variable);
        PolynomialDeconstructor polynomialDeconstructor = new PolynomialDeconstructor(left, right, mon);
        String expectedStructureLeft = "GeometryNumber",
                expectedStructureRight = "Polynomial[GeometryNumber, GeometryNumber, Monomial[GeometryNumber, GeometryNumber]]";
        assertEquals(expectedStructureLeft, polynomialDeconstructor.getLeftoversOfDeconstructable().getUniqueStructureString());
        assertEquals(expectedStructureRight, polynomialDeconstructor.getOppositeSide().getUniqueStructureString());
    }

    @Test
    public void MonomialDeconstructorTest() {
        GeometryNumber num2 = GeometryNumber.get(2);
        GeometryNumber variable = GeometryNumber.get(1);
        Monomial left = new Monomial(variable, num2);
        Polynomial right = new Polynomial(GeometryNumber.get(3), GeometryNumber.get(4));
        HashSet<Monomial> mon = new HashSet<>();
        mon.add((Monomial) variable);
        MonomialDeconstructor monomialDeconstructor = new MonomialDeconstructor(left, right, mon);
        String expectedStructureLeft = "GeometryNumber",
                expectedStructureRight = "Monomial[Polynomial[GeometryNumber, GeometryNumber], RaisedInThePower[GeometryNumber, GeometryNumber]]";
        assertEquals(expectedStructureLeft, monomialDeconstructor.getLeftoversOfDeconstructable().getUniqueStructureString());
        assertEquals(expectedStructureRight, monomialDeconstructor.getOppositeSide().getUniqueStructureString());

    }

    @Test
    public void multpleVariablesTest() {
        Vertex A = new Vertex();
        Vertex B = new Vertex();
        Vertex C = new Vertex();
        LineSegment AB = new LineSegment(A, B);
        LineSegment BC = new LineSegment(B, C);
        LineSegment AC = new LineSegment(A, C);
        GeometryNumber num2 = GeometryNumber.get(2);
        EqualityFact equation = new EqualityFact(
                GeometryNumber.get(69),
                new Polynomial(
                        new Monomial(
                                GeometryNumber.get(3),
                                new RaisedInThePower(AC.getMonomial(), num2),
                                BC.getMonomial()
                        ),
                        new Monomial(
                                new RaisedInThePower(AC.getMonomial(), num2),
                                BC.getMonomial(),
                                AB.getMonomial()
                        ),
                        AB.getMonomial()
                ));// 69 = 3(AC^2)*BC +(AC^2)*BC*AB + AB


        // BC = (69 - AB)*(3[AC^2] + AB[AC^2])^[-1]
        // AC = {(69 - AB)*(3*BC + AB*BC)^[-1]}^[1/2]
        // AB = (69 - 3(AC^2)*BC)*((AC^2)*BC + 1)^[-1]



        Monomial answerAB = (Monomial) new ExpressedVariableFromEquation(equation, AB.getMonomial()).right;
        Monomial answerAC = (Monomial) new ExpressedVariableFromEquation(equation, AC.getMonomial()).right;
        Monomial answerBC = (Monomial) new ExpressedVariableFromEquation(equation, BC.getMonomial()).right;

        Monomial expectedStructureAB = new Monomial(
                new Polynomial(
                        GeometryNumber.get(69),
                        new Monomial(
                                new Monomial(
                                        GeometryNumber.get(3),
                                        BC.getMonomial(),
                                        new RaisedInThePower(
                                                AC.getMonomial(),
                                                GeometryNumber.get(2)
                                        )
                                )
                                , GeometryNumber.get(-1)
                        )
                ),
                new RaisedInThePower(
                        new Polynomial(
                                new Monomial(
                                        BC.getMonomial(),
                                        new RaisedInThePower(
                                                AC.getMonomial(),
                                                GeometryNumber.get(2)
                                        )
                                ),
                                GeometryNumber.get(1)
                        )
                        , GeometryNumber.get(-1)
                )
        );
        Monomial expectedStructureAC = new RaisedInThePower(
                new Monomial(
                        new Polynomial(
                                GeometryNumber.get(69),
                                new Monomial(AB.getMonomial(), GeometryNumber.get(-1))
                        ),
                        new RaisedInThePower(
                                new Polynomial(
                                        new Monomial(
                                                GeometryNumber.get(3),
                                                BC.getMonomial()
                                        ),
                                        new Monomial(
                                                AB.getMonomial(),
                                                BC.getMonomial()
                                        )
                                )
                                , GeometryNumber.get(-1)
                        )
                ),
                new RaisedInThePower(GeometryNumber.get(2), GeometryNumber.get(-1))
        );
        Monomial expectedStructureBC = new Monomial(
                new Polynomial(
                        GeometryNumber.get(69),
                        new Monomial(AB.getMonomial(), GeometryNumber.get(-1))
                ),
                new RaisedInThePower(
                        new Polynomial(
                                new Monomial(
                                        GeometryNumber.get(3),
                                        new RaisedInThePower(
                                                AC.getMonomial(),
                                                GeometryNumber.get(2)
                                        )
                                ),
                                new Monomial(
                                        AB.getMonomial(),
                                        new RaisedInThePower(AC.getMonomial(), GeometryNumber.get(2))
                                )
                        )
                        , GeometryNumber.get(-1)
                )
        );
        assertEquals(answerBC, expectedStructureBC);
        assertEquals(answerAC, expectedStructureAC);
        assertEquals(answerAB, expectedStructureAB);
    }

    @Test
    public void expressorTest() {
        Vertex A = new Vertex();
        Vertex B = new Vertex();
        Vertex C = new Vertex();
        LineSegment AB = new LineSegment(A, B);
        LineSegment BC = new LineSegment(B, C);
        LineSegment AC = new LineSegment(A, C);
        GeometryNumber num2 = GeometryNumber.get(2);
        EqualityFact equation = new EqualityFact(
                new RaisedInThePower(AB.getMonomial(), num2),
                new Polynomial(
                        new RaisedInThePower(new LinkedList<>(Arrays.asList(AC.getMonomial(), num2)), num2),
                        new RaisedInThePower(BC.getMonomial(), num2)
                ));// AB^2 = (2AC)^2+BC^2
        Monomial answerAB = (Monomial) new ExpressedVariableFromEquation(equation, AB.getMonomial()).right;
        Monomial answerAC = (Monomial) new ExpressedVariableFromEquation(equation, AC.getMonomial()).right;
        Monomial answerBC = (Monomial) new ExpressedVariableFromEquation(equation, BC.getMonomial()).right;

        final String expectedStructureAB = "RaisedInThePower[Polynomial[RaisedInThePower[MonomialEnveloper, GeometryNumber, GeometryNumber], RaisedInThePower[MonomialEnveloper, GeometryNumber]], RaisedInThePower[GeometryNumber, GeometryNumber]]", expectedStructureAC = "Monomial[RaisedInThePower[Polynomial[RaisedInThePower[MonomialEnveloper, GeometryNumber], Monomial[RaisedInThePower[MonomialEnveloper, GeometryNumber], GeometryNumber]], RaisedInThePower[GeometryNumber, GeometryNumber]], RaisedInThePower[GeometryNumber, GeometryNumber]]", expectedStructureBC = "RaisedInThePower[Polynomial[RaisedInThePower[MonomialEnveloper, GeometryNumber], Monomial[RaisedInThePower[MonomialEnveloper, GeometryNumber, GeometryNumber], GeometryNumber]], RaisedInThePower[GeometryNumber, GeometryNumber]]";

        assertEquals(expectedStructureAB, answerAB.getUniqueStructureString());

        assertEquals(expectedStructureAC, answerAC.getUniqueStructureString());

        assertEquals(expectedStructureBC, answerBC.getUniqueStructureString());
    }
}
