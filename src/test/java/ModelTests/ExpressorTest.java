package ModelTests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import java.util.HashSet;

import core.model.facts.equation.VariableSeeker;
import core.model.facts.equation.EqualityFact;
import core.model.facts.equation.MonomialDeconstructor;
import core.model.facts.equation.PolynomialDeconstructor;
import core.model.facts.objects.LineSegment;
import core.model.facts.objects.Vertex;
import core.model.facts.objects.expression.monomials.GeometryNumber;
import core.model.facts.objects.expression.monomials.Monomial;
import core.model.facts.objects.expression.monomials.Polynomial;
import core.model.facts.objects.expression.monomials.RaisedInThePower;

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
                new RaisedInThePower(new Monomial(new RaisedInThePower(AB.getMonomial(), num2))
                        , num2),
                new Polynomial(
                        new RaisedInThePower(new Monomial(BC.getMonomial(), num2), num2),
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
        assertEquals(answerAB.size(), 4);
        assertEquals(answerBC.size(), 4);
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
        // [1]+2 = 3+4 => [1] = 3+4-2
        PolynomialDeconstructor polynomialDeconstructor = new PolynomialDeconstructor(left, right, mon);
        Monomial expectedLeft = (Monomial) variable;
        Monomial expectedRight = new Polynomial(
                GeometryNumber.get(3),
                GeometryNumber.get(4),
                new Monomial(GeometryNumber.get(-1), num2)
        );
        assertEquals(expectedLeft, polynomialDeconstructor.getLeftoversOfDeconstructable());
        assertEquals(expectedRight, polynomialDeconstructor.getOppositeSide());
    }

    @Test
    public void MonomialDeconstructorTest() {
        GeometryNumber num2 = GeometryNumber.get(2);
        GeometryNumber variable = GeometryNumber.get(1);
        Monomial left = new Monomial(variable, num2);
        Polynomial right = new Polynomial(GeometryNumber.get(3), GeometryNumber.get(4));
        // [1]*2 = 3+4 => [1] = (3+4)*2^(-1)
        HashSet<Monomial> mon = new HashSet<>();
        mon.add((Monomial) variable);
        MonomialDeconstructor monomialDeconstructor = new MonomialDeconstructor(left, right, mon);
        Monomial expectedLeft = (Monomial) variable;
        Monomial expectedRight = new Monomial(
                new Polynomial(GeometryNumber.get(3), GeometryNumber.get(4)),
                new RaisedInThePower(GeometryNumber.get(2), GeometryNumber.get(-1))
        );
        assertEquals(expectedLeft, monomialDeconstructor.getLeftoversOfDeconstructable());
        assertEquals(expectedRight, monomialDeconstructor.getOppositeSide());

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


        Monomial answerAB = (Monomial) equation.expressMonomial(AB.getMonomial()).right;
        Monomial answerAC = (Monomial) equation.expressMonomial(AC.getMonomial()).right;
        Monomial answerBC = (Monomial) equation.expressMonomial(BC.getMonomial()).right;

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
                        new Monomial(
                                new RaisedInThePower(AC.getMonomial(), num2),
                                GeometryNumber.get(4)
                        ),
                        new RaisedInThePower(BC.getMonomial(), num2)
                ));// AB^2 = 4*(AC)^2+BC^2
        //AC = {(AB^2-BC^2)*4^[-1]}^(2^[-1])
        Monomial answerAB = (Monomial) equation.expressMonomial(AB.getMonomial()).right;
        Monomial answerAC = (Monomial) equation.expressMonomial(AC.getMonomial()).right;
        Monomial answerBC = (Monomial) equation.expressMonomial(BC.getMonomial()).right;

        Monomial expectedAB = new RaisedInThePower(
                new Polynomial(
                        new Monomial(
                                new RaisedInThePower(AC.getMonomial(), num2),
                                GeometryNumber.get(4)
                        ),
                        new RaisedInThePower(BC.getMonomial(), num2)
                ),
                new RaisedInThePower(GeometryNumber.get(2), GeometryNumber.get(-1)));
        Monomial expectedAC = new RaisedInThePower(
                new Monomial(
                        new Polynomial(
                                new RaisedInThePower(
                                        AB.getMonomial(),
                                        GeometryNumber.get(2)
                                ),
                                new Monomial(
                                        GeometryNumber.get(-1),
                                        new RaisedInThePower(
                                                BC.getMonomial(),
                                                GeometryNumber.get(2)
                                        )
                                )
                        ),
                        new RaisedInThePower(
                                GeometryNumber.get(4),
                                GeometryNumber.get(-1)
                        )
                ),
                new RaisedInThePower(GeometryNumber.get(2), GeometryNumber.get(-1)));
        // AB^2 = 4*(AC)^2+BC^2
        // BC = [AB^2 - 4*(AC)^2]^[2^(-1)]
        Monomial expectedBC = new RaisedInThePower(
                new Polynomial(
                        new RaisedInThePower(AB.getMonomial(), num2),
                        new Monomial(
                                new Monomial(
                                        new RaisedInThePower(AC.getMonomial(), num2),
                                        GeometryNumber.get(4)
                                ),
                                GeometryNumber.get(-1)
                        )
                ),
                new RaisedInThePower(GeometryNumber.get(2), GeometryNumber.get(-1)));
        assertEquals(expectedAB, answerAB);

        assertEquals(expectedAC, answerAC);

        assertEquals(expectedBC, answerBC);
    }
}
