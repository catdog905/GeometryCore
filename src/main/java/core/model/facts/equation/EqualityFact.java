package core.model.facts.equation;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;

import core.model.facts.Fact;
import core.model.facts.objects.GeometryObject;
import core.model.facts.objects.expression.monomials.Monomial;
import core.model.facts.objects.expression.monomials.Polynomial;

public class EqualityFact extends Fact {
    public GeometryObject left, right;

    public EqualityFact(GeometryObject left, GeometryObject right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public LinkedList<GeometryObject> getAllSubObjects() {
        return new LinkedList<>(Arrays.asList(left, right));
    }

    @Override
    public Fact createNewSimilarObject(Map<GeometryObject, GeometryObject> correspondence) {
        return new EqualityFact(correspondence.get(left), correspondence.get(right));
    }

    public EqualityFact expressMonomial(Monomial monomial) {
        // New equation left = our variable
        Monomial newLeft;

        // New equation right = some expression
        Monomial newRight;

        HashSet<Monomial> monomialsWithVariable = VariableSeeker.findAllMonomialsWithVariable(monomial, right.getMonomial());
        if (monomialsWithVariable != null) {
            newLeft = right.getMonomial();
            newRight = left.getMonomial();

            // We assume that variable is only on one side
            assert (VariableSeeker.findAllMonomialsWithVariable(monomial, left.getMonomial()) == null);
        } else {
            monomialsWithVariable = VariableSeeker.findAllMonomialsWithVariable(monomial, left.getMonomial());

            // We assume that variable exists
            assert (monomialsWithVariable != null);

            newLeft = left.getMonomial();
            newRight = right.getMonomial();
        }

        while (!(newLeft.getAllSubObjects().size() == 0)) {

            if (newLeft instanceof Polynomial) {
                PolynomialDeconstructor polynomialDeconstructor = new PolynomialDeconstructor((Polynomial) newLeft,newRight,monomialsWithVariable);
                newLeft = polynomialDeconstructor.getLeftoversOfDeconstructable();
                newRight = polynomialDeconstructor.getOppositeSide();
            } else {
                MonomialDeconstructor monomialDeconstructor = new MonomialDeconstructor( newLeft,newRight,monomialsWithVariable);
                newLeft = monomialDeconstructor.getLeftoversOfDeconstructable();
                newRight = monomialDeconstructor.getOppositeSide();
            }
        }

        // We assume that the number value that's on the left is the variable
        assert (monomial.equals(newLeft));

        return new EqualityFact(monomial, newRight);
    }
}
