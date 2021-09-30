package core.facts.equation;

import java.util.HashSet;

import core.UniqueVariableSeeker;
import core.objects.expression.Monomial;
import core.objects.expression.Polynomial;

public class Expressor {


    static Monomial expressVariableFromEquation(Monomial variable, Monomial equationLeft, Monomial equationRight) {

        // New equation left = our variable
        Monomial newLeft;

        // New equation right = some expression
        Monomial newRight;

        HashSet<Monomial> monomialsWithVariable = UniqueVariableSeeker.findAllMonomialsWithUniqueVariable(variable, equationRight);
        if (monomialsWithVariable != null) {
            newLeft = equationRight;
            newRight = equationLeft;

            // We assume that variable is only on one side
            assert (UniqueVariableSeeker.findAllMonomialsWithUniqueVariable(variable, equationLeft) == null);
        } else {
            monomialsWithVariable = UniqueVariableSeeker.findAllMonomialsWithUniqueVariable(variable, equationLeft);

            // We assume that variable exists
            assert (monomialsWithVariable != null);

            newLeft = equationLeft;
            newRight = equationRight;
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
        assert (variable.equals(newLeft));

        return newRight;
    }

}
