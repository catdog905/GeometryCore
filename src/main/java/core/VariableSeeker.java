package core;

import java.util.HashSet;

import core.objects.GeometryObject;
import core.objects.expression.Monomial;
import core.objects.expression.Polynomial;

public class VariableSeeker {

    // The functions assumes that variable can be encountered only once, otherwise there will be
    // undefined behavior
    public static HashSet<Monomial> findAllMonomialsWithVariable(Monomial variable, Monomial equation) {
        HashSet<Monomial> answer = new HashSet<>();
        if (equation instanceof Polynomial) {
            //Polynomial
            Polynomial polynomialEquation = (Polynomial) equation;
            for (GeometryObject term : polynomialEquation.getAllSubObjects()) {
                Monomial monomialTerm = (Monomial) term;
                HashSet<Monomial> something = findAllMonomialsWithVariable(variable, monomialTerm);
                if (something != null) {
                    answer.addAll(something);
                }
            }
        } else if (equation.getAllSubObjects().size() == 0) {
            //A number, finally
            if (variable.equals(equation)) {
                answer = new HashSet<>();
                answer.add(equation);
                return answer;
            }
        } else {
            //Monomial
            for (GeometryObject term : equation.getAllSubObjects()) {
                HashSet<Monomial> something = findAllMonomialsWithVariable(variable, (Monomial) term);
                if (something != null) {
                    answer.addAll(something);
                }
            }
        }
        if (answer.isEmpty())
            return null;
        answer.add(equation);
        return answer;
    }
}
