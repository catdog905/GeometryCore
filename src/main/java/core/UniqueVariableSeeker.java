package core;

import java.util.HashSet;

import core.objects.GeometryObject;
import core.objects.Monomial;
import core.objects.numbers.NumberValue;
import core.objects.Polynomial;

public class UniqueVariableSeeker {

    // The functions assumes that variable can be encountered only once, otherwise there will be
    // undefined behavior
    public static HashSet<Monomial> findAllMonomialsWithUniqueVariable(NumberValue variable, Monomial equation) {
        HashSet<Monomial> answer;
        if (equation instanceof Polynomial) {
            //Polynomial
            Polynomial polynomialEquation = (Polynomial) equation;
            for (GeometryObject term : polynomialEquation.getAllSubObjects()) {
                Monomial monomialTerm = (Monomial) term;
                answer = findAllMonomialsWithUniqueVariable(variable, monomialTerm);
                if (answer != null) {
                    answer.add(equation);
                    return answer;
                }
            }
        } else if (equation instanceof NumberValue) {
            //A number, finally
            if (variable.equals(equation)) {
                answer = new HashSet<>();
                answer.add(equation);
                return answer;
            }
        } else {
            //Monomial
            for (GeometryObject term : equation.getAllSubObjects()) {
                answer = findAllMonomialsWithUniqueVariable(variable, (Monomial) term);
                if (answer != null) {
                    answer.add(equation);
                    return answer;
                }
            }
        }
        return null;
    }
}
